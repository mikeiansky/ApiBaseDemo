package com.winson.apibasedemo.media

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.VideoView
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 * @date 2020/2/22
 * @author Winson
 */
class MediaVoiceActivity : BaseActivity() {

    //    val testUrl = "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4"
//    val testUrl = "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4"
//    val testUrl = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4"
    val testUrl = "http://wma.9ku.com/file2/78/77707.mp3"

    override fun onPause() {
        super.onPause()
        Log.d("TAG","onPause ----> ")
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(
            AudioManager.STREAM_VOICE_CALL,
            1,
            AudioManager.FLAG_PLAY_SOUND
        )
    }

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_media_voice)

        val videoView = findViewById<VideoView>(R.id.video_view)
//        videoView.setVideoURI(Uri.parse(testUrl))
        videoView.setVideoPath(testUrl)
        videoView.start()

        findViewById<View>(R.id.play).setOnClickListener {
            videoView.start()
        }
        findViewById<View>(R.id.pause).setOnClickListener {
            videoView.pause()
        }

        findViewById<View>(R.id.plus).setOnClickListener {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val volume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) + 1
            Log.d("TAG", "plus volume is : $volume")
            audioManager.setStreamVolume(
                AudioManager.STREAM_VOICE_CALL,
                volume,
                AudioManager.FLAG_PLAY_SOUND
            )
        }
        findViewById<View>(R.id.minus).setOnClickListener {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val volume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) - 1
            Log.d("TAG", "minus volume is : $volume")
            audioManager.setStreamVolume(
                AudioManager.STREAM_VOICE_CALL,
                volume,
                AudioManager.FLAG_PLAY_SOUND
            )
        }

    }

}