package com.surelabsid.newmrjempoot.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.database.AppDatabase
import com.surelabsid.newmrjempoot.databinding.FragmentCartBinding
import com.surelabsid.newmrjempoot.model.Cart
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.routemodel.LegsItem
import com.surelabsid.newmrjempoot.utils.DirectionMapsV2
import kotlinx.coroutines.*

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var binding: FragmentCartBinding
    private var cartItem : Cart? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        val db = (requireActivity() as BaseActivity).db

        getCart(db)
        Places.initialize(requireActivity(), getString(R.string.google_maps_key))

    }

    private fun getCart(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val cart = db.cartDao().getAllCart()
                MainScope().launch {
                    if (cart.isEmpty()) {
                        binding.flipper.displayedChild = 1
                    } else {
                        initializeUi(cart)
                    }
                }
            }
        }
    }

    private fun initializeUi(cart: List<Cart>) {
        cartItem = cart.first()
        binding.alamatResto.text = cartItem?.merchantAddress
        binding.namaResto.text = cartItem?.merchantName
        binding.pickPeta.setOnClickListener {
            val fields =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

            val intentPickup =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(requireActivity())
            startActivityForResult(intentPickup, 1022)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1022 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                getRoute(place)
            }
        }
    }

    private fun getRoute(place: Place) {
        val origin = "${cartItem?.merchantLatitude}, ${cartItem?.merchantLatitude}"
        val destination = "${place.latLng?.latitude}, ${place.latLng?.longitude}"

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val routes = Network.getServiceRoute()
                    .getRoute(origin, destination, getString(R.string.google_maps_key))
                withContext(Dispatchers.Main) {
                    DirectionMapsV2.removePolyline()
                    DirectionMapsV2.gambarRoute(
                        requireActivity(),
                        null,
                        routes.routes?.get(0)?.overviewPolyline?.points!!
                    )
                    calculateHarga(routes.routes.get(0)?.legs)
                }
            }
        }
    }

    private fun calculateHarga(legs: List<LegsItem?>?) {
        val distance = legs?.get(0)?.distance?.value?.div(1000)

    }

}