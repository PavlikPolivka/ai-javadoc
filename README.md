
# AI Auto Javadoc Generator

AI Auto Javadoc Generator is an IntelliJ plugin that uses an AI-powered API to generate Javadoc comments for methods in Java code. The plugin integrates directly into the Generate menu of IntelliJ IDEA, making it easy to add Javadoc documentation with a single action.

## Features

- **Generate Javadoc**: Automatically generates Javadoc for Java methods.
- **AI-Powered**: Uses a custom API for intelligent Javadoc generation.
- **Cross-Platform Keyboard Shortcut**: Compatible with Windows, macOS, and Linux.
- **Integrated in Generate Menu**: Available through the IntelliJ Generate menu with customizable shortcuts.

## Installation

### Install from JetBrains Marketplace

1. Open IntelliJ IDEA.
2. Go to `Plugins > Marketplace`.
3. Search for "AI Auto Javadoc Generator".
4. Click `Install`.

### Manual Installation

1. Download the plugin package from the [Releases page](https://plugins.jetbrains.com/).
2. In IntelliJ IDEA, go to `Plugins > Installed`.
3. Click on the `⚙️` icon and select `Install Plugin from Disk...`.
4. Select the downloaded `.zip` file and click `OK`.

## Usage

1. Place the caret inside any Java method.
2. Open the **Generate** menu (shortcut: `Alt + Insert` on Windows/Linux, `⌘ + N` on macOS).
3. Select **Generate Javadoc** or press `Ctrl + Alt + Shift + J` (Windows) / `⌘ + Option + Shift + J` (macOS).
4. The plugin calls the AI API and inserts the generated Javadoc above the method.

## Development

To develop or contribute to the plugin:

1. Clone the repository.
2. Run `./gradlew runIde` to test the plugin in a sandboxed IntelliJ environment.
3. After making changes, build the plugin with `./gradlew buildPlugin`.

## License

This project is licensed under the MIT License.

---

MIT License

Copyright (c) 2024 Pavel Polívka

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
