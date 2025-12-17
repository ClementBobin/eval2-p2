package com.diiage.template

import android.app.Application
import com.diiage.template.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Custom Application class for initializing Koin dependency injection.
 */
class Application : Application() {
    /** Initializes Koin with the application context and modules. */
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin events (optional - use Level.NONE for production)
            androidLogger(level = Level.ERROR)

            // Inject Android context
            androidContext(this@Application)

            // Load your modules
            modules(appModule)
        }
    }
}