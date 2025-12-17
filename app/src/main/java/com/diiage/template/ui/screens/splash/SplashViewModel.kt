package com.diiage.template.ui.screens.splash

import android.app.Application
import com.diiage.template.ui.core.Destination
import com.diiage.template.ui.core.ViewModel
import kotlinx.coroutines.delay

/**
 * Contracts for the Splash screen, including UI state and actions.
 */
interface SplashContracts {
    /**
     * Data class representing the UI state for the Splash screen.
     */
    data class UiState(
        val isLoading: Boolean = true
    )
}

/**
 * ViewModel for managing the state and actions of the Splash screen.
 *
 * @param application The application context
 */
class SplashViewModel(
    application: Application
) : ViewModel<SplashContracts.UiState>(
    initialState = SplashContracts.UiState(),
    application = application
) {
    init {
        // Start the splash timer when ViewModel is created
        startSplashTimer()
    }

    /**
     * Starts a timer for the splash screen duration and navigates to the Home screen afterwards.
     */
    private fun startSplashTimer() {
        fetchData(
            source = {
                delay(2000) // 2 seconds splash duration
                true // Return a dummy value since we just need the delay
            },
            onResult = {
                onSuccess {
                    sendEvent(Destination.Home)
                }
                onFailure {
                    // Even if there's an error, navigate to Home after delay
                    sendEvent(Destination.Home)
                }
            }
        )
    }
}