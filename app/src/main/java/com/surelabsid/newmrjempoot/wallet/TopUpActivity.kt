package com.surelabsid.newmrjempoot.wallet

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityTopUpBinding
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.HourToMillis
import kotlinx.coroutines.*
import me.abhinay.input.CurrencySymbols
import java.util.*

class TopUpActivity : BaseActivity() {

    private lateinit var binding: ActivityTopUpBinding
    private val walletRequestJson = WalletRequestJson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.nominal.setCurrency(CurrencySymbols.INDONESIA)
        binding.nominal.setDecimals(false)
        binding.nominal.setDelimiter(false)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                101
            )
        } else {
            initializeSDK()
        }

        binding.next.setOnClickListener {
            sendRequestToSever()
        }

        initClick()
    }

    @SuppressLint("SetTextI18n")
    private fun initClick() {
        binding.c100k.setOnClickListener {
            binding.next.isEnabled = true
            binding.next.alpha = 1f
            binding.nominal.setText("10000")
        }
        binding.c50k.setOnClickListener {
            binding.next.isEnabled = true
            binding.next.alpha = 1f
            binding.nominal.setText("50000")
        }
        binding.c100k.setOnClickListener {
            binding.next.isEnabled = true
            binding.next.alpha = 1f
            binding.nominal.setText("100000")
        }
        binding.c200k.setOnClickListener {
            binding.next.isEnabled = true
            binding.next.alpha = 1f
            binding.nominal.setText(
                "200000"
            )
        }
        binding.c300k.setOnClickListener {
            binding.next.isEnabled = true
            binding.next.alpha = 1f
            binding.nominal.setText("300000")
        }
        binding.c500k.setOnClickListener {
            binding.next.isEnabled = true
            binding.next.alpha = 1f
            binding.nominal.setText("500000")
        }
        binding.hapus.setOnClickListener {
            binding.next.isEnabled = false
            binding.next.alpha = 0.5f
            binding.nominal.setText("0")
        }

        binding.satu.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("1")
            } else {
                binding.nominal.setText(temp1.toString() + "1")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.dua.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("2")
            } else {
                binding.nominal.setText(temp1.toString() + "2")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.tiga.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("3")
            } else {
                binding.nominal.setText(temp1.toString() + "3")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.empat.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("4")
            } else {
                binding.nominal.setText(temp1.toString() + "4")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.lima.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("5")
            } else {
                binding.nominal.setText(temp1.toString() + "5")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.enam.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("6")
            } else {
                binding.nominal.setText(temp1.toString() + "6")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.tujuh.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("7")
            } else {
                binding.nominal.setText(temp1.toString() + "7")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.delapan.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("8")
            } else {
                binding.nominal.setText(temp1.toString() + "8")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.sembilan.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("9")
            } else {
                binding.nominal.setText(temp1.toString() + "9")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.nol.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("0")
            } else {
                binding.nominal.setText(temp1.toString() + "0")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
        binding.nolnol.setOnClickListener {
            val temp1: Int = binding.nominal.cleanIntValue
            if (temp1 == 0) {
                binding.nominal.setText("00")
            } else {
                binding.nominal.setText(temp1.toString() + "00")
            }
            binding.next.isEnabled = true
            binding.next.alpha = 1f
        }
    }

    private fun initializeSDK() {
        SdkUIFlowBuilder.init()
            .setClientKey(getString(R.string.client_key_midtrans))
            .setContext(this)
            .setTransactionFinishedCallback {

            }
            .setMerchantBaseUrl(Network.BASE_URL_SURELABS)
            .enableLog(true)
            .setLanguage("id")
            .buildSDK()
    }

    private fun sendRequestToSever() {
        val h = HourToMillis()

        walletRequestJson.id = UUID.randomUUID().toString()
        walletRequestJson.date = h.millisToDateHour(h.millis(), null)
        walletRequestJson.idUser = user?.id
        walletRequestJson.type = "Topup"
        walletRequestJson.walletAmount = binding.nominal.cleanDoubleValue
        walletRequestJson.status = Constant.PENDING

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val req = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .requestSaldo(walletRequestJson)
                    if (req.code == 200) {
                        MainScope().launch {
                            generateMidTrans(walletRequestJson)
                        }
                    } else {
                        MainScope().launch {
                            Toast.makeText(
                                this@TopUpActivity,
                                req.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun generateMidTrans(walletrequest: WalletRequestJson) {
        val transactionRequest =
            TransactionRequest(
                walletrequest.id.toString(),
                walletrequest.walletAmount.toString().toDouble()
            )
        val customeDetails = CustomerDetails()
        val name = user?.customer_fullname?.split(" ")
        val lastName: String
        var fistName: String
        if (name!!.size > 1) {
            lastName = name[name.size.minus(1)]
            fistName = ""
            for (i in 0 until name.size.minus(1)) {
                fistName += name[i] + " "
            }
        } else {
            lastName = name[name.size.minus(1)]
            fistName = name[name.size.minus(1)]
        }

        customeDetails.email = user?.email
        customeDetails.firstName = fistName
        customeDetails.lastName = lastName
        customeDetails.phone = user?.phone_number
        customeDetails.customerIdentifier = user?.id

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Yogyakarta"
        shippingAddress.city = "Yogyakarta"
        shippingAddress.postalCode = "55572"

        customeDetails.shippingAddress = shippingAddress

        val itemDetails = ItemDetails(
            walletrequest.id,
            walletrequest.walletAmount.toString().toDouble(),
            1,
            walletrequest.type
        )
        val itemDetailList = arrayListOf<ItemDetails>()
        itemDetailList.add(itemDetails)

        transactionRequest.customerDetails = customeDetails
        transactionRequest.itemDetails = itemDetailList

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)
        finish()
    }
}