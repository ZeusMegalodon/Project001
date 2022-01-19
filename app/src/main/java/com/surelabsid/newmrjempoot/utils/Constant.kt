package com.surelabsid.newmrjempoot.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.surelabsid.newmrjempoot.network.Network

object Constant {
    const val FCM_KEY =
        "AAAA-MEPdi4:APA91bG8UxvjpVe-ph1hCXm5_kFmXDAA3Nx1zzEhD-nmm2zt3YnJCgmBKRByHeNNOQr6YGGU7kgWb919ECxyOHZ_ez-16TAVsXRxbNWwaiN_qkUpyd6dcVIwlnwq56LPMfD6SL47u76j"

    const val PHONE = "phone"
    const val NAME = "name"
    const val PASSWORD = "password"
    const val EMAIL = "email"
    const val TOKEN = "token"
    const val IS_LOGIN = "islogin"
    const val FIRSTRUN = "firstRun"

    const val DBNAME = "db_mrjempoot"

    const val ALL_NOTIFICATION = "allNotif"
    const val PROMO_NOTIFICATION = "promoNotification"


    val IMAGESFITUR = Network.BASE_URL_SURELABS + "images/service/"
    val IMAGESMERCHANT = Network.BASE_URL_SURELABS + "images/merchant/"
    val IMAGESBANK = Network.BASE_URL_SURELABS + "images/bank/"
    val IMAGESITEM = Network.BASE_URL_SURELABS + "images/itemphoto/"
    val IMAGESBERITA = Network.BASE_URL_SURELABS + "images/news/"
    val IMAGESSLIDER = Network.BASE_URL_SURELABS + "images/promo/"
    val IMAGESDRIVER = Network.BASE_URL_SURELABS + "images/driverphoto/"
    val IMAGESUSER = Network.BASE_URL_SURELABS + "images/customer/"


    const val PENDING = 1
    const val SUCCESS = 2
    const val FAILURE = 3
    const val CHALLENGE = 4


    fun alertOkOnly(
        message: String,
        title: String,
        context: Activity,
        positiveButtonText: String = "OK",
        action: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { _, _ ->
                action.invoke()
            }
            .setCancelable(false)
            .create().show()
    }


    fun setDrawableColor(context: Context, checkBox: CheckBox, color: Int){
        for(drawable in checkBox.compoundDrawables){
            if(drawable!= null){
                drawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

}