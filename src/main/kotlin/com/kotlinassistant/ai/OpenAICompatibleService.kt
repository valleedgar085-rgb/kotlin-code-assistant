package com.kotlinassistant.ai

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kotlinassistant.models.AIRequest
import com.kotlinassistant.models.AIResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

/**
 * OpenAI-compatible AI service implementation
 * Supports OpenAI, Anthropic (via compatibility layer), and other compatible APIs
 */
class OpenAICompatibleService(
    private val apiKey: String = System.getenv("OPENAI_API_KEY") ?: "",
    private val baseUrl: String = System.getenv("AI_BASE_URL") ?: "https://api.openai.com/v1",
    private val model: String = System.getenv("AI_MODEL") ?: "gpt-4"
) : AIService {
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
    
    private val gson = Gson()
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    
    override suspend fun complete(request: AIRequest): Result<AIResponse> {
        if (!isAvailable()) {
            return Result.failure(IllegalStateException("AI service is not configured. Set OPENAI_API_KEY environment variable."))
        }
        
        return try {
            val requestBody = buildRequestBody(request)
            val httpRequest = Request.Builder()
                .url("$baseUrl/chat/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody(jsonMediaType))
                .build()
            
            val response = client.newCall(httpRequest).execute()
            
            if (!response.isSuccessful) {
                return Result.failure(Exception("AI API request failed: ${response.code} - ${response.message}"))
            }
            
            val responseBody = response.body?.string() ?: ""
            val aiResponse = parseResponse(responseBody)
            Result.success(aiResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun isAvailable(): Boolean {
        return apiKey.isNotBlank()
    }
    
    private fun buildRequestBody(request: AIRequest): String {
        val messages = mutableListOf<Map<String, String>>()
        
        if (request.context != null) {
            messages.add(mapOf("role" to "system", "content" to request.context))
        }
        
        messages.add(mapOf("role" to "user", "content" to request.prompt))
        
        val requestMap = mapOf(
            "model" to model,
            "messages" to messages,
            "max_tokens" to request.maxTokens,
            "temperature" to 0.7
        )
        
        return gson.toJson(requestMap)
    }
    
    private fun parseResponse(responseBody: String): AIResponse {
        val jsonResponse = gson.fromJson(responseBody, JsonObject::class.java)
        val choices = jsonResponse.getAsJsonArray("choices")
        val firstChoice = choices[0].asJsonObject
        val message = firstChoice.getAsJsonObject("message")
        val content = message.get("content").asString
        
        val usage = jsonResponse.getAsJsonObject("usage")
        val tokensUsed = usage?.get("total_tokens")?.asInt ?: 0
        
        return AIResponse(
            content = content,
            model = model,
            tokensUsed = tokensUsed
        )
    }
}
