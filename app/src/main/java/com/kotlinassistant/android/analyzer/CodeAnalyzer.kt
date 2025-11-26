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
        
        // Check for common Kotlin best practices
        if (code.contains("!!")) {
            suggestions.add(
                CodeSuggestion(
                    title = "Avoid !! operator",
                    description = "The !! operator can cause NullPointerException. Consider using safe calls (?.) or elvis operator (?:) instead.",
                    severity = SuggestionSeverity.WARNING
                )
            )
        }
        
        if (code.contains("var ") && !code.contains("val ")) {
            suggestions.add(
                CodeSuggestion(
                    title = "Prefer val over var",
                    description = "Use 'val' for immutable variables when possible to make your code more predictable and thread-safe.",
                    severity = SuggestionSeverity.INFO
                )
            )
        }
        
        if (code.contains("== null") || code.contains("!= null")) {
            suggestions.add(
                CodeSuggestion(
                    title = "Use safe calls",
                    description = "Consider using safe call operator (?.) or let function instead of explicit null checks.",
                    severity = SuggestionSeverity.INFO
                )
            )
        }
        
        if (code.contains("Thread.sleep")) {
            suggestions.add(
                CodeSuggestion(
                    title = "Use coroutines for delays",
                    description = "Consider using kotlinx.coroutines delay() instead of Thread.sleep() for better async handling.",
                    severity = SuggestionSeverity.IMPROVEMENT
                )
            )
        }
        
        // Check for missing documentation on public functions
        if (code.contains("fun ") && !code.contains("/**")) {
            val hasPotentialPublicFun = code.lines().any { 
                it.trim().startsWith("fun ") && !it.trim().startsWith("private fun")
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
        val code = snippet.code
        
        // Generic code quality checks
        if (code.lines().any { it.length > 120 }) {
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
        return mapOf(
            "lines" to code.lines().size,
            "nonEmptyLines" to code.lines().count { it.trim().isNotEmpty() },
            "functions" to code.split("fun ").size - 1,
            "classes" to code.split("class ").size - 1
        )
    }
}
