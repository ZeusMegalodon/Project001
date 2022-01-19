package com.surelabsid.newmrjempoot.settings.akun

import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityDataPribadiBinding
import com.surelabsid.newmrjempoot.settings.akun.sheets.JenisKelaminBottomSheet
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class DataPribadiActivity : BaseActivity() {
    private lateinit var binding: ActivityDataPribadiBinding
    private lateinit var akunViewModel: AkunViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataPribadiBinding.inflate(layoutInflater)
        akunViewModel = ViewModelProvider(this).get(AkunViewModel::class.java)
        setContentView(binding.root)

        binding.namaLengkap.setText(user?.customer_fullname)
        binding.tanggalLahir.setOnClickListener {
            showTanggal(binding.tanggalLahir)
        }
        binding.finish.setOnClickListener {
            finish()
        }
        binding.jenisKelamin.setOnClickListener {
            val jkDialog = JenisKelaminBottomSheet()
            jkDialog.show(supportFragmentManager, "jkDIalog")
        }

        akunViewModel.data.observe(this){
            binding.jenisKelamin.text = it
        }
    }

    private fun showTanggal(dt: TextView) {
        val datePicker = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            dt.text = String.format(
                Locale.getDefault(),
                "%d-%02d-%02d",
                year,
                monthOfYear + 1,
                dayOfMonth
            )
        }
        datePicker.isThemeDark = false
        datePicker.accentColor = ActivityCompat.getColor(this@DataPribadiActivity, R.color.red2)
        datePicker.show(fragmentManager, "datepicker")
    }
}