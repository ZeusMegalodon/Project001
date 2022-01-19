package com.surelabsid.newmrjempoot.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize


@Parcelize
data class ExtraVIPModel (
    var kendaraan: Int? = null,
    var tambahanFasilitasi: List<String?>? = null,
    var pickup: String? = null,
    var destination: String? = null,
    var pickupLatLng: LatLng? = null,
    var destinationLatLng: LatLng? = null,
    var distanceText: String? = null,
    var distance: Int? = null,
    var finalCost: Int? = null,
    var metode: Int? = null,
): Parcelable