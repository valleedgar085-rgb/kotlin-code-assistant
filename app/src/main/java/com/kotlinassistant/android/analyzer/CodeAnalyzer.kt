package com.kotlinassistant.android.analyzer

import com.kotlinassistant.android.models.CodeSnippet
import com.kotlinassistant.android.models.CodeSuggestion
import com.kotlinassistant.android.models.SuggestionSeverity

/**
 * Analyzes code for common issues and patterns
 */
class CodeAnalyzer {
    
    /**
     * Analyze a code snippet and return suggestions
     */
    fun analyze(snippet: CodeSnippet): List<CodeSuggestion> {
        val suggestions = mutableListOf<CodeSuggestion>()
        
        when (snippet.language.lowercase()) {
            "kotlin" -> suggestions.addAll(analyzeKotlin(snippet))
            else -> suggestions.addAll(analyzeGeneric(snippet))
        }
        
        return suggestions
    }
    
    private fun analyzeKotlin(snippet: CodeSnippet): List<CodeSuggestion> {
        val suggestions = mutableListOf<CodeSuggestion>()
        val code = snippet.code
        
        // Cache commonly used checks to avoid multiple passes
        val hasNullAssertion = code.contains("!!")
        val hasVar = code.contains("var ")
        val hasVal = code.contains("val ")
        val hasExplicitNullCheck = code.contains("== null") || code.contains("!= null")
        val hasThreadSleep = code.contains("Thread.sleep")
        val hasFun = code.contains("fun ")
        val hasKDoc = code.contains("/**")
        
        // Check for common Kotlin best practices
        if (hasNullAssertion) {
            suggestions.add(
                CodeSuggestion(
                    title = "Avoid !! operator",
                    description = "The !! operator can cause NullPointerException. Consider using safe calls (?.) or elvis operator (?:) instead.",
                    severity = SuggestionSeverity.WARNING
                )
            )
        }
        
        if (hasVar && !hasVal) {
            suggestions.add(
                CodeSuggestion(
                    title = "Prefer val over var",
                    description = "Use 'val' for immutable variables when possible to make your code more predictable and thread-safe.",
                    severity = SuggestionSeverity.INFO
                )
            )
        }
        
        if (hasExplicitNullCheck) {
            suggestions.add(
                CodeSuggestion(
                    title = "Use safe calls",
                    description = "Consider using safe call operator (?.) or let function instead of explicit null checks.",
                    severity = SuggestionSeverity.INFO
                )
            )
        }
        
        if (hasThreadSleep) {
            suggestions.add(
                CodeSuggestion(
                    title = "Use coroutines for delays",
                    description = "Consider using kotlinx.coroutines delay() instead of Thread.sleep() for better async handling.",
                    severity = SuggestionSeverity.IMPROVEMENT
                )
            )
        }
        
        // Check for missing documentation on public functions
        // Only parse lines if we have functions but no KDoc
        if (hasFun && !hasKDoc) {
            // Single pass through lines to check for public functions
            val hasPotentialPublicFun = code.lineSequence()
                .any { line ->
                    val trimmed = line.trim()
                    trimmed.startsWith("fun ") && !trimmed.startsWith("private fun")
                }
            if (hasPotentialPublicFun) {
                suggestions.add(
                    CodeSuggestion(
                        title = "Add documentation",
                        description = "Consider adding KDoc documentation for public functions to improve code maintainability.",
                        severity = SuggestionSeverity.INFO
                    )
                )
            }
        }
        
        return suggestions
    }
    
    private fun analyzeGeneric(snippet: CodeSnippet): List<CodeSuggestion> {
        val suggestions = mutableListOf<CodeSuggestion>()
        
        // Generic code quality checks - use sequence for better performance
        val hasLongLines = snippet.code.lineSequence().any { it.length > 120 }
        if (hasLongLines) {
            suggestions.add(
                CodeSuggestion(
                    title = "Long lines detected",
                    description = "Some lines exceed 120 characters. Consider breaking them for better readability.",
                    severity = SuggestionSeverity.INFO
                )
            )
        }
        
        return suggestions
    }
    
    /**
     * Get complexity metrics for the code
     */
    fun getComplexityMetrics(snippet: CodeSnippet): Map<String, Int> {
        val code = snippet.code
        val lines = code.lines()
        
        return mapOf(
            "lines" to lines.size,
            "nonEmptyLines" to lines.count { it.isNotBlank() },
            "functions" to code.split("fun ").size - 1,
            "classes" to code.split("class ").size - 1
        )
    }
}
