package com.surelabsid.newmrjempoot.goban

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityGobanBinding
import com.surelabsid.newmrjempoot.goceng.GocengActivity
import com.surelabsid.newmrjempoot.model.*
import com.surelabsid.newmrjempoot.model.fcm.DriverResponse
import com.surelabsid.newmrjempoot.model.fcm.FCMMessage
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.response.DriverModel
import com.surelabsid.newmrjempoot.response.RideCarResponseJson
import com.surelabsid.newmrjempoot.response.TransModel
import com.surelabsid.newmrjempoot.response.routemodel.LegsItem
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.DirectionMapsV2
import com.surelabsid.newmrjempoot.utils.FCMType
import com.surelabsid.newmrjempoot.utils.GPSTracker
import com.surelabsid.newmrjempoot.utils.api.FCMHelper
import com.surelabsid.newmrjempoot.wallet.WalletLandingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.abhinay.input.CurrencySymbols
import okhttp3.Call
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException
import java.util.*
import kotlin.math.roundToLong

class GobanActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGobanBinding
    private val PICKUP_REQUEST = 1
    private val DESTINATION_REQUEST = 2
    private var pickupMarker: Marker? = null
    private var destinationMarker: Marker? = null
    private var startLocation: LatLng? = null
    private var destinationLocation: LatLng? = null
    private var allfiturItem: AllfiturItem? = null
    private lateinit var gpsTracker: GPSTracker
    private var nearbyDriver: List<DriverModel?>? = listOf()
    private var jarak = 0.0
    private var tripText = ""
    private var transaksi: TransModel? = null
    private val driverRequest = DriverRequest()
    private var extraVIPModel : ExtraVIPModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGobanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allfiturItem = intent.getParcelableExtra(GocengActivity.SERVICE_DATA)
        extraVIPModel = intent.getParcelableExtra(EXTRAVIP_DATA)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Places.initialize(this, getString(R.string.google_maps_key))
        gpsTracker = GPSTracker(this)
        fetchNearbyDriver()

        binding.editPickup.setOnClickListener {
            val fields =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

            val intentPickup =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intentPickup, PICKUP_REQUEST)
        }

        binding.cost.setDecimals(false)
        binding.cost.setDelimiter(false)
        binding.cost.setCurrency(CurrencySymbols.INDONESIA)

        binding.finish.setOnClickListener {
            finish()
        }

        binding.editDestination.setOnClickListener {
            val fields =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

            val intentPickup =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intentPickup, DESTINATION_REQUEST)
        }

        binding.order.setOnClickListener {
            if (binding.metodeSpinner.selectedItemPosition == 0) {
                if (checkWallet()) {
                    binding.flipper.displayedChild = 1
                    broadCastOrderan()
                }
            } else {
                binding.flipper.displayedChild = 1
                broadCastOrderan()
            }
        }

        binding.searchDriver.cancelDriver.setOnClickListener {
            binding.flipper.displayedChild = 0
        }
    }

    private fun updateUI(extraVIPModel: ExtraVIPModel?) {
        startLocation = extraVIPModel?.pickupLatLng
        pickupMarker = mMap.addMarker(
            MarkerOptions().position(startLocation!!)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pickup))
        )

        binding.pickup.text = extraVIPModel?.pickup
        binding.pickup.isSelected = true

        if (destinationLocation != null) {
            val builder = LatLngBounds.Builder()
            builder.include(startLocation!!)
            builder.include(destinationLocation!!)
            val bounds = builder.build()
            val padding = 100
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
            getRoute()
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation!!, 15f))
        }

        if (destinationMarker != null)
            destinationMarker?.remove()

        destinationLocation = extraVIPModel?.destinationLatLng
        destinationMarker = mMap.addMarker(
            MarkerOptions().position(destinationLocation!!)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destinationmap))
        )


        binding.destination.text = extraVIPModel?.destination
        binding.destination.isSelected = true

        if (startLocation != null) {
            val builder = LatLngBounds.Builder()
            builder.include(startLocation!!)
            builder.include(destinationLocation!!)
            val bounds = builder.build()
            val padding = 100
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
            getRoute()
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLocation!!, 15f))
        }

    }

    private fun broadCastOrderan() {
        val request = RideCarRequestJson()
        if (nearbyDriver?.isEmpty() == true) {
            Toast.makeText(this, "Sorry, there are no drivers around you", Toast.LENGTH_SHORT)
                .show()
        } else {
            request.idPelanggan = user?.id
            request.orderFitur = allfiturItem?.serviceId
            request.startLatitude = pickupMarker?.position?.latitude
            request.startLongitude = pickupMarker?.position?.longitude
            request.endLatitude = destinationMarker?.position?.latitude
            request.endLongitude = destinationMarker?.position?.longitude
            request.distance = this.jarak
            request.price = binding.cost.cleanIntValue.toLong()
            request.estimasi = binding.waktuDropOff.text.toString()
            request.alamatAsal = binding.pickup.text.toString()
            request.alamatTujuan = binding.destination.text.toString()
            request.trip = tripText
            if (binding.metodeSpinner.selectedItemPosition == 0) {
                request.pakaiWallet = 1
            } else {
                request.pakaiWallet = 0
            }

            sendRequestTransaksi(request, nearbyDriver)
        }
    }

    private fun sendRequestTransaksi(
        request: RideCarRequestJson,
        nearbyDriver: List<DriverModel?>?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val res = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .requestTransaksi(request)
                    buildDriverRequest(res)
                    for (i in 0 until nearbyDriver?.size!!) {
                        fcmBroadcast(i, nearbyDriver)
                    }
                    val checkStatusTransRequest = CheckStatusTransRequest()
                    checkStatusTransRequest.idTransaksi = transaksi?.id

                    try {
                        val status = Network.getRetrofitSurelabs(
                            phone = user?.phone_number,
                            password = user?.password
                        ).checkStatusTransaksi(checkStatusTransRequest)

                        if (status.status) {
                            withContext(Dispatchers.Main) {
                                binding.flipper.displayedChild = 0
                                Toast.makeText(
                                    this@GobanActivity,
                                    "Driver not Found !",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            binding.flipper.displayedChild = 0
                            Toast.makeText(
                                this@GobanActivity,
                                "Driver not Found !",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        binding.flipper.displayedChild = 0
                        Toast.makeText(
                            this@GobanActivity,
                            "Your account has a problem, please contact customer service!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun buildDriverRequest(res: RideCarResponseJson) {
        if(res.data.isNotEmpty()) {
            transaksi = res.data.first()
            transaksi?.alamatAsal = binding.pickup.text.toString()
            transaksi?.alamatTujuan = binding.destination.text.toString()
            transaksi?.metodeBayar = binding.metodeSpinner.selectedItem.toString()
            transaksi?.trip = tripText

            driverRequest.idTransaksi = transaksi?.id
            driverRequest.idPelanggan = transaksi?.idPelanggan
            driverRequest.regIdPelanggan = user?.token
            driverRequest.orderFitur = allfiturItem?.home
            driverRequest.startLatitude = transaksi?.startLatitude
            driverRequest.startLongitude = transaksi?.startLongitude
            driverRequest.endLatitude = transaksi?.endLatitude
            driverRequest.endLongitude = transaksi?.endLongitude
            driverRequest.jarak = transaksi?.distance
            driverRequest.price = transaksi?.price
            driverRequest.waktuOrder = transaksi?.waktuOrder
            driverRequest.alamatAsal = transaksi?.alamatAsal
            driverRequest.alamatTujuan = transaksi?.alamatTujuan
            driverRequest.kodePromo = transaksi?.kodePromo
            driverRequest.kreditPromo = transaksi?.kreditPromo
            driverRequest.pakaiWallet = transaksi?.pakaiWallet.toString()
            driverRequest.estimasi = transaksi?.estimasi
            driverRequest.layanan = allfiturItem?.service
            driverRequest.layanandesc = allfiturItem?.description
            driverRequest.icon = allfiturItem?.icon
            driverRequest.cost = transaksi?.final_cost
            driverRequest.distance = binding.distance.toString()
            driverRequest.namaPelanggan = user?.customer_fullname
            driverRequest.telepon = user?.phone_number
            driverRequest.type = FCMType.ORDER
        }
    }

    private fun fcmBroadcast(i: Int, nearbyDriver: List<DriverModel?>) {
        val driverToSend = nearbyDriver.get(i)
        driverRequest.time_accept = Date().time.toString()
        val fcmMessage = FCMMessage()
        fcmMessage.to = driverToSend?.regId
        fcmMessage.data = driverRequest

        FCMHelper.sendMessage(Constant.FCM_KEY, fcmMessage).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("REQUEST TO DRIVER", fcmMessage.data.toString())
            }

        })

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(responses: DriverResponse) {
        Log.e(
            "DRIVER RESPONSE (W)",
            responses.response.toString() + " " + responses.id + " " + responses.idTransaksi
        )
        if (responses.response.equals(DriverResponse.ACCEPT, true) || responses.response.equals("3") || responses.response.equals(
                "4"
            )
        ) {
            runOnUiThread {
                for (cDriver in nearbyDriver!!) {
                    if (cDriver?.id.equals(responses.id)) {
//                        val intent = Intent(this@GocengActivity, ProgressActivity::class.java)
                        intent.putExtra("driver_id", cDriver?.id)
                        intent.putExtra("transaction_id", driverRequest.idTransaksi)
                        intent.putExtra("response", "2")
                        startActivity(intent)
                        val response = DriverResponse()
                        response.id = ""
                        response.idTransaksi = ""
                        response.response = ""
                        EventBus.getDefault().postSticky(response)
                        finish()
                    }
                }
            }
        }
    }

    private fun fetchNearbyDriver() {
        val nearbyDriverRequest = NearbyDriverRequest()
        nearbyDriverRequest.latitude = gpsTracker.latitude
        nearbyDriverRequest.longitude = gpsTracker.longitude
        nearbyDriverRequest.service = allfiturItem?.serviceId

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val nearby = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .getNearRide(nearbyDriverRequest)
                    if (nearby.data?.isNotEmpty() == true) {
                        nearbyDriver = nearby.data
                    }
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        if(extraVIPModel != null){
            updateUI(extraVIPModel)
        }else {
            startLocation = LatLng(gpsTracker.latitude, gpsTracker.longitude)
            pickupMarker = mMap.addMarker(
                MarkerOptions().position(startLocation!!).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_pickup)
                )
            )

            val geocoder = Geocoder(this)
            val location =
                geocoder.getFromLocation(startLocation?.latitude!!, startLocation?.longitude!!, 1)
            binding.pickup.text = location[0].getAddressLine(0)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation!!, 15f))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKUP_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                if (pickupMarker != null)
                    pickupMarker?.remove()

                startLocation = place.latLng
                pickupMarker = mMap.addMarker(
                    MarkerOptions().position(startLocation!!)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pickup))
                )

                binding.pickup.text = place.name
                binding.pickup.isSelected = true

                if (destinationLocation != null) {
                    val builder = LatLngBounds.Builder()
                    builder.include(startLocation!!)
                    builder.include(destinationLocation!!)
                    val bounds = builder.build()
                    val padding = 100
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
                    getRoute()
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation!!, 15f))
                }
            }
        }

        if (requestCode == DESTINATION_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                if (destinationMarker != null)
                    destinationMarker?.remove()

                destinationLocation = place.latLng
                destinationMarker = mMap.addMarker(
                    MarkerOptions().position(destinationLocation!!)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destinationmap))
                )


                binding.destination.text = place.name
                binding.destination.isSelected = true

                if (startLocation != null) {
                    val builder = LatLngBounds.Builder()
                    builder.include(startLocation!!)
                    builder.include(destinationLocation!!)
                    val bounds = builder.build()
                    val padding = 100
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
                    getRoute()
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLocation!!, 15f))
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
                    DirectionMapsV2.removePolyline()
                    DirectionMapsV2.gambarRoute(
                        this@GobanActivity,
                        mMap,
                        routes.routes?.get(0)?.overviewPolyline?.points!!
                    )
                    calculateHarga(routes.routes[0]?.legs)
                }
            }
        }
    }

    private fun calculateHarga(legs: List<LegsItem?>?) {
        val jarak = legs?.get(0)?.distance!!
        if (jarak.value!! > 1000) {
            binding.bottomSheet.visibility = View.VISIBLE
            binding.pickUpTextTarif.text = binding.pickup.text.toString()
            binding.destinationTextTarif.text = binding.destination.text.toString()
            binding.waktuDropOff.text = legs.get(0)?.duration?.text
            getServiceById(allfiturItem?.serviceId.toString().toInt())
            val biayaMinimum = allfiturItem?.minimumCost
            val biaya = allfiturItem?.cost?.toLong()
            this.jarak = jarak.value.div(1000.0)
            val biayaTotal = biaya?.times((this.jarak).roundToLong())
            binding.cost.setText(biayaTotal.toString())
            binding.distance.text = jarak.text

            if (biayaTotal!! > biayaMinimum!!.toLong()) {
                binding.trip.setOnCheckedChangeListener { group, checkedId ->
                    if (checkedId == R.id.pulangPergi) {
                        tripText = "Pulang Pergi"
                        binding.cost.setText(biayaTotal.times(2).toString())
                    } else {
                        tripText = "Sekali Jalan"
                        binding.cost.setText(biayaTotal.toString())
                    }
                }

            } else {
                binding.trip.setOnCheckedChangeListener { _, checkedId ->
                    if (checkedId == R.id.pulangPergi) {
                        binding.cost.setText(biayaMinimum.toLong().times(2).toString())
                        tripText = "Pulang Pergi"
                    } else {
                        tripText = "Sekali Jalan"
                        binding.cost.setText(biayaMinimum.toString())
                    }
                }
            }

            binding.bottomSheet.visibility = View.VISIBLE

        } else {
            AlertDialog.Builder(this)
                .setMessage("Titik Penjemputan dan Destinasi tidak boleh sama")
                .setTitle("Kesalaham")
                .setPositiveButton("Tutup") { d, _ ->
                    d.dismiss()
                    binding.bottomSheet.visibility = View.GONE
                }
                .create().show()
        }
    }

    private fun checkWallet(): Boolean {
        val wallet = user?.balance!!
        if (wallet.toInt() < binding.cost.cleanIntValue) {
            AlertDialog.Builder(this)
                .setMessage("Saldo tidak cukup")
                .setTitle("Kesalahan")
                .setPositiveButton("Topup") { d, _ ->
                    d.dismiss()
                    Intent(this@GobanActivity, WalletLandingActivity::class.java).apply {
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
    companion object{
        const val EXTRAVIP_DATA = "extraVipData"
    }
}