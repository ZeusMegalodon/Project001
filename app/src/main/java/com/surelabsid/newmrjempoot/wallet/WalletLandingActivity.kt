package com.surelabsid.newmrjempoot.wallet

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityWalletLandingBinding
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.utils.HourToMillis
import com.surelabsid.newmrjempoot.wallet.adapter.AdapterHistoryWallet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.abhinay.input.CurrencySymbols

class WalletLandingActivity : BaseActivity() {

    private lateinit var binding: ActivityWalletLandingBinding
    private lateinit var adapterWallet: AdapterHistoryWallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWalletLandingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.namaLengkap.text = user?.customer_fullname
        binding.nomorTelepon.text = user?.phone_number
        binding.date.text = HourToMillis().fullDate(HourToMillis().millis())
        binding.day.text = HourToMillis().getDayOfDate(HourToMillis().millis())
        binding.jumlahSaldo.setCurrency(CurrencySymbols.INDONESIA)
        binding.jumlahSaldo.setDecimals(false)
        binding.jumlahSaldo.setDelimiter(false)



        adapterWallet = AdapterHistoryWallet()
        binding.transToday.apply {
            adapter = adapterWallet
            layoutManager = LinearLayoutManager(this@WalletLandingActivity)
        }

        binding.topup.setOnClickListener {
            Intent(this@WalletLandingActivity, TopUpActivity::class.java).apply {
                startActivity(this)
            }
        }

        fetchWallet()

    }

    private fun fetchWallet() {
        val walletRequestJson = WalletRequestJson()
        walletRequestJson.id = user?.id
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val wallet = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .wallet(walletRequestJson)
                    if (wallet.data != null) {
                        withContext(Dispatchers.Main) {
                            binding.jumlahSaldo.setText(wallet.balance)
                            wallet.data?.let {
                                adapterWallet.addItem(it)
                            }
                        }
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}

class WalletRequestJson(
    var idUser: String? = null,
    var id: String? = null,
    var walletAmount: Double? = null,
    var type: String? = null,
    var date: String? = null,
    var status: Int? = null
)