package com.kotlinassistant.analyzer

import com.kotlinassistant.models.CodeSnippet
import com.kotlinassistant.models.SuggestionSeverity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CodeAnalyzerTest {
    
    private val analyzer = CodeAnalyzer()
    
    @Test
    fun `test analyze detects null assertion operator`() {
        val code = """
            val name: String? = null
            val length = name!!.length
        """.trimIndent()
        
        val snippet = CodeSnippet(code, "kotlin")
        val suggestions = analyzer.analyze(snippet)
        
        assertTrue(suggestions.any { it.title.contains("!!") })
    }
    
    @Test
    fun `test analyze suggests val over var`() {
        val code = """
            var count = 0
            var total = 100
        """.trimIndent()
        
        val snippet = CodeSnippet(code, "kotlin")
        val suggestions = analyzer.analyze(snippet)
        
        assertTrue(suggestions.any { it.title.contains("val") })
    }
    
    @Test
    fun `test analyze detects explicit null checks`() {
        val code = """
            if (value != null) {
                println(value)
            }
        """.trimIndent()
        
        val snippet = CodeSnippet(code, "kotlin")
        val suggestions = analyzer.analyze(snippet)
        
        assertTrue(suggestions.any { it.title.contains("safe call") })
    }
    
    @Test
    fun `test analyze suggests coroutines over Thread sleep`() {
        val code = """
            Thread.sleep(1000)
        """.trimIndent()
        
        val snippet = CodeSnippet(code, "kotlin")
        val suggestions = analyzer.analyze(snippet)
        
        assertTrue(suggestions.any { it.title.contains("coroutines") })
    }
    
    @Test
    fun `test complexity metrics`() {
        val code = """
            class Calculator {
                fun add(a: Int, b: Int): Int {
                    return a + b
                }
                
                fun subtract(a: Int, b: Int): Int {
                    return a - b
                }
            }
        """.trimIndent()
        
        val snippet = CodeSnippet(code, "kotlin")
        val metrics = analyzer.getComplexityMetrics(snippet)
        
        assertEquals(9, metrics["lines"])
        assertTrue((metrics["functions"] ?: 0) >= 2)
        assertTrue((metrics["classes"] ?: 0) >= 1)
    }
    
    @Test
    fun `test clean code returns no suggestions`() {
        val code = """
            /**
             * Calculates the sum of two numbers
             */
            fun add(a: Int, b: Int): Int {
                return a + b
            }
        """.trimIndent()
        
        val snippet = CodeSnippet(code, "kotlin")
        val suggestions = analyzer.analyze(snippet)
        
        // Should have minimal or no critical suggestions
        assertTrue(suggestions.none { it.severity == SuggestionSeverity.ERROR })
    }
}
