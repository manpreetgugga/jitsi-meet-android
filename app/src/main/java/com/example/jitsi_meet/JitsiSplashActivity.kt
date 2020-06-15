package com.example.jitsi_meet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class JitsiSplashActivity : AppCompatActivity() {

    val SPLASH_SCREEN_DELAY = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jitsi_splash)
        Handler().postDelayed({
            launchActivity(this, ChatInitiateActiviry::class.java)
            finish()
        }, SPLASH_SCREEN_DELAY)
    }

    fun launchActivity(activity: AppCompatActivity, aClass: Class<*>) {
        ActivityManager.startFreshActivityClearStack(activity, aClass)
        AppAndroidUtils.startFwdAnimation(activity)
    }
}
