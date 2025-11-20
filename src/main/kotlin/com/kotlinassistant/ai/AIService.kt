package com.kotlinassistant.ai

import com.kotlinassistant.models.AIRequest
import com.kotlinassistant.models.AIResponse

/**
 * Interface for AI service implementations
 */
interface AIService {
    /**
     * Send a request to the AI service
     */
    suspend fun complete(request: AIRequest): Result<AIResponse>
    
    /**
     * Check if the AI service is configured and available
     */
    fun isAvailable(): Boolean
}
