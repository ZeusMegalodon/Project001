package com.surelabsid.newmrjempoot.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.newmrjempoot.databinding.ActivityLandingBinding
import com.surelabsid.newmrjempoot.login.LoginActivity
import com.surelabsid.newmrjempoot.register.RegisterActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            Intent(this@LandingActivity, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.buatAkun.setOnClickListener {
            Intent(this@LandingActivity, RegisterActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.changeLang.setOnClickListener {
            val changeLang = ChangeLanguageFragment()
            changeLang.show(supportFragmentManager, "change_lang")
        }
    }
}