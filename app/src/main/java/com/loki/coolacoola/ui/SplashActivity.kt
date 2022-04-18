package com.loki.coolacoola.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.os.postDelayed
import com.loki.coolacoola.R

class SplashActivity : AppCompatActivity() {

    private lateinit var mName: TextView
    private lateinit var mAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mName = findViewById(R.id.main_name)

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.intro_anim)

        mName.startAnimation(mAnimation)


        val handler: Handler = Handler()
        handler.postDelayed( Runnable {

            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }, 5000
        )

    }
}