package com.surelabsid.newmrjempoot.settings.akun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finish.setOnClickListener {
            finish()
        }

        binding.ubahDataPribadi.setOnClickListener {
            Intent(this@ProfileActivity, DataPribadiActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.namaLengkap.text = user?.customer_fullname
        binding.email.text = user?.email
        binding.status.text = "Belum terverifikasi"
        binding.telepon.text = user?.phone_number
        binding.statusVerifikasi.text = "Sudah terverifikasi"

        if(user?.google_token.toString().isNotEmpty()){
            binding.sambungkanGoogle.text = "Terhubung"
        }

    }
}