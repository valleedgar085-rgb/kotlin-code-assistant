package com.kotlinassistant.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.kotlinassistant.ai.OpenAICompatibleService
import com.kotlinassistant.analyzer.CodeAnalyzer
import com.kotlinassistant.models.CodeSnippet
import com.kotlinassistant.models.SuggestionSeverity
import com.kotlinassistant.suggestions.SuggestionEngine
import kotlinx.coroutines.runBlocking
import java.io.File

/**
 * Main CLI command
 */
class KotlinAssistantCLI : CliktCommand(
    name = "kotlin-assistant",
    help = "AI-powered Kotlin code assistant"
) {
    override fun run() = Unit
}

/**
 * Analyze command - analyze code for issues
 */
class AnalyzeCommand : CliktCommand(
    name = "analyze",
    help = "Analyze code and get suggestions"
) {
    private val file by argument(help = "File to analyze").file(mustExist = true, canBeDir = false)
    private val language by option("-l", "--language", help = "Programming language").default("kotlin")
    
    override fun run() = runBlocking {
        val code = file.readText()
        val snippet = CodeSnippet(code, language, file.absolutePath)
        
        echo("Analyzing ${file.name}...")
        
        val analyzer = CodeAnalyzer()
        val suggestions = analyzer.analyze(snippet)
        
        if (suggestions.isEmpty()) {
            echo("✓ No issues found!")
        } else {
            echo("\nFound ${suggestions.size} suggestion(s):\n")
            suggestions.forEachIndexed { index, suggestion ->
                val icon = when (suggestion.severity) {
                    SuggestionSeverity.ERROR -> "❌"
                    SuggestionSeverity.WARNING -> "⚠️"
                    SuggestionSeverity.INFO -> "ℹ️"
                    SuggestionSeverity.IMPROVEMENT -> "💡"
                }
                echo("$icon ${index + 1}. ${suggestion.title}")
                echo("   ${suggestion.description}\n")
            }
        }
        
        val metrics = analyzer.getComplexityMetrics(snippet)
        echo("\nCode Metrics:")
        echo("  Lines: ${metrics["lines"]}")
        echo("  Non-empty lines: ${metrics["nonEmptyLines"]}")
        echo("  Functions: ${metrics["functions"]}")
        echo("  Classes: ${metrics["classes"]}")
    }
}

/**
 * Explain command - explain what code does
 */
class ExplainCommand : CliktCommand(
    name = "explain",
    help = "Explain what the code does using AI"
) {
    private val file by argument(help = "File to explain").file(mustExist = true, canBeDir = false)
    private val language by option("-l", "--language", help = "Programming language").default("kotlin")
    
    override fun run() = runBlocking {
        val code = file.readText()
        val snippet = CodeSnippet(code, language, file.absolutePath)
        
        echo("Explaining ${file.name}...\n")
        
        val aiService = OpenAICompatibleService()
        if (!aiService.isAvailable()) {
            echo("❌ AI service not available. Please set OPENAI_API_KEY environment variable.")
            return@runBlocking
        }
        
        val engine = SuggestionEngine(aiService)
        val result = engine.explainCode(snippet)
        
        result.fold(
            onSuccess = { explanation ->
                echo(explanation)
            },
            onFailure = { error ->
                echo("❌ Error: ${error.message}")
            }
        )
    }
}

/**
 * Improve command - get AI suggestions for code improvement
 */
class ImproveCommand : CliktCommand(
    name = "improve",
    help = "Get AI-powered suggestions to improve code"
) {
    private val file by argument(help = "File to improve").file(mustExist = true, canBeDir = false)
    private val language by option("-l", "--language", help = "Programming language").default("kotlin")
    private val focus by option("-f", "--focus", help = "What to focus on (e.g., 'performance', 'readability')")
    
    override fun run() = runBlocking {
        val code = file.readText()
        val snippet = CodeSnippet(code, language, file.absolutePath)
        
        echo("Getting improvement suggestions for ${file.name}...\n")
        
        val aiService = OpenAICompatibleService()
        if (!aiService.isAvailable()) {
            echo("❌ AI service not available. Please set OPENAI_API_KEY environment variable.")
            return@runBlocking
        }
        
        val engine = SuggestionEngine(aiService)
        val result = engine.improveCode(snippet, focus)
        
        result.fold(
            onSuccess = { improvements ->
                echo(improvements)
            },
            onFailure = { error ->
                echo("❌ Error: ${error.message}")
            }
        )
    }
}

/**
 * Generate command - generate code from description
 */
class GenerateCommand : CliktCommand(
    name = "generate",
    help = "Generate code from a description using AI"
) {
    private val description by argument(help = "Description of what to generate")
    private val language by option("-l", "--language", help = "Programming language").default("kotlin")
    private val output by option("-o", "--output", help = "Output file").file()
    
    override fun run() = runBlocking {
        echo("Generating $language code...\n")
        
        val aiService = OpenAICompatibleService()
        if (!aiService.isAvailable()) {
            echo("❌ AI service not available. Please set OPENAI_API_KEY environment variable.")
            return@runBlocking
        }
        
        val engine = SuggestionEngine(aiService)
        val result = engine.generateCode(description, language)
        
        result.fold(
            onSuccess = { code ->
                if (output != null) {
                    output!!.writeText(code)
                    echo("✓ Code generated and saved to ${output!!.absolutePath}")
                } else {
                    echo(code)
                }
            },
            onFailure = { error ->
                echo("❌ Error: ${error.message}")
            }
        )
    }
}

/**
 * Suggest command - get comprehensive suggestions using AI and static analysis
 */
class SuggestCommand : CliktCommand(
    name = "suggest",
    help = "Get comprehensive code suggestions (static analysis + AI)"
) {
    private val file by argument(help = "File to analyze").file(mustExist = true, canBeDir = false)
    private val language by option("-l", "--language", help = "Programming language").default("kotlin")
    
    override fun run() = runBlocking {
        val code = file.readText()
        val snippet = CodeSnippet(code, language, file.absolutePath)
        
        echo("Getting suggestions for ${file.name}...\n")
        
        val aiService = OpenAICompatibleService()
        val engine = SuggestionEngine(aiService)
        
        val result = engine.getSuggestions(snippet)
        
        result.fold(
            onSuccess = { suggestions ->
                if (suggestions.isEmpty()) {
                    echo("✓ No issues found!")
                } else {
                    echo("Found ${suggestions.size} suggestion(s):\n")
                    suggestions.forEachIndexed { index, suggestion ->
                        val icon = when (suggestion.severity) {
                            SuggestionSeverity.ERROR -> "❌"
                            SuggestionSeverity.WARNING -> "⚠️"
                            SuggestionSeverity.INFO -> "ℹ️"
                            SuggestionSeverity.IMPROVEMENT -> "💡"
                        }
                        echo("$icon ${index + 1}. ${suggestion.title}")
                        echo("   ${suggestion.description}\n")
                    }
                }
            },
            onFailure = { error ->
                echo("❌ Error: ${error.message}")
            }
        )
    }
}
