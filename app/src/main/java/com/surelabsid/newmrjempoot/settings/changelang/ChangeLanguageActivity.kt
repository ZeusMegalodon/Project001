package com.surelabsid.newmrjempoot.settings.changelang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ActivityChangeLanguageBinding

class ChangeLanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.finish.setOnClickListener {
            finish()
        }

        binding.rdGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.indonesia -> Toast.makeText(
                    this@ChangeLanguageActivity,
                    "Indonesia",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.english -> Toast.makeText(
                    this@ChangeLanguageActivity,
                    "English",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.china -> Toast.makeText(
                    this@ChangeLanguageActivity,
                    "China",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.arabic -> Toast.makeText(
                    this@ChangeLanguageActivity,
                    "Arabic",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}