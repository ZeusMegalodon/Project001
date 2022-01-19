package com.surelabsid.newmrjempoot.landing

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.newmrjempoot.MainActivity
import com.surelabsid.newmrjempoot.R

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        Handler(Looper.getMainLooper()).postDelayed({
            finishAffinity()
            Intent(this@SuccessActivity, MainActivity::class.java).apply {
                startActivity(this)
            }
        }, 2000)
    }
}