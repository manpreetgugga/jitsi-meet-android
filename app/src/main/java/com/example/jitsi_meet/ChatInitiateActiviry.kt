package com.example.jitsi_meet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_chat_initiate_activiry.*

class ChatInitiateActiviry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_initiate_activiry)
        startCall.setOnClickListener {
            launchActivity(this, MainActivity::class.java)
            finish()
        }
    }

    fun launchActivity(activity: AppCompatActivity, aClass: Class<*>) {
        ActivityManager.startFreshActivityClearStack(activity, aClass)
        AppAndroidUtils.startFwdAnimation(activity)
    }

}