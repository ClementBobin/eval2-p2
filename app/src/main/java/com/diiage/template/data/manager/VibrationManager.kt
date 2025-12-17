package com.diiage.template.data.manager

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.diiage.template.domain.model.VibrationType
import com.diiage.template.domain.repository.VibrationManagerRepository

/**
 * Implementation of VibrationManagerRepository for Android.
 *
 * Handles device vibration with support for different API levels:
 * - API 31+: Uses VibratorManager
 * - API 26+: Uses VibrationEffect
 * - Lower APIs: Uses legacy vibration methods
 *
 * @param context The application context.
 */
class VibrationManager(private val context: Context) : VibrationManagerRepository {

    private var vibrator: Vibrator? = null
    private var isVibrating = false

    init {
        initializeVibrator()
    }

    /**
     * Initializes the vibrator based on Android API level.
     */
    private fun initializeVibrator() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+ (API 31) uses VibratorManager
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            // Legacy API (deprecated in API 31)
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    /**
     * Checks if the device supports vibration.
     *
     * @return true if vibration is available and supported, false otherwise
     */
    override fun hasVibrator(): Boolean {
        return vibrator?.hasVibrator() ?: false
    }

    /**
     * Vibrates the device with a specific pattern.
     *
     * @param vibrationType The type of vibration to play
     * @param repeat Whether to repeat the pattern (-1 for no repeat)
     */
    override fun vibrate(vibrationType: VibrationType, repeat: Int) {
        if (!hasVibrator()) return

        try {
            isVibrating = true

            when {
                true -> {
                    // API 26+ with VibrationEffect support
                    vibrateWithEffect(vibrationType, repeat)
                }
                else -> {
                    // Legacy vibration (API < 26)
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(vibrationType.pattern, repeat)
                }
            }
        } catch (e: Exception) {
            println("Error vibrating device: ${e.message}")
            isVibrating = false
        }
    }

    /**
     * Vibrates the device with a simple duration (modern API).
     *
     * @param durationMillis Duration in milliseconds
     */
    override fun vibrate(durationMillis: Long) {
        if (!hasVibrator()) return

        try {
            isVibrating = true

            when {
                true -> {
                    // API 26+ with VibrationEffect support
                    val effect = VibrationEffect.createOneShot(
                        durationMillis,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                    vibrator?.vibrate(effect)
                }
                else -> {
                    // Legacy vibration (API < 26)
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(durationMillis)
                }
            }
        } catch (e: Exception) {
            println("Error vibrating device: ${e.message}")
            isVibrating = false
        }
    }

    /**
     * Cancels any ongoing vibration.
     */
    override fun cancel() {
        try {
            vibrator?.cancel()
            isVibrating = false
        } catch (e: Exception) {
            println("Error cancelling vibration: ${e.message}")
        }
    }

    /**
     * Checks if the device is currently vibrating.
     *
     * @return true if currently vibrating, false otherwise
     */
    override fun isVibrating(): Boolean = isVibrating

    /**
     * Vibrates using VibrationEffect (API 26+).
     *
     * @param vibrationType The vibration pattern
     * @param repeat Pattern repeat index
     */
    private fun vibrateWithEffect(vibrationType: VibrationType, repeat: Int) {
        val effect = when {
            // Use amplitudes if available (API 26+)
            vibrationType.amplitudes != null -> {
                VibrationEffect.createWaveform(
                    vibrationType.pattern,
                    repeat
                )
            }
            // Simple waveform without amplitudes
            else -> {
                VibrationEffect.createWaveform(
                    vibrationType.pattern,
                    repeat
                )
            }
        }

        vibrator?.vibrate(effect)
    }

    /**
     * Safely vibrates with compatibility wrapper.
     * Automatically handles API level differences.
     *
     * @param vibrationType Type of vibration
     * @param volume Optional volume modifier (0.0 to 1.0) for haptic feedback intensity
     */
    fun vibrateWithCompat(vibrationType: VibrationType, volume: Float = 1.0f) {
        if (!hasVibrator() || volume <= 0.01f) return

        try {
            // Adjust pattern based on volume (simple scaling)
            val adjustedPattern = if (volume < 1.0f) {
                vibrationType.pattern.map { (it * volume).toLong() }.toLongArray()
            } else {
                vibrationType.pattern
            }

            val adjustedType = when (vibrationType) {
                is VibrationType.Custom -> VibrationType.Custom(
                    adjustedPattern,
                    vibrationType.amplitudes,
                    vibrationType.repeat
                )
                else -> VibrationType.Custom(
                    adjustedPattern,
                    null,
                    vibrationType.repeat
                )
            }

            vibrate(adjustedType, adjustedType.repeat)
        } catch (e: Exception) {
            println("Error in compatible vibration: ${e.message}")
        }
    }

    /**
     * Cleans up resources when the manager is no longer needed.
     */
    fun cleanup() {
        cancel()
        vibrator = null
    }
}