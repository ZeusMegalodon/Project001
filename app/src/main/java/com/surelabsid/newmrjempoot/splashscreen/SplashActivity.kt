package com.surelabsid.newmrjempoot.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.splashscreen.ui.SplashFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportFragmentManager.beginTransaction()
            .replace(R.id.containerSplash, SplashFragment())
            .commit()
    }
}