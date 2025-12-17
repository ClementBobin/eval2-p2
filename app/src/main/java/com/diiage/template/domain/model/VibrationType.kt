package com.diiage.template.domain.model

/**
 * Represents different types of vibrations used in the application.
 *
 * @property pattern The vibration pattern array (timings in milliseconds)
 * @property amplitudes Optional amplitude array (0.0 to 1.0) - API level 26+
 * @property repeat The index at which to repeat the pattern (-1 for no repeat)
 */
sealed class VibrationType(
    val pattern: LongArray,
    val amplitudes: FloatArray? = null,
    val repeat: Int = -1
) {
    /**
     * Short feedback vibration (50ms)
     * Use for button clicks, minor actions
     */
    object Click : VibrationType(
        pattern = longArrayOf(0, 50)
    )

    /**
     * Success feedback vibration pattern (short-long-short)
     * Use for successful operations
     */
    object Success : VibrationType(
        pattern = longArrayOf(0, 30, 100, 100)
    )

    /**
     * Error feedback vibration pattern (3 short bursts)
     * Use for errors, warnings, failures
     */
    object Error : VibrationType(
        pattern = longArrayOf(0, 100, 100, 100, 100, 100)
    )

    /**
     * Notification vibration pattern (standard pattern)
     * Use for notifications, alerts
     */
    object Notification : VibrationType(
        pattern = longArrayOf(0, 400, 200, 400)
    )

    /**
     * Custom vibration with full control
     *
     * @param pattern Vibration pattern (alternating wait/vibrate durations)
     * @param amplitudes Optional vibration intensities (API 26+)
     * @param repeat Pattern repeat index
     */
    class Custom(
        pattern: LongArray,
        amplitudes: FloatArray? = null,
        repeat: Int = -1
    ) : VibrationType(pattern, amplitudes, repeat)
}