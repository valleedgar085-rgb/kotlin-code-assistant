package com.kotlinassistant

import com.kotlinassistant.analyzer.CodeAnalyzer
import com.kotlinassistant.models.CodeSnippet
import kotlin.system.measureTimeMillis
import kotlin.test.Test
import kotlin.test.assertTrue

class PerformanceTest {
    
    private val analyzer = CodeAnalyzer()
    
    @Test
    fun `test analyzer performance on large code snippet`() {
        // Generate a large code snippet to test performance
        val largeCode = buildString {
            repeat(1000) { i ->
                appendLine("fun function$i(param: String?): String {")
                appendLine("    if (param != null) {")
                appendLine("        val result = param!!.uppercase()")
                appendLine("        var temp = result")
                appendLine("        Thread.sleep(100)")
                appendLine("        return temp")
                appendLine("    }")
                appendLine("    return \"\"")
                appendLine("}")
                appendLine()
            }
        }
        
        val snippet = CodeSnippet(largeCode, "kotlin")
        
        // Measure time for analysis
        val timeMillis = measureTimeMillis {
            val suggestions = analyzer.analyze(snippet)
            // Should detect multiple issues
            assertTrue(suggestions.isNotEmpty())
        }
        
        // Analysis should complete in reasonable time (< 500ms for 1000 functions)
        println("Analysis of 1000 functions took $timeMillis ms")
        assertTrue(timeMillis < 500, "Analysis took too long: $timeMillis ms")
    }
    
    @Test
    fun `test complexity metrics performance`() {
        val largeCode = buildString {
            repeat(500) { i ->
                appendLine("class Class$i {")
                appendLine("    fun method1() { }")
                appendLine("    fun method2() { }")
                appendLine("}")
            }
        }
        
        val snippet = CodeSnippet(largeCode, "kotlin")
        
        // Measure time for metrics calculation
        val timeMillis = measureTimeMillis {
            val metrics = analyzer.getComplexityMetrics(snippet)
            assertTrue(metrics["classes"]!! > 0)
            assertTrue(metrics["functions"]!! > 0)
        }
        
        println("Metrics calculation for 500 classes took $timeMillis ms")
        assertTrue(timeMillis < 200, "Metrics calculation took too long: $timeMillis ms")
    }
    
    @Test
    fun `test generic analyzer performance with long lines`() {
        val codeWithLongLines = buildString {
            repeat(1000) { i ->
                append("val variable$i = ")
                append("\"${"x".repeat(130)}\"")
                appendLine()
            }
        }
        
        val snippet = CodeSnippet(codeWithLongLines, "python")
        
        // Measure time for generic analysis
        val timeMillis = measureTimeMillis {
            val suggestions = analyzer.analyze(snippet)
            assertTrue(suggestions.any { it.title.contains("Long lines") })
        }
        
        println("Generic analysis of 1000 long lines took $timeMillis ms")
        assertTrue(timeMillis < 100, "Generic analysis took too long: $timeMillis ms")
    }
}
