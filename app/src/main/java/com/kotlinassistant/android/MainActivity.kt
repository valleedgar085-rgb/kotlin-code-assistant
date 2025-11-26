package com.kotlinassistant.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView
import com.kotlinassistant.android.analyzer.CodeAnalyzer
import com.kotlinassistant.android.models.CodeSnippet
import com.kotlinassistant.android.models.SuggestionSeverity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    
    private lateinit var codeInput: TextInputEditText
    private lateinit var analyzeButton: MaterialButton
    private lateinit var resultsText: TextView
    private val codeAnalyzer = CodeAnalyzer()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        codeInput = findViewById(R.id.codeInput)
        analyzeButton = findViewById(R.id.analyzeButton)
        resultsText = findViewById(R.id.resultsText)
        
        analyzeButton.setOnClickListener {
            analyzeCode()
        }
    }
    
    private fun analyzeCode() {
        val code = codeInput.text?.toString() ?: ""
        
        if (code.isBlank()) {
            Toast.makeText(this, R.string.error_empty_code, Toast.LENGTH_SHORT).show()
            return
        }
        
        analyzeButton.isEnabled = false
        resultsText.text = getString(R.string.analyzing)
        
        lifecycleScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    val snippet = CodeSnippet(code, "kotlin")
                    val suggestions = codeAnalyzer.analyze(snippet)
                    val metrics = codeAnalyzer.getComplexityMetrics(snippet)
                    Pair(suggestions, metrics)
                }
                
                displayResults(result.first, result.second)
            } catch (e: Exception) {
                resultsText.text = "Error analyzing code: ${e.message}"
            } finally {
                analyzeButton.isEnabled = true
            }
        }
    }
    
    private fun displayResults(suggestions: List<com.kotlinassistant.android.models.CodeSuggestion>, metrics: Map<String, Int>) {
        val resultBuilder = StringBuilder()
        
        if (suggestions.isEmpty()) {
            resultBuilder.append(getString(R.string.no_issues))
        } else {
            resultBuilder.append(getString(R.string.found_suggestions, suggestions.size))
            resultBuilder.append("\n")
            
            suggestions.forEachIndexed { index, suggestion ->
                val icon = when (suggestion.severity) {
                    SuggestionSeverity.ERROR -> "❌"
                    SuggestionSeverity.WARNING -> "⚠️"
                    SuggestionSeverity.INFO -> "ℹ️"
                    SuggestionSeverity.IMPROVEMENT -> "💡"
                }
                resultBuilder.append(
                    getString(
                        R.string.suggestion_format,
                        icon,
                        index + 1,
                        suggestion.title,
                        suggestion.description
                    )
                )
            }
        }
        
        resultBuilder.append("\n")
        resultBuilder.append(getString(R.string.code_metrics))
        resultBuilder.append("\n")
        resultBuilder.append(getString(R.string.lines, metrics["lines"] ?: 0))
        resultBuilder.append("\n")
        resultBuilder.append(getString(R.string.non_empty_lines, metrics["nonEmptyLines"] ?: 0))
        resultBuilder.append("\n")
        resultBuilder.append(getString(R.string.functions, metrics["functions"] ?: 0))
        resultBuilder.append("\n")
        resultBuilder.append(getString(R.string.classes, metrics["classes"] ?: 0))
        
        resultsText.text = resultBuilder.toString()
    }
}
