package com.kotlinassistant.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ModelsTest {
    
    @Test
    fun `test CodeSnippet creation`() {
        val snippet = CodeSnippet(
            code = "fun hello() { }",
            language = "kotlin",
            filePath = "/test.kt"
        )
        
        assertEquals("fun hello() { }", snippet.code)
        assertEquals("kotlin", snippet.language)
        assertEquals("/test.kt", snippet.filePath)
    }
    
    @Test
    fun `test CodeSuggestion severity levels`() {
        val error = CodeSuggestion("Error", "desc", severity = SuggestionSeverity.ERROR)
        val warning = CodeSuggestion("Warning", "desc", severity = SuggestionSeverity.WARNING)
        val info = CodeSuggestion("Info", "desc", severity = SuggestionSeverity.INFO)
        val improvement = CodeSuggestion("Improvement", "desc", severity = SuggestionSeverity.IMPROVEMENT)
        
        assertEquals(SuggestionSeverity.ERROR, error.severity)
        assertEquals(SuggestionSeverity.WARNING, warning.severity)
        assertEquals(SuggestionSeverity.INFO, info.severity)
        assertEquals(SuggestionSeverity.IMPROVEMENT, improvement.severity)
    }
    
    @Test
    fun `test AIRequest defaults`() {
        val request = AIRequest(prompt = "Test prompt")
        
        assertEquals("Test prompt", request.prompt)
        assertEquals(null, request.context)
        assertEquals(2000, request.maxTokens)
    }
    
    @Test
    fun `test AIResponse creation`() {
        val response = AIResponse(
            content = "Response content",
            model = "gpt-4",
            tokensUsed = 150
        )
        
        assertNotNull(response)
        assertEquals("Response content", response.content)
        assertEquals("gpt-4", response.model)
        assertEquals(150, response.tokensUsed)
    }
}
