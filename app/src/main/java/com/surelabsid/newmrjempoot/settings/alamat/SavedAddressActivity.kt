package com.surelabsid.newmrjempoot.settings.alamat

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivitySavedAddressBinding
import com.surelabsid.newmrjempoot.settings.alamat.adapter.AdapterSavedAddress
import com.surelabsid.newmrjempoot.utils.db
import kotlinx.coroutines.*

class SavedAddressActivity : BaseActivity() {
    private lateinit var binding: ActivitySavedAddressBinding
    private lateinit var adapterSavedAddress: AdapterSavedAddress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedAddressBinding.inflate(layoutInflater)

        setContentView(binding.root)


        adapterSavedAddress = AdapterSavedAddress {
            Intent(this@SavedAddressActivity, AddressActivity::class.java).apply {
                putExtra(AddressActivity.EDIT, it)
                startActivity(this)
            }
        }

        binding.savedAddress.apply {
            adapter = adapterSavedAddress
            layoutManager = LinearLayoutManager(this@SavedAddressActivity)
        }
        binding.tambahAlamat.setOnClickListener {
            startActivity(Intent(this@SavedAddressActivity, AddressActivity::class.java))
        }

        binding.finish.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        getSavedAddress()
    }

    private fun getSavedAddress() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val savedAddress = db?.addressDao()?.getAllAddress()
                    MainScope().launch {
                        adapterSavedAddress.addItem(savedAddress, true)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}