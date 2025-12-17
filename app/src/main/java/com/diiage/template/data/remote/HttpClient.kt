package com.diiage.template.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createHttpClient(
    baseUrl: String,
    engine: HttpClientEngine = CIO.create()
): HttpClient = HttpClient(engine) {
    defaultRequest {
        url(baseUrl)
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        })
    }

    install(HttpTimeout) {
        connectTimeoutMillis = 15000
        socketTimeoutMillis = 15000
        requestTimeoutMillis = 15000
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("HTTP Client: $message")
            }
        }
        level = LogLevel.ALL
    }

    install(HttpCallValidator) {
        validateResponse { response ->
            println("Response status: ${response.status}")
            when (response.status.value) {
                429 -> throw HttpException.RateLimitExceededException("Rate limit exceeded: ${response.status}")
                in 400..499 -> throw HttpException.ClientError(response.status.value, "Client error: ${response.status}")
                in 500..599 -> throw HttpException.ServerError("Server error: ${response.status}")
            }
        }
    }
}

inline fun <reified T> HttpRequestBuilder.setBodyJson(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}

fun HttpResponse.accept(vararg codes: HttpStatusCode) = apply {
    println("Checking response status: $status, accepted codes: ${codes.joinToString()}")
    if (status !in codes) {
        throw HttpException.NotAccepted("HTTP $status not accepted. Expected: ${codes.joinToString()}")
    }
}

sealed class HttpException(message: String) : Exception(message) {
    class NotAccepted(message: String) : HttpException(message)
    class ClientError(val statusCode: Int, message: String) : HttpException(message)
    class ServerError(message: String) : HttpException(message)



    /**
     * Exception thrown when Jikan API rate limit is exceeded.
     *
     * Jikan API has a limit of 30 requests per minute and 3 requests per second.
     * This exception is caught so it be display in the UI layer to show appropriate user feedback.
     */
    class RateLimitExceededException(message: String) : Exception(message)
}