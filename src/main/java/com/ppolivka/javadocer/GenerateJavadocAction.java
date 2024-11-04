package com.ppolivka.javadocer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GenerateJavadocAction extends AnAction {

    public GenerateJavadocAction() {
        super("Generate Javadoc");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        PsiFile psiFile = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE);

        if (project == null || editor == null || psiFile == null) {
            return;
        }

        PsiElement elementAtCaret = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiMethod method = PsiTreeUtil.getParentOfType(elementAtCaret, PsiMethod.class);

        if (method == null) {
            JOptionPane.showMessageDialog(null, "No method found at cursor position.");
            return;
        }

        String javadoc = generateJavadocFromApi(method);
        if (javadoc != null) {
            insertJavadoc(project, psiFile, method, javadoc);
        }
    }

    private String generateJavadocFromApi(PsiMethod method) {
        try {
            String apiUrl = "https://ai-auto-javadoc-api.sliplane.app/generate_javadoc";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Create JSON payload using JSONObject for valid JSON encoding
            JSONObject payload = new JSONObject();
            payload.put("code", method.getText());

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read API response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Extract Javadoc comment from JSON response (assuming API returns {"javadoc": "..."})
            String jsonResponse = response.toString();
            JSONObject jsonResponseObject = new JSONObject(jsonResponse);
            return jsonResponseObject.getString("javadoc");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error calling API: " + e.getMessage());
        }
        return null;
    }

    private void insertJavadoc(Project project, PsiFile psiFile, PsiMethod method, String javadoc) {
        ApplicationManager.getApplication().invokeLater(() ->
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    // Format the Javadoc to ensure correct multiline structure
                    StringBuilder formattedJavadoc = new StringBuilder("/**\n");
                    for (String line : javadoc.split("\n")) {
                        formattedJavadoc.append(" * ").append(line.trim()).append("\n");
                    }
                    formattedJavadoc.append(" */");

                    // Create the Javadoc comment element
                    PsiElement comment = JavaPsiFacade.getElementFactory(project)
                            .createCommentFromText(formattedJavadoc.toString(), null);

                    // Insert the formatted Javadoc before the method's parent to ensure proper placement
                    PsiElement addedComment = method.getParent().addBefore(comment, method);

                    // Commit the document to ensure it reflects the changes
                    PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(
                            PsiDocumentManager.getInstance(project).getDocument(psiFile)
                    );
                    PsiDocumentManager.getInstance(project).commitDocument(
                            PsiDocumentManager.getInstance(project).getDocument(psiFile)
                    );
                })
        );
    }
}