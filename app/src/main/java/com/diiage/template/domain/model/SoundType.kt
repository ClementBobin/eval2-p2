package com.diiage.template.domain.model

/**
 * Represents different types of sounds used in the application.
 *
 * @property resourceId The resource ID of the sound file, or null if not applicable.
 */
sealed class SoundType(val resourceId: Int?) {
    object Click : SoundType(null)
    object Success : SoundType(null)
    object Error : SoundType(null)
}