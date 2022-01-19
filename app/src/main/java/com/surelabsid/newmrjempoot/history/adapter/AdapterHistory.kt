package com.surelabsid.newmrjempoot.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.databinding.ItemAdapterHistoryBinding
import com.surelabsid.newmrjempoot.response.HistoryItem
import com.surelabsid.newmrjempoot.utils.Constant
import me.abhinay.input.CurrencySymbols
import java.util.*
import kotlin.math.roundToLong

class AdapterHistory(private val titleTarif : String?) : RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

    private var mListItemHistory = mutableListOf<HistoryItem?>()

    inner class ViewHolder(private val itemAdapterHistoryBinding: ItemAdapterHistoryBinding) :
        RecyclerView.ViewHolder(itemAdapterHistoryBinding.root){

            fun onBindItem(historyItem: HistoryItem?){
                itemAdapterHistoryBinding.driverName.text = historyItem?.driver?.driverName
//                Glide.with(itemView.context)
//                    .load(Constant.IMAGESDRIVER + historyItem?.driver?.photo)
//                    .into(itemAdapterHistoryBinding.driverImg)

                itemAdapterHistoryBinding.pickup.text = historyItem?.pickupAddress
                itemAdapterHistoryBinding.dest.text = historyItem?.destinationAddress
                itemAdapterHistoryBinding.trip.text = historyItem?.trip

                val finalDistance = historyItem?.distance?.div(1000)?.toDouble()
                itemAdapterHistoryBinding.distance.text = String.format(Locale.getDefault(), "%d KM", finalDistance?.roundToLong())

                itemAdapterHistoryBinding.tarifText.text = titleTarif
                itemAdapterHistoryBinding.tarif.setCurrency(CurrencySymbols.INDONESIA)
                itemAdapterHistoryBinding.tarif.setDecimals(false)
                itemAdapterHistoryBinding.tarif.setDelimiter(false)
                itemAdapterHistoryBinding.tarif.setText(historyItem?.finalCost)

                itemAdapterHistoryBinding.nominal.setCurrency(CurrencySymbols.INDONESIA)
                itemAdapterHistoryBinding.nominal.setDecimals(false)
                itemAdapterHistoryBinding.nominal.setDelimiter(false)
                itemAdapterHistoryBinding.nominal.setText(historyItem?.finalCost)
                itemAdapterHistoryBinding.kodePesanan.text = "Kode Pesanan: BIKE-${historyItem?.id}"
                if(historyItem?.walletPayment?.equals("0") == true){
                    itemAdapterHistoryBinding.metode.text = "Cash"
                }else{
                    itemAdapterHistoryBinding.metode.text = "Jempoot Pay"
                }
                itemAdapterHistoryBinding.waktuDropOff.text = historyItem?.estimateTime

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun addItemHistory(list: List<HistoryItem?>, clearAll: Boolean = false){
        if(clearAll)
            mListItemHistory.removeAll(mListItemHistory)

        mListItemHistory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListItemHistory[position])
    }

    override fun getItemCount(): Int {
        return mListItemHistory.size
    }
}