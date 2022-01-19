package com.surelabsid.newmrjempoot.settings.privacy

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityPrivacyBinding
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrivacyActivity : BaseActivity() {

    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val isKebijakan = intent.getStringExtra(IS_SYARAT)
        val isPrivacy = intent.getStringExtra(IS_PRIVACY)
        val judul = intent.getStringExtra("judul")
        binding.title.text = judul


        if (NetworkUtils.isConnected(this)) {
            if (isPrivacy?.isNotEmpty() == true)
                getPrivacyPolicy()

            if(isKebijakan?.isNotEmpty() == true)
                getSyarat()
        }

        binding.finish.setOnClickListener {
            finish()
        }
    }

    private fun getSyarat() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO){
                try{
                    val data = Network.getRetrofitSurelabs(user?.phone_number, user?.password).getSyarat()
                    withContext(Dispatchers.Main) {
                        updateUi(data.data?.get(0)?.syarat)
                    }
                }catch (e: Throwable){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getPrivacyPolicy() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data =
                        Network.getRetrofitSurelabs(user?.phone_number, user?.password).privacy()
                    if (data.data != null) {
                        withContext(Dispatchers.Main) {
                            updateUi(data.data.get(0)?.privacy)
                        }
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun updateUi(data: String?) {
        val mimeType = "text/html"
        val encoding = "utf-8"
        val text = ("<html dir=" + "><head>"
                + "<style type=\"text/css\">" +
                "body{color: #000000;text-align:justify;line-height:1.2, background: #FDFFCD}"
                + "</style></head>"
                + "<body>"
                + data
                + "</body></html>")

        binding.webView.loadDataWithBaseURL(null, text, mimeType, encoding, null)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val IS_PRIVACY = "isPrivacy"
        const val IS_SYARAT = "isSyarat"
    }
}