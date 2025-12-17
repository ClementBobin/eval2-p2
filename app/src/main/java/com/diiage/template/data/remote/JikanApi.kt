package com.diiage.template.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import com.diiage.template.data.dto.WeatherResponseDto

internal class WeatherApi(private val client: HttpClient) {

    /**
     * Fetches the weather forecast for the specified city.
     *
     * @param city The city to fetch the weather forecast for.
     * @return [WeatherResponseDto] containing the weather forecast details.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getWeatherForecast(city: String): WeatherResponseDto = client.getWeatherForecast(city)
}

private suspend fun HttpClient.getWeatherForecast(city: String): WeatherResponseDto =
    get("weather/forecast/$city")
        .accept(HttpStatusCode.OK)
        .body()