# Kotlin Code Assistant

An AI-powered code assistant that helps developers write better Kotlin code (and other languages too!). It combines static code analysis with AI-powered suggestions to provide comprehensive code insights, improvements, and generation capabilities.

**Available as both a command-line tool and an Android app!**

## Features

- 🔍 **Static Code Analysis**: Detect common issues and anti-patterns in Kotlin code
- 🤖 **AI-Powered Suggestions**: Get intelligent code improvement suggestions using OpenAI or compatible APIs
- 📝 **Code Explanation**: Understand what code does with AI-generated explanations
- ✨ **Code Improvement**: Get refactored versions of your code with best practices applied
- 🚀 **Code Generation**: Generate code from natural language descriptions
- 📊 **Code Metrics**: Get complexity metrics and statistics about your code
- 🌐 **Multi-Language Support**: While focused on Kotlin, supports other programming languages
- 📱 **Android App**: Use the code assistant directly on your Android device

## Installation

### Command Line Tool

#### Prerequisites

- Java 11 or higher
- Gradle 8.5 or higher (wrapper included)

#### Build from Source

```bash
# Clone the repository
git clone https://github.com/valleedgar085-rgb/kotlin-code-assistant.git
cd kotlin-code-assistant

# Build the project
./gradlew build

# Run the application
./gradlew run --args="--help"
```

### Android App

To build and install the Android APK, see [ANDROID_BUILD.md](ANDROID_BUILD.md) for detailed instructions.

**Quick start:**
1. Install Android Studio
2. Open the project
3. Build > Build Bundle(s) / APK(s) > Build APK(s)
4. Install the generated APK on your Android device

## Usage

The Kotlin Code Assistant provides several commands:

### 1. Analyze Code (Static Analysis)

Analyze code for common issues and best practices without requiring AI:

```bash
./gradlew run --args="analyze path/to/file.kt"
```

Options:
- `-l, --language`: Specify the programming language (default: kotlin)

Example:
```bash
./gradlew run --args="analyze src/main/kotlin/MyClass.kt"
```

### 2. Get Comprehensive Suggestions (Static + AI)

Get both static analysis and AI-powered suggestions:

```bash
./gradlew run --args="suggest path/to/file.kt"
```

This command combines static analysis with AI insights for comprehensive feedback.

### 3. Explain Code

Get an AI-generated explanation of what your code does:

```bash
./gradlew run --args="explain path/to/file.kt"
```

Options:
- `-l, --language`: Specify the programming language (default: kotlin)

### 4. Improve Code

Get AI-powered suggestions and refactored code:

```bash
./gradlew run --args="improve path/to/file.kt"
```

Options:
- `-l, --language`: Specify the programming language (default: kotlin)
- `-f, --focus`: Specify what to focus on (e.g., "performance", "readability", "error handling")

Example:
```bash
./gradlew run --args="improve MyClass.kt -f performance"
```

### 5. Generate Code

Generate code from a natural language description:

```bash
./gradlew run --args="'generate \"Create a function that calculates fibonacci numbers\"'"
```

Options:
- `-l, --language`: Specify the programming language (default: kotlin)
- `-o, --output`: Save generated code to a file

Example:
```bash
./gradlew run --args="'generate \"Create a REST API client for user management\" -l kotlin -o UserClient.kt'"
```

## Configuration

### AI Service Setup

To use AI-powered features, you need to configure an AI service:

#### Using OpenAI

```bash
export OPENAI_API_KEY="your-api-key-here"
export AI_MODEL="gpt-4"  # Optional, defaults to gpt-4
```

#### Using Custom OpenAI-Compatible APIs

```bash
export OPENAI_API_KEY="your-api-key-here"
export AI_BASE_URL="https://your-api-endpoint.com/v1"
export AI_MODEL="your-model-name"
```

### Without AI

The `analyze` command works without any AI configuration, providing static code analysis only.

## Static Analysis Features

The static analyzer checks for:

- ✅ Use of null assertion operator (!!)
- ✅ Preference for `val` over `var`
- ✅ Explicit null checks that could use safe calls
- ✅ Thread.sleep() instead of coroutines
- ✅ Missing documentation on public functions
- ✅ Long lines (> 120 characters)
- ✅ Code complexity metrics

## AI Features

When configured with an AI service, you get:

- 🤖 Intelligent code review suggestions
- 📚 Detailed code explanations
- 🔧 Automated code improvements
- ⚡ Code generation from descriptions
- 🎯 Context-aware recommendations

## Examples

### Example 1: Analyzing a Kotlin File

```bash
./gradlew run --args="analyze src/main/kotlin/Calculator.kt"
```

Output:
```
Analyzing Calculator.kt...

Found 2 suggestion(s):

⚠️  1. Avoid !! operator
   The !! operator can cause NullPointerException. Consider using safe calls (?.) or elvis operator (?:) instead.

ℹ️  2. Add documentation
   Consider adding KDoc documentation for public functions to improve code maintainability.

Code Metrics:
  Lines: 15
  Non-empty lines: 12
  Functions: 3
  Classes: 1
```

### Example 2: Generating Code

```bash
./gradlew run --args="'generate \"Create a data class for a User with name, email, and age\"'"
```

Output:
```kotlin
/**
 * Represents a user in the system
 */
data class User(
    val name: String,
    val email: String,
    val age: Int
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.contains("@")) { "Invalid email format" }
        require(age >= 0) { "Age must be non-negative" }
    }
}
```

## Architecture

The project is organized into several modules:

- **models**: Data classes for code snippets, suggestions, and AI requests/responses
- **analyzer**: Static code analysis engine
- **ai**: AI service interface and implementations (OpenAI-compatible)
- **suggestions**: Suggestion engine that combines static analysis with AI
- **cli**: Command-line interface using Clikt

## Testing

Run the test suite:

```bash
./gradlew test
```

Run with coverage:

```bash
./gradlew test jacocoTestReport
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

### Development Setup

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Make your changes and add tests
4. Run tests: `./gradlew test`
5. Build the project: `./gradlew build`
6. Commit your changes: `git commit -am 'Add new feature'`
7. Push to the branch: `git push origin feature/my-feature`
8. Submit a pull request

## License

This project is open source and available under the MIT License.

## Roadmap

- [x] Android app for mobile code analysis
- [ ] Add support for more programming languages
- [ ] Implement caching for AI responses
- [ ] Add IDE plugins (IntelliJ IDEA, VS Code)
- [ ] Support for batch file analysis
- [ ] Interactive REPL mode
- [ ] Custom rule configuration
- [ ] Integration with Git hooks
- [ ] Web UI for code analysis
- [ ] AI-powered suggestions in Android app

## Support

For issues, questions, or contributions, please visit:
https://github.com/valleedgar085-rgb/kotlin-code-assistant

---

Built with ❤️ for the Kotlin community