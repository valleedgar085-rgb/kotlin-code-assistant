# Kotlin Code Assistant - Quick Start Guide

Get started with Kotlin Code Assistant in 5 minutes!

## Prerequisites

- Java 11+ installed
- Git (to clone the repository)

## Installation

```bash
# Clone the repository
git clone https://github.com/valleedgar085-rgb/kotlin-code-assistant.git
cd kotlin-code-assistant

# Build the project
./gradlew build
```

That's it! You're ready to use the assistant.

## Your First Analysis

Let's analyze some example code:

```bash
./gradlew run --args="analyze examples/BadExample.kt"
```

You'll see output like:

```
Analyzing BadExample.kt...

Found 4 suggestion(s):

⚠️ 1. Avoid !! operator
   The !! operator can cause NullPointerException...

ℹ️ 2. Use safe calls
   Consider using safe call operator...

💡 3. Use coroutines for delays
   Consider using kotlinx.coroutines delay()...

ℹ️ 4. Add documentation
   Consider adding KDoc documentation...
```

## Basic Commands

### 1. Analyze (No AI Required)

Analyze code for common issues and get metrics:

```bash
./gradlew run --args="analyze path/to/file.kt"
```

**Use this when:**
- You want quick feedback without AI
- You're checking for common Kotlin anti-patterns
- You want code metrics

### 2. Suggest (AI Optional)

Get comprehensive suggestions combining static analysis with AI:

```bash
./gradlew run --args="suggest path/to/file.kt"
```

**Use this when:**
- You want both static and AI analysis
- Works without AI (falls back to static analysis only)

## AI-Powered Features

To use AI features, set your API key:

```bash
export OPENAI_API_KEY="your-key-here"
```

### 3. Explain Code

Get an explanation of what code does:

```bash
./gradlew run --args="explain examples/GoodExample.kt"
```

**Use this when:**
- You're learning new code
- You need to understand complex logic
- You're onboarding to a new codebase

### 4. Improve Code

Get refactoring suggestions and improved versions:

```bash
./gradlew run --args="improve examples/BadExample.kt"

# Focus on specific aspects
./gradlew run --args="improve MyClass.kt -f performance"
./gradlew run --args="improve MyClass.kt -f readability"
```

**Use this when:**
- You want to refactor code
- You're learning better patterns
- You want optimization suggestions

### 5. Generate Code

Generate code from descriptions:

```bash
./gradlew run --args="'generate \"Create a data class for a Book with title, author, and year\"'"

# Save to file
./gradlew run --args="'generate \"REST client for GitHub API\" -o GitHubClient.kt'"
```

**Use this when:**
- You're starting a new feature
- You need boilerplate code
- You want to explore different approaches

## Common Workflows

### Workflow 1: Code Review

```bash
# Check for issues
./gradlew run --args="analyze MyCode.kt"

# Get comprehensive suggestions
./gradlew run --args="suggest MyCode.kt"

# Get improvement ideas
./gradlew run --args="improve MyCode.kt"
```

### Workflow 2: Learning

```bash
# Understand code
./gradlew run --args="explain ComplexClass.kt"

# See better version
./gradlew run --args="improve ComplexClass.kt"
```

### Workflow 3: Development

```bash
# Generate starter code
./gradlew run --args="'generate \"User repository with CRUD operations\" -o UserRepository.kt'"

# Analyze what was generated
./gradlew run --args="analyze UserRepository.kt"

# Improve if needed
./gradlew run --args="improve UserRepository.kt"
```

## Tips & Tricks

### Tip 1: Use Without AI

The analyzer works great without any AI configuration:

```bash
./gradlew run --args="analyze MyCode.kt"
```

### Tip 2: Focus Improvements

Tell the AI what to focus on:

```bash
./gradlew run --args="improve MyCode.kt -f performance"
./gradlew run --args="improve MyCode.kt -f error-handling"
./gradlew run --args="improve MyCode.kt -f testing"
```

### Tip 3: Batch Analysis

Analyze multiple files:

```bash
for file in src/**/*.kt; do
  ./gradlew run --args="analyze $file" --quiet
done
```

### Tip 4: Use Examples

Learn from the included examples:

```bash
# Compare bad vs good
./gradlew run --args="analyze examples/BadExample.kt"
./gradlew run --args="analyze examples/GoodExample.kt"
```

### Tip 5: Save Output

Save analysis results:

```bash
./gradlew run --args="analyze MyCode.kt" > analysis-report.txt
```

## What the Analyzer Checks

Static analysis detects:

| Check | Severity | What It Catches |
|-------|----------|-----------------|
| `!!` operator | ⚠️ WARNING | Potential NullPointerException |
| `var` over `val` | ℹ️ INFO | Mutability that could be immutable |
| Explicit null checks | ℹ️ INFO | Code that could use safe calls |
| `Thread.sleep()` | 💡 IMPROVEMENT | Blocking calls in async code |
| Missing docs | ℹ️ INFO | Public APIs without documentation |
| Long lines | ℹ️ INFO | Lines exceeding 120 characters |

## Troubleshooting

### Issue: "AI service not available"

**Solution:** Set your API key:
```bash
export OPENAI_API_KEY="your-key"
```

### Issue: Build fails

**Solution:** Ensure Java 11+ is installed:
```bash
java -version
./gradlew clean build
```

### Issue: Command not found

**Solution:** Use the full Gradle command:
```bash
./gradlew run --args="your-command"
```

## Next Steps

1. ✅ Try analyzing your own code
2. ✅ Experiment with AI features
3. ✅ Read the full [README.md](README.md)
4. ✅ Check out [EXAMPLES.md](examples/EXAMPLES.md)
5. ✅ Contribute! See [CONTRIBUTING.md](CONTRIBUTING.md)

## Need Help?

- 📖 Read the full [README.md](README.md)
- 💡 Check [examples/](examples/) for demonstrations
- 🤝 Read [CONTRIBUTING.md](CONTRIBUTING.md) to contribute
- 🐛 Report issues on GitHub

---

Happy coding! 🚀
