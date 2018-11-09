package com.example.toof.customview

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAudioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        volumn_bar.calibrateVolumeLevels(
            mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
            mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)
        )

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        super.dispatchKeyEvent(event)
        if (event?.keyCode == KeyEvent.KEYCODE_VOLUME_UP || event?.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            volumn_bar.setVolumeLevel(mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM))
        }
        return false
    }

}
