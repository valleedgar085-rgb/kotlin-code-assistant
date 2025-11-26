package com.kotlinassistant.suggestions

import com.kotlinassistant.ai.AIService
import com.kotlinassistant.analyzer.CodeAnalyzer
import com.kotlinassistant.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Generates code suggestions using AI and static analysis
 */
class SuggestionEngine(
    private val aiService: AIService,
    private val analyzer: CodeAnalyzer = CodeAnalyzer()
) {
    
    /**
     * Get suggestions for a code snippet
     */
    suspend fun getSuggestions(snippet: CodeSnippet): Result<List<CodeSuggestion>> = withContext(Dispatchers.IO) {
        try {
            val staticSuggestions = analyzer.analyze(snippet)
            
            // If AI is not available, return only static analysis
            if (!aiService.isAvailable()) {
                return@withContext Result.success(staticSuggestions)
            }
            
            // Get AI-powered suggestions
            val aiSuggestions = getAISuggestions(snippet).getOrElse { 
                // If AI fails, still return static suggestions
                return@withContext Result.success(staticSuggestions)
            }
            
            Result.success(staticSuggestions + aiSuggestions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Explain a code snippet using AI
     */
    suspend fun explainCode(snippet: CodeSnippet): Result<String> = withContext(Dispatchers.IO) {
        if (!aiService.isAvailable()) {
            return@withContext Result.failure(IllegalStateException("AI service not available"))
        }
        
        val prompt = """
            Explain the following ${snippet.language} code in a clear and concise way:
            
            ```${snippet.language}
            ${snippet.code}
            ```
            
            Include:
            1. What the code does
            2. Key concepts used
            3. Any potential issues or improvements
        """.trimIndent()
        
        val request = AIRequest(
            prompt = prompt,
            context = "You are a helpful programming assistant specializing in ${snippet.language}."
        )
        
        aiService.complete(request).map { it.content }
    }
    
    /**
     * Improve code using AI
     */
    suspend fun improveCode(snippet: CodeSnippet, focus: String? = null): Result<String> = withContext(Dispatchers.IO) {
        if (!aiService.isAvailable()) {
            return@withContext Result.failure(IllegalStateException("AI service not available"))
        }
        
        val focusInstruction = if (focus != null) {
            "Focus on: $focus"
        } else {
            "Focus on best practices, readability, and performance."
        }
        
        val prompt = """
            Improve the following ${snippet.language} code:
            
            ```${snippet.language}
            ${snippet.code}
            ```
            
            $focusInstruction
            
            Provide the improved code with explanations of the changes made.
        """.trimIndent()
        
        val request = AIRequest(
            prompt = prompt,
            context = "You are an expert ${snippet.language} developer. Provide clean, idiomatic code."
        )
        
        aiService.complete(request).map { it.content }
    }
    
    /**
     * Generate code from description using AI
     */
    suspend fun generateCode(description: String, language: String = "kotlin"): Result<String> = withContext(Dispatchers.IO) {
        if (!aiService.isAvailable()) {
            return@withContext Result.failure(IllegalStateException("AI service not available"))
        }
        
        val prompt = """
            Generate $language code for the following requirement:
            
            $description
            
            Provide clean, well-documented, and idiomatic code.
        """.trimIndent()
        
        val request = AIRequest(
            prompt = prompt,
            context = "You are an expert $language developer. Write clean, production-ready code."
        )
        
        aiService.complete(request).map { it.content }
    }
    
    private suspend fun getAISuggestions(snippet: CodeSnippet): Result<List<CodeSuggestion>> {
        val prompt = """
            Analyze the following ${snippet.language} code and provide suggestions for improvement:
            
            ```${snippet.language}
            ${snippet.code}
            ```
            
            Provide suggestions in the format:
            TITLE: <suggestion title>
            SEVERITY: <ERROR|WARNING|INFO|IMPROVEMENT>
            DESCRIPTION: <detailed description>
            ---
            
            Focus on code quality, best practices, potential bugs, and performance.
        """.trimIndent()
        
        val request = AIRequest(
            prompt = prompt,
            context = "You are a code review expert specializing in ${snippet.language}.",
            maxTokens = 1500
        )
        
        return aiService.complete(request).map { response ->
            parseSuggestions(response.content)
        }
    }
    
    private fun parseSuggestions(content: String): List<CodeSuggestion> {
        val suggestions = mutableListOf<CodeSuggestion>()
        val blocks = content.split("---").filter { it.trim().isNotEmpty() }
        
        for (block in blocks) {
            try {
                val lines = block.trim().lines()
                var title = ""
                var severity = SuggestionSeverity.INFO
                val descriptionBuilder = StringBuilder()
                var parsingDescription = false
                
                for (line in lines) {
                    when {
                        line.startsWith("TITLE:") -> {
                            title = line.substringAfter("TITLE:").trim()
                            parsingDescription = false
                        }
                        line.startsWith("SEVERITY:") -> {
                            val severityStr = line.substringAfter("SEVERITY:").trim()
                            severity = when (severityStr.uppercase()) {
                                "ERROR" -> SuggestionSeverity.ERROR
                                "WARNING" -> SuggestionSeverity.WARNING
                                "IMPROVEMENT" -> SuggestionSeverity.IMPROVEMENT
                                else -> SuggestionSeverity.INFO
                            }
                            parsingDescription = false
                        }
                        line.startsWith("DESCRIPTION:") -> {
                            descriptionBuilder.append(line.substringAfter("DESCRIPTION:").trim())
                            parsingDescription = true
                        }
                        parsingDescription && line.isNotBlank() -> {
                            descriptionBuilder.append('\n').append(line)
                        }
                    }
                }
                
                if (title.isNotEmpty()) {
                    suggestions.add(CodeSuggestion(title, descriptionBuilder.toString().trim(), null, severity))
                }
            } catch (e: Exception) {
                // Skip malformed suggestions
            }
        }
        
        return suggestions
    }
}
