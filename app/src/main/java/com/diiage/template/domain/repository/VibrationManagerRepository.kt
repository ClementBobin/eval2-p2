package com.diiage.template.domain.repository

import com.diiage.template.domain.model.VibrationType

/**
 * Vibration manager interface for handling haptic feedback in the application.
 *
 * This interface provides methods to control device vibrations with different
 * patterns and intensities, with proper resource management and API compatibility.
 */
interface VibrationManagerRepository {

    /**
     * Checks if the device supports vibration.
     *
     * @return true if vibration is available and supported, false otherwise
     */
    fun hasVibrator(): Boolean

    /**
     * Vibrates the device with a specific pattern.
     *
     * @param vibrationType The type of vibration to play
     * @param repeat Whether to repeat the pattern (-1 for no repeat)
     */
    fun vibrate(vibrationType: VibrationType, repeat: Int = -1)

    /**
     * Vibrates the device with a simple duration.
     *
     * @param durationMillis Duration in milliseconds
     */
    fun vibrate(durationMillis: Long)

    /**
     * Cancels any ongoing vibration.
     */
    fun cancel()

    /**
     * Checks if the device is currently vibrating.
     *
     * @return true if currently vibrating, false otherwise
     */
    fun isVibrating(): Boolean
}