# Contributing to Kotlin Code Assistant

Thank you for your interest in contributing to Kotlin Code Assistant! This document provides guidelines and information for contributors.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/kotlin-code-assistant.git`
3. Create a new branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test your changes
6. Submit a pull request

## Development Setup

### Prerequisites

- Java 11 or higher
- Gradle 8.5+ (included via wrapper)
- Git

### Building the Project

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

### Running the Application

```bash
./gradlew run --args="--help"
```

## Code Style

We follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use 4 spaces for indentation
- Use camelCase for variable and function names
- Use PascalCase for class names
- Add KDoc documentation for public APIs
- Keep line length under 120 characters

## Adding New Features

When adding new features:

1. **Write tests first** - Test-driven development helps ensure quality
2. **Update documentation** - Keep README.md and other docs up to date
3. **Follow existing patterns** - Look at existing code for guidance
4. **Keep it modular** - New features should be in appropriate packages

## Project Structure

```
src/
├── main/kotlin/com/kotlinassistant/
│   ├── ai/           # AI service implementations
│   ├── analyzer/     # Static code analysis
│   ├── cli/          # Command-line interface
│   ├── models/       # Data models
│   └── suggestions/  # Suggestion engine
└── test/kotlin/com/kotlinassistant/
    └── ...           # Test files
```

## Adding New Analyzers

To add a new code analyzer:

1. Add detection logic in `CodeAnalyzer.kt`
2. Create appropriate `CodeSuggestion` objects
3. Add tests in `CodeAnalyzerTest.kt`
4. Update documentation

Example:
```kotlin
if (code.contains("some_pattern")) {
    suggestions.add(
        CodeSuggestion(
            title = "Your suggestion title",
            description = "Detailed explanation...",
            severity = SuggestionSeverity.WARNING
        )
    )
}
```

## Adding New Commands

To add a new CLI command:

1. Create a new command class in `Commands.kt` extending `CliktCommand`
2. Register it in `Main.kt`
3. Add documentation
4. Add tests if applicable

Example:
```kotlin
class YourCommand : CliktCommand(
    name = "yourcommand",
    help = "Description of your command"
) {
    override fun run() {
        // Implementation
    }
}
```

## Testing

### Unit Tests

Write unit tests for all new functionality:

```kotlin
@Test
fun `test your feature`() {
    // Arrange
    val input = createTestInput()
    
    // Act
    val result = yourFunction(input)
    
    // Assert
    assertTrue(result.isValid())
}
```

### Integration Tests

For features that interact with AI services, consider:
- Mocking AI responses
- Testing with real APIs (in separate test suite)
- Providing mock implementations

## Pull Request Process

1. **Update documentation** - README, CONTRIBUTING, etc.
2. **Add/update tests** - Maintain test coverage
3. **Run all tests** - Ensure nothing breaks
4. **Update CHANGELOG** - If applicable
5. **Keep commits clean** - Use meaningful commit messages
6. **Reference issues** - Link to related issues in PR description

### PR Checklist

- [ ] Code follows project style guidelines
- [ ] Tests added/updated and passing
- [ ] Documentation updated
- [ ] No breaking changes (or clearly documented)
- [ ] Commits are clear and descriptive

## Reporting Issues

When reporting issues, please include:

- Description of the problem
- Steps to reproduce
- Expected behavior
- Actual behavior
- Environment (OS, Java version, etc.)
- Code samples (if applicable)

## Feature Requests

We welcome feature requests! Please:

1. Check existing issues first
2. Provide clear use cases
3. Explain the problem it solves
4. Consider implementation approaches

## Code of Conduct

- Be respectful and inclusive
- Welcome newcomers
- Focus on constructive feedback
- Help others learn and grow

## Questions?

Feel free to:
- Open an issue for questions
- Start a discussion
- Reach out to maintainers

Thank you for contributing! 🎉
