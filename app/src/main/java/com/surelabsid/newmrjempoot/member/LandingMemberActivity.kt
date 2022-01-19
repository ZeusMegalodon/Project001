package com.surelabsid.newmrjempoot.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityLandingMemberBinding
import com.surelabsid.newmrjempoot.model.KtpReq
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.ResponseCheckStatus
import kotlinx.coroutines.*

class LandingMemberActivity : BaseActivity() {
    private lateinit var binding: ActivityLandingMemberBinding
    private var isMember = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.finish.setOnClickListener {
            finish()
        }

        binding.nama.text = user?.customer_fullname

        binding.daftar.setOnClickListener {
            Intent(this@LandingMemberActivity, DaftarMembershipActivity::class.java).apply {
                startActivity(this)
            }
        }

        checkStatus()

    }

    private fun checkStatus() {
        val ktpReq = KtpReq()
        ktpReq.id_user = user?.id
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO){
                try{
                    val data = Network.getRetrofitSurelabs(user?.phone_number, user?.password).checkMember(ktpReq)
                    MainScope().launch {
                        updateUI(data)
                    }
                }catch(e: Throwable){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateUI(data: ResponseCheckStatus) {
        if(data.code == 400){
            binding.daftar.visibility = View.GONE

            binding.dataMemberCard.setOnClickListener {
//                Intent(this@LandingMemberActivity, Datamem)
            }
            Toast.makeText(this@LandingMemberActivity, data.message, Toast.LENGTH_SHORT).show()
        }else if(data.code == 200){
            isMember = true

            binding.daftar.visibility = View.GONE
            binding.statusContainer.visibility = View.GONE

            binding.dataMemberCard.setOnClickListener {

            }

            binding.referal.setOnClickListener {

            }
        }else if(data.code == 404){
            isMember = false
            binding.daftar.visibility = View.VISIBLE
            binding.referal.setOnClickListener {
                Toast.makeText(this@LandingMemberActivity, "Daftar member terlebih dahulu untuk menggunakan program Referal", Toast.LENGTH_SHORT).show()
            }
        }

        binding.status.text = data.message
    }


}