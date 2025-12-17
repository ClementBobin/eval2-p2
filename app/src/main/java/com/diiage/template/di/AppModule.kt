package com.diiage.template.di

import com.diiage.template.data.manager.*
import com.diiage.template.data.remote.*
import com.diiage.template.data.repository.*
import com.diiage.template.domain.repository.*
import com.diiage.template.data.remote.createHttpClient
import org.koin.dsl.module
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext

private const val RMAPI_URL = "https://api.jikan.moe/v4/"

/**
 * Koin dependency injection module for the application.
 *
 * This module defines all the dependencies that will be available for injection
 * throughout the application. It configures how dependencies are created and
 * their lifecycle (singleton, factory, etc.).
 *
 * ## Usage
 * This module should be loaded when initializing the Koin container in the application.
 *
 * ## Dependencies Included
 * - [AnimeRepository] as singleton using [AnimeRepositoryImpl] implementation
 *
 * @see org.koin.dsl.module
 * @see single
 * @see factory
 *
 * @sample
 * // Initialize Koin with this module
 * startKoin {
 *     modules(appModule)
 * }
 */
val appModule = module {
    // Single instance (singleton) of HttpClient configured for Jikan API
    single<HttpClient> {
        createHttpClient(
            baseUrl = RMAPI_URL
        )
    }

    // Single instance (singleton) of JikanService
    // Data layer - Jikan API
    single { JikanApi(get()) }
    single<AnimeRepository> { AnimeRepositoryImpl(get()) }

    // Manager (singleton - sound effects)
    single<SoundManagerRepository> { SoundManager(androidContext()) }

    // Manager (singleton - vibration/haptic feedback)
    single<VibrationManagerRepository> { VibrationManager(androidContext()) }
}