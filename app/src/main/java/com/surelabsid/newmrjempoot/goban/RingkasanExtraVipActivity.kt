package com.surelabsid.newmrjempoot.goban

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityRingkasanExtraVipBinding
import com.surelabsid.newmrjempoot.goceng.GocengActivity
import com.surelabsid.newmrjempoot.model.ExtraVIPModel
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.response.routemodel.LegsItem
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.wallet.WalletLandingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.abhinay.input.CurrencySymbols
import kotlin.math.roundToLong

class RingkasanExtraVipActivity : BaseActivity() {

    private lateinit var binding: ActivityRingkasanExtraVipBinding
    private var extraVIPModel: ExtraVIPModel? = null
    private val gobanViewMoodel: GobanViewModel by viewModels()
    private val PICKUP_REQUEST = 1
    private val DESTINATION_REQUEST = 2
    private var startLocation: LatLng? = null
    private var destinationLocation: LatLng? = null
    private var allfiturItem: AllfiturItem? = null
//    private var tripText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingkasanExtraVipBinding.inflate(layoutInflater)
        setContentView(binding.root)


        extraVIPModel = intent.getParcelableExtra(EXTRAVIP_DATA)
        allfiturItem = intent.getParcelableExtra(GocengActivity.SERVICE_DATA)

        Places.initialize(this, getString(R.string.google_maps_key))

        gobanViewMoodel.paymentMethod.observe(this) {
            extraVIPModel?.metode = it
        }
        gobanViewMoodel.textPayment.observe(this) {
            binding.metode.text = it
        }

        binding.finalCost.setDelimiter(false)
        binding.finalCost.setDecimals(false)
        binding.finalCost.setCurrency(CurrencySymbols.INDONESIA)

        binding.finish.setOnClickListener {
            finish()
        }

        binding.pesanVip.setOnClickListener {
            extraVIPModel?.pickupLatLng = startLocation
            extraVIPModel?.destinationLatLng = destinationLocation
            extraVIPModel?.distanceText = binding.distance.text.toString()
            extraVIPModel?.pickup = binding.start.text.toString()
            extraVIPModel?.destination = binding.dest.text.toString()
            extraVIPModel?.finalCost = binding.finalCost.cleanIntValue
            if (extraVIPModel?.metode == 1) {
                if (checkWallet()) {
                    Intent(this@RingkasanExtraVipActivity, GobanActivity::class.java).apply {
                        putExtra(GobanActivity.EXTRAVIP_DATA, extraVIPModel)
                        putExtra(GocengActivity.SERVICE_DATA, allfiturItem)
                        startActivity(this)
                    }
                }
            } else {
                Intent(this@RingkasanExtraVipActivity, GobanActivity::class.java).apply {
                    putExtra(GobanActivity.EXTRAVIP_DATA, extraVIPModel)
                    putExtra(GocengActivity.SERVICE_DATA, allfiturItem)
                    startActivity(this)
                }
            }

        }

        binding.metode.setOnClickListener {
            val metodeDialog = MetodeBayarSheetVip()
            metodeDialog.show(supportFragmentManager, "metodeBayar")
        }

        updateUI(extraVIPModel)
    }

    private fun updateUI(extraVIPModel: ExtraVIPModel?) {
        if (extraVIPModel?.tambahanFasilitasi?.isNotEmpty() == true) {
            binding.tambahanFasilitas.visibility = View.VISIBLE
            extraVIPModel.tambahanFasilitasi?.map {
                if (it.equals(getString(R.string.soft_drink))) {
                    binding.softDrink.visibility = View.VISIBLE
                    Constant.setDrawableColor(
                        this@RingkasanExtraVipActivity,
                        binding.softDrink,
                        R.color.red2
                    )
                }

                if (it.equals(getString(R.string.bar_table))) {
                    binding.barTable.visibility = View.VISIBLE
                    Constant.setDrawableColor(
                        this@RingkasanExtraVipActivity,
                        binding.barTable,
                        R.color.red2
                    )
                }

                if (it.equals(getString(R.string.bar_table2))) {
                    binding.barTable2.visibility = View.VISIBLE
                    Constant.setDrawableColor(
                        this@RingkasanExtraVipActivity,
                        binding.barTable2,
                        R.color.red2
                    )
                }
            }
        }

        if (extraVIPModel?.kendaraan == R.id.billis1) {
            binding.billis1.visibility = View.VISIBLE
        }
        if (extraVIPModel?.kendaraan == R.id.billis2) {
            binding.billis2.visibility = View.VISIBLE
        }



        binding.pickUpText.setOnClickListener {
            val fields =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

            val intentPickup =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intentPickup, PICKUP_REQUEST)
        }
        binding.destinationText.setOnClickListener {
            val fields =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

            val intentPickup =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intentPickup, DESTINATION_REQUEST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKUP_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                startLocation = place.latLng
                binding.start.text = place.name
                binding.start.isSelected = true

                if (destinationLocation != null) {
                    getRoute()
                }
            }
        }

        if (requestCode == DESTINATION_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)

                destinationLocation = place.latLng

                binding.dest.text = place.name
                binding.dest.isSelected = true

                if (startLocation != null) {
                    getRoute()
                }
            }
        }
    }

    private fun getRoute() {
        val origin = "${startLocation?.latitude}, ${startLocation?.longitude}"
        val destination = "${destinationLocation?.latitude}, ${destinationLocation?.longitude}"

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val routes = Network.getServiceRoute()
                    .getRoute(origin, destination, getString(R.string.google_maps_key))
                withContext(Dispatchers.Main) {
                    calculateHarga(routes.routes?.get(0)?.legs)
                }
            }
        }
    }

    private fun calculateHarga(legs: List<LegsItem?>?) {
        val jarak = legs?.get(0)?.distance!!
        binding.detailOrder.visibility = View.VISIBLE
        if (jarak.value!! > 1000) {
            binding.pickup.text = binding.start.text.toString()
            binding.tujuan.text = binding.dest.text.toString()
            val biaya = allfiturItem?.cost?.toLong()
            val finalJarak = jarak.value.div(1000.0)
            val biayaTotal = biaya?.times((finalJarak).roundToLong())
            binding.finalCost.setText(biayaTotal.toString())
            binding.distance.text = jarak.text
            binding.trip.text = "Sekali Jalan"

        } else {
            AlertDialog.Builder(this)
                .setMessage("Titik Penjemputan dan Destinasi tidak boleh sama")
                .setTitle("Kesalaham")
                .setPositiveButton("Tutup") { d, _ ->
                    d.dismiss()
                }
                .create().show()
        }
    }

    private fun checkWallet(): Boolean {
        val wallet = user?.balance!!
        if (wallet.toInt() < binding.finalCost.cleanIntValue) {
            AlertDialog.Builder(this)
                .setMessage("Saldo tidak cukup")
                .setTitle("Kesalahan")
                .setPositiveButton("Topup") { d, _ ->
                    d.dismiss()
                    Intent(
                        this@RingkasanExtraVipActivity,
                        WalletLandingActivity::class.java
                    ).apply {
                        startActivity(this)
                    }
                }
                .setNegativeButton("Tutup") { d, _ -> d.dismiss() }
                .create().show()

            return false
        } else {
            return true
        }
    }


    companion object {
        const val EXTRAVIP_DATA = "extraVipData"
    }
}