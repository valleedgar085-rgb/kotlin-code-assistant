# Kotlin Code Assistant - Examples

This directory contains example Kotlin files to demonstrate the capabilities of the Kotlin Code Assistant.

## Files

### BadExample.kt

Contains code with several issues that the analyzer will detect:
- Use of `!!` operator (null assertion)
- Use of `var` instead of `val`
- Explicit null checks instead of safe calls
- `Thread.sleep()` instead of coroutines
- Missing documentation

**Try it:**
```bash
./gradlew run --args="analyze examples/BadExample.kt"
```

Expected output will show 5-6 suggestions for improvements.

### GoodExample.kt

Contains well-written Kotlin code following best practices:
- Proper null safety with safe calls and elvis operator
- Immutable variables (`val`)
- KDoc documentation
- Proper use of coroutines
- Data validation in `init` blocks

**Try it:**
```bash
./gradlew run --args="analyze examples/GoodExample.kt"
```

Expected output should show minimal or no issues.

## More Examples

### Testing the Analyzer

```bash
# Analyze a file
./gradlew run --args="analyze examples/BadExample.kt"

# Compare good vs bad
./gradlew run --args="analyze examples/BadExample.kt"
./gradlew run --args="analyze examples/GoodExample.kt"
```

### Using AI Features (Requires OPENAI_API_KEY)

```bash
# Explain code
export OPENAI_API_KEY="your-key"
./gradlew run --args="explain examples/GoodExample.kt"

# Get improvement suggestions
./gradlew run --args="improve examples/BadExample.kt"

# Comprehensive analysis
./gradlew run --args="suggest examples/BadExample.kt"
```

### Generate Code

```bash
export OPENAI_API_KEY="your-key"

# Generate a simple function
./gradlew run --args="'generate \"Create a function to validate email addresses\"'"

# Generate and save to file
./gradlew run --args="'generate \"Create a REST client for GitHub API\" -o GitHubClient.kt'"
```

## Creating Your Own Examples

Feel free to add your own Kotlin files to this directory and test them with the analyzer!

### Tips

1. **Start with simple code** - Test basic patterns first
2. **Introduce issues deliberately** - See what the analyzer catches
3. **Compare results** - Try the same logic written in different ways
4. **Use AI features** - Get suggestions for improvements
5. **Learn from output** - The suggestions explain best practices

## Common Issues Detected

The static analyzer checks for:

| Issue | Severity | Example |
|-------|----------|---------|
| `!!` operator | WARNING | `name!!.length` |
| `var` over `val` | INFO | `var x = 1` |
| Explicit null checks | INFO | `if (x != null)` |
| `Thread.sleep()` | IMPROVEMENT | `Thread.sleep(100)` |
| Missing docs | INFO | Public functions without KDoc |
| Long lines | INFO | Lines > 120 characters |

## Best Practices Demonstrated

The good example shows:

- ✅ Safe null handling with `?.` and `?:`
- ✅ Immutable variables with `val`
- ✅ KDoc documentation
- ✅ Coroutines for async operations
- ✅ Data validation in `init` blocks
- ✅ Clear, readable code structure

## Experiment!

Try modifying these files and see how the suggestions change. The assistant is designed to help you learn and improve your Kotlin code quality.
