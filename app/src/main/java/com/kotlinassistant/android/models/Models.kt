package com.kotlinassistant.android.models

/**
 * Represents a code snippet to be analyzed or improved
 */
data class CodeSnippet(
    val code: String,
    val language: String = "kotlin",
    val filePath: String? = null
)

/**
 * Represents a suggestion from the code analyzer
 */
data class CodeSuggestion(
    val title: String,
    val description: String,
    val suggestedCode: String? = null,
    val severity: SuggestionSeverity = SuggestionSeverity.INFO
)

enum class SuggestionSeverity {
    ERROR,
    WARNING,
    INFO,
    IMPROVEMENT
}
