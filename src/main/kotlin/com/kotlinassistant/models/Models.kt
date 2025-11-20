package com.kotlinassistant.models

/**
 * Represents a code snippet to be analyzed or improved
 */
data class CodeSnippet(
    val code: String,
    val language: String = "kotlin",
    val filePath: String? = null
)

/**
 * Represents a suggestion from the AI assistant
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

/**
 * Request to the AI service
 */
data class AIRequest(
    val prompt: String,
    val context: String? = null,
    val maxTokens: Int = 2000
)

/**
 * Response from the AI service
 */
data class AIResponse(
    val content: String,
    val model: String,
    val tokensUsed: Int = 0
)
