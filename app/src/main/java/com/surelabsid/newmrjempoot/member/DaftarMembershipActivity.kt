package com.surelabsid.newmrjempoot.member

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityDaftarMembershipBinding
import com.surelabsid.newmrjempoot.model.KtpReq
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.GeneralResponse
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.util.*

class DaftarMembershipActivity : BaseActivity() {
    private lateinit var binding: ActivityDaftarMembershipBinding
    private var jk = ""
    private var imgBase64 = ""
    private val ktpReq = KtpReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finish.setOnClickListener {
            finish()
        }

        binding.startCamera.setOnClickListener {
            Intent(this@DaftarMembershipActivity, AmbilKtpActivity::class.java).apply {
                startActivityForResult(this, 1040)
            }
        }

        binding.pickTanggal.setOnClickListener {
            showTanggal()
        }

        binding.rgJk.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.pria) {
                jk = "Pria"
                binding.wanita.setTextColor(
                    ActivityCompat.getColor(
                        this@DaftarMembershipActivity,
                        R.color.red2
                    )
                )
                binding.pria.setTextColor(
                    ActivityCompat.getColor(
                        this@DaftarMembershipActivity,
                        R.color.kuning
                    )
                )
                binding.pria.background = ActivityCompat.getDrawable(
                    this@DaftarMembershipActivity,
                    R.drawable.button_round_1
                )
                binding.wanita.background = ActivityCompat.getDrawable(
                    this@DaftarMembershipActivity,
                    R.drawable.bg_white_border_red
                )
            } else if (checkedId == R.id.wanita) {
                binding.wanita.background = ActivityCompat.getDrawable(
                    this@DaftarMembershipActivity,
                    R.drawable.button_round_1
                )
                binding.wanita.setTextColor(
                    ActivityCompat.getColor(
                        this@DaftarMembershipActivity,
                        R.color.kuning
                    )
                )
                binding.pria.setTextColor(
                    ActivityCompat.getColor(
                        this@DaftarMembershipActivity,
                        R.color.red2
                    )
                )
                binding.pria.background = ActivityCompat.getDrawable(
                    this@DaftarMembershipActivity,
                    R.drawable.bg_white_border_red
                )
                jk = "Wanita"
            }
        }

        binding.daftar.setOnClickListener {
            AlertDialog.Builder(this@DaftarMembershipActivity)
                .setTitle("Konfirmasi")
                .setMessage("Apakah data yang anda masukkan sudah sesuai?")
                .setPositiveButton("Ya, lanjutkan") { d, i ->
                    sendData()
                }
                .setNegativeButton("Batal") { d, _ -> d.dismiss() }
                .create().show()
        }
    }

    private fun sendData() {
        binding.daftar.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val bmp = BitmapFactory.decodeFile(imgBase64)
                    val outputStream = ByteArrayOutputStream()
                    bmp.compress(Bitmap.CompressFormat.WEBP, 70, outputStream)
                    val finBase64 =
                        Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                    ktpReq.jenis_kelamin = jk
                    ktpReq.tanggal_lahir = binding.tanggalLahir.text.toString()
                    ktpReq.no_ktp = binding.noKtp.text.toString()
                    ktpReq.id_user = user?.id
                    ktpReq.ktp = finBase64


                    val res = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .sendDataKtp(ktpReq)
                    MainScope()
                        .launch {
                            updateUI(res)
                        }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    MainScope().launch {
                        Toast.makeText(
                            this@DaftarMembershipActivity,
                            e.message.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

        }
    }

    private fun updateUI(generalResponse: GeneralResponse) {
        AlertDialog.Builder(this@DaftarMembershipActivity)
            .setTitle("Informasi")
            .setMessage(generalResponse.message)
            .setPositiveButton("Ya, paham") { _, _ ->
                finish()
            }.create().show()
    }

    private fun showTanggal() {
        val datepicker = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
            binding.tanggalLahir.text = String.format(
                Locale.getDefault(),
                "%d-%02d-%02d",
                year,
                monthOfYear.plus(1),
                dayOfMonth
            )
        }
        datepicker.isThemeDark = false
        datepicker.accentColor =
            ActivityCompat.getColor(this@DaftarMembershipActivity, R.color.red2)
        datepicker.show(fragmentManager, "datePickerDialog")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1040 && resultCode == Activity.RESULT_OK) {
            data?.let {
                imgBase64 = it.getStringExtra("path").toString()
                val filename = it.getStringExtra("filename")
                binding.fileName.text = filename
            }
        }
    }
}