package com.surelabsid.newmrjempoot.settings.alamat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.newmrjempoot.addresspick.MapsPickerActivity
import com.surelabsid.newmrjempoot.databinding.ActivityAddressBinding
import com.surelabsid.newmrjempoot.model.SavedAddress
import com.surelabsid.newmrjempoot.utils.db
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedAddress = intent.getParcelableExtra<SavedAddress>(EDIT)
        if(savedAddress != null){
            updateUi(savedAddress)
        }

        binding.openMapPicker.setOnClickListener {
            if (binding.label.text.toString().isNotEmpty())
                Intent(this@AddressActivity, MapsPickerActivity::class.java).apply {
                    startActivityForResult(this, 102)
                } else {
                Toast.makeText(
                    this@AddressActivity,
                    "Tulis Label Alamat terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUi(savedAddress: SavedAddress) {
        binding.containerAlamat.visibility = View.VISIBLE
        binding.containerExtra.visibility = View.VISIBLE
        binding.alamat.setText(savedAddress.address)
        binding.extra.setText(savedAddress.extra)
        binding.label.setText(savedAddress.label)
        binding.simpanAlamat.setOnClickListener {
            savedToDatabase(savedAddress)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val savedAddress =
                    it.getParcelableExtra<SavedAddress>(MapsPickerActivity.ADDRESSMODEL)
                binding.containerAlamat.visibility = View.VISIBLE
                binding.containerExtra.visibility = View.VISIBLE
                binding.alamat.setText(savedAddress?.address)
                binding.extra.setText(savedAddress?.extra)
                binding.simpanAlamat.setOnClickListener {
                    savedToDatabase(savedAddress)
                }
            }
        }
    }

    private fun savedToDatabase(savedAddress: SavedAddress?) {
        savedAddress?.label = binding.label.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    db?.addressDao()?.insert(savedAddress)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddressActivity,
                            "Alamat berhasil disimpan",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddressActivity,
                            "Terjadi kesalahan saat menyimpan alamat",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    companion object{
        const val EDIT = "edit"
    }
}