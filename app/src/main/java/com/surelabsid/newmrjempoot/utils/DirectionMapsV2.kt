package com.surelabsid.newmrjempoot.utils


import android.content.Context
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.surelabsid.newmrjempoot.R
import java.util.*

/*
* ----------------------------------------------------------------------
* Silahkan untuk mengcopy, mendistribusikan dan menggunakan
* class ini sebebas-bebasnya, Tapi jangan menghapus bagian header ini.
*
* created by xrb21 riyadi.rb@gmail.com http://ercode.xyz
* ----------------------------------------------------------------------
*/

class DirectionMapsV2() {


    companion object {
        private var polyz: List<LatLng>? = null
        private var line: Polyline? = null

        // menggambar polyline
        fun gambarRoute(context: Context, map: GoogleMap?, dataPoly: String) {

            polyz = decodePoly(dataPoly)
            val options =
                PolylineOptions().width(20f).color(ActivityCompat.getColor(context, R.color.red2))
                    .geodesic(true)
            for (i in 0 until polyz?.size!! - 1) {
                val src = polyz!![i]
                val dest = polyz!![i + 1]
//                val line = map.addPolyline(
//                    PolylineOptions()
//                        .add(
//                            LatLng(src.latitude, src.longitude),
//                            LatLng(dest.latitude, dest.longitude)
//                        ).width(10f)
//                        .color(ActivityCompat.getColor(context, R.color.red2)).geodesic(true)
//                )
                options.add(src, dest)
            }
            if (map != null)
                line = map.addPolyline(options)
        }


        fun removePolyline() {
            if (line != null)
                line?.remove()
        }

        /* Method to decode polyline points */
        internal fun decodePoly(encoded: String): List<LatLng> {

            val poly = ArrayList<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat

                shift = 0
                result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng

                val p = LatLng(
                    lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5
                )
                poly.add(p)
            }

            return poly
        }
    }


}