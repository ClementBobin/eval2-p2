package com.diiage.template.data.manager

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import com.diiage.template.domain.media.SoundManagerRepository
import com.diiage.template.domain.model.SoundType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SoundManager(private val context: Context) : SoundManagerRepository {

    private var mediaPlayer: MediaPlayer? = null

    override suspend fun playSound(soundType: SoundType, volume: Float) {
        val soundUri = getSystemSoundUri(soundType) ?: run {
            println("No system sound URI found for: $soundType")
            return
        }

        withContext(Dispatchers.IO) {
            try {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(context, soundUri)
                    setVolume(volume, volume)

                    setOnPreparedListener { mp ->
                        try {
                            if (!mp.isPlaying) {
                                mp.start()
                            }
                        } catch (e: Exception) {
                            println("Error starting sound: ${e.message}")
                        }
                    }

                    setOnCompletionListener { mp ->
                        try {
                            mp.release()
                        } catch (e: Exception) {
                            // Ignore errors during cleanup
                        }
                        mediaPlayer = null
                    }

                    setOnErrorListener { mp, what, extra ->
                        println("MediaPlayer error: what=$what, extra=$extra")
                        try {
                            mp.release()
                        } catch (e: Exception) {
                            // Ignore errors during cleanup
                        }
                        mediaPlayer = null
                        true
                    }

                    prepareAsync()
                }
            } catch (e: Exception) {
                println("Error playing system sound: ${e.message}")
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
    }

    override fun stopSounds() {
        mediaPlayer?.let {
            try {
                if (it.isPlaying) {
                    it.stop()
                }
            } catch (e: Exception) {
                println("Error stopping sound: ${e.message}")
            }
            try {
                it.release()
            } catch (e: Exception) {
                // Ignore cleanup errors
            }
            mediaPlayer = null
        }
    }

    /**
     * Gets the Android system URI for a given sound type.
     * Uses RingtoneManager for reliable URI retrieval.
     */
    private fun getSystemSoundUri(soundType: SoundType): Uri? {
        return try {
            when (soundType) {
                SoundType.Click -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                SoundType.Success -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                SoundType.Error -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                else -> null
            }
        } catch (e: Exception) {
            println("Error getting system sound URI: ${e.message}")
            null
        }
    }
}