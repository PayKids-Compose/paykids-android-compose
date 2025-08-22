package com.paykidscompose.presentation.audio

import android.content.Context
import android.media.SoundPool
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.paykidscompose.presentation.R
import kotlinx.coroutines.suspendCancellableCoroutine

class AudioManager(private val context: Context) {
    private val soundPool = SoundPool.Builder().setMaxStreams(5).build()
    private val sounds = mutableMapOf<SoundEffect, Int>()
    private val exoPlayer = ExoPlayer.Builder(context).build()
    private val loadedSounds = mutableSetOf<SoundEffect>()

    /** 효과음 로드 */
    fun loadQuizSounds() {
        sounds[SoundEffect.CORRECT] = soundPool.load(context, R.raw.quiz_correct_effect, 1)
        sounds[SoundEffect.WRONG] = soundPool.load(context, R.raw.quiz_wrong_effect, 1)
    }

    fun loadQuizClearSounds() {
        sounds[SoundEffect.CLEAR] = soundPool.load(context, R.raw.quiz_clear_effect, 1)
        sounds[SoundEffect.FAIL] = soundPool.load(context, R.raw.quiz_fail_effect, 1)
    }

    /** 배경 음악 재생 */
    fun playQuizBgm() {
        val mediaItem =
            MediaItem.fromUri(context.rawUri(R.raw.quiz_bgm))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        exoPlayer.prepare()
        exoPlayer.play()
    }

    /** 배경 음악 정지 */
    fun stopBgm() = exoPlayer.stop()

    /** 리소스 해제 */
    fun release() {
        exoPlayer.release()
        soundPool.release()
    }

    /** 효과음 재생 */
    fun play(effect: SoundEffect) {
        val soundId = sounds[effect] ?: return
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    // 퀴즈 클리어 화면에서 효과음이 로드된 후에 재생이 보장되어야 하기 때문에 메서드 분리
    suspend fun awaitLoadAndPlay(effect: SoundEffect) {
        val soundId = sounds[effect] ?: return

        // 로드 완료될 때까지 대기
        if (!loadedSounds.contains(effect)) {
            suspendCancellableCoroutine { cont ->
                val listener = SoundPool.OnLoadCompleteListener { _, sampleId, status ->
                    if (status == 0 && sampleId == soundId) {
                        loadedSounds.add(effect)
                        soundPool.setOnLoadCompleteListener(null) // 해제
                        cont.resume(Unit) { cause, _, _ -> }
                    }
                }
                soundPool.setOnLoadCompleteListener(listener)
            }
        }

        // 재생
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

}