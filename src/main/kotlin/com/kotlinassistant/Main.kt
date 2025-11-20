package com.kotlinassistant

import com.github.ajalt.clikt.core.subcommands
import com.kotlinassistant.cli.*

/**
 * Main entry point for Kotlin Code Assistant
 */
fun main(args: Array<String>) {
    KotlinAssistantCLI()
        .subcommands(
            AnalyzeCommand(),
            ExplainCommand(),
            ImproveCommand(),
            GenerateCommand(),
            SuggestCommand()
        )
        .main(args)
}
