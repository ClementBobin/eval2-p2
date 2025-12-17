package com.diiage.template.domain.media

import com.diiage.template.domain.model.SoundType

/**
 * Sound manager interface for handling sound effects in the application.
 *
 * This interface provides methods to play, control, and manage sound effects
 * with proper resource management.
 */
interface SoundManagerRepository {

    /**
     * Plays a sound effect.
     *
     * @param soundType The type of sound to play
     * @param volume Optional volume level (0.0 to 1.0)
     */
    suspend fun playSound(soundType: SoundType, volume: Float = 1.0f)

    /**
     * Stops currently playing sounds.
     */
    fun stopSounds()
}