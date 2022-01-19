package com.surelabsid.newmrjempoot.onboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.MainActivity
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ActivityOnBoardBinding
import com.surelabsid.newmrjempoot.landing.LandingActivity
import com.surelabsid.newmrjempoot.utils.Constant

class OnBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (!Prefs.getBoolean(Constant.FIRSTRUN)) {
            finishAffinity()
            if (Prefs.contains(Constant.PHONE)) {
                Intent(this@OnBoardActivity, MainActivity::class.java).apply {
                    startActivity(this)
                }
            } else {
                Intent(this@OnBoardActivity, LandingActivity::class.java).apply {
                    startActivity(this)
                }
            }
            return
        }

        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.boarding1.board1Lanjut.setOnClickListener {
            binding.flipper.setInAnimation(this@OnBoardActivity, R.anim.from_right)
            binding.flipper.setOutAnimation(this, R.anim.to_left)
            binding.flipper.displayedChild = 1
        }

        binding.boarding2.board2Lanjut.setOnClickListener {
            binding.flipper.setInAnimation(this@OnBoardActivity, R.anim.from_right)
            binding.flipper.setOutAnimation(this, R.anim.to_left)
            binding.flipper.displayedChild = 2
        }

        binding.boarding3.board3Lanjut.setOnClickListener {
            finishAffinity()
            Prefs.putBoolean(Constant.FIRSTRUN, false)
            if (Prefs.contains(Constant.PHONE)) {
                Intent(this@OnBoardActivity, MainActivity::class.java).apply {
                    startActivity(this)
                }
            } else {
                Intent(this@OnBoardActivity, LandingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }
}