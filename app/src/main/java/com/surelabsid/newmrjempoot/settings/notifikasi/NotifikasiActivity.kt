package com.surelabsid.newmrjempoot.settings.notifikasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.databinding.ActivityNotifikasiBinding
import com.surelabsid.newmrjempoot.utils.Constant

class NotifikasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val promoChecked = Prefs.getBoolean(Constant.PROMO_NOTIFICATION)
        val allChecked = Prefs.getBoolean(Constant.ALL_NOTIFICATION)

        binding.semuaNotifikasi.isChecked = allChecked
        binding.promoNotifikasi.isChecked = promoChecked

        binding.promoNotifikasi.setOnCheckedChangeListener { _, b ->
            if (b) {
                Prefs.putBoolean(Constant.PROMO_NOTIFICATION, true)
            } else {
                Prefs.remove(Constant.PROMO_NOTIFICATION)
            }
        }
        binding.semuaNotifikasi.setOnCheckedChangeListener { _, b ->
            if (b) {
                Prefs.putBoolean(Constant.ALL_NOTIFICATION, true)
            } else {
                Prefs.remove(Constant.ALL_NOTIFICATION)
            }
        }

        binding.finish.setOnClickListener { finish() }
    }
}