package com.surelabsid.newmrjempoot.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.newmrjempoot.databinding.ItemAdapterHistoryWalletBinding
import com.surelabsid.newmrjempoot.response.WalletDataItem
import me.abhinay.input.CurrencySymbols


class AdapterHistoryWallet : RecyclerView.Adapter<AdapterHistoryWallet.ViewHolder>() {

    private val listItemWallet = mutableListOf<WalletDataItem?>()

    class ViewHolder(val mItemAdapterHistoryWalletBinding: ItemAdapterHistoryWalletBinding): RecyclerView.ViewHolder(mItemAdapterHistoryWalletBinding.root){
        fun onBindItem(walletDataItem: WalletDataItem?){
            mItemAdapterHistoryWalletBinding.nominal.setCurrency(CurrencySymbols.INDONESIA)
            mItemAdapterHistoryWalletBinding.nominal.setDelimiter(false)
            mItemAdapterHistoryWalletBinding.nominal.setDecimals(false)
            mItemAdapterHistoryWalletBinding.nominal.setText(walletDataItem?.walletAmount)
            mItemAdapterHistoryWalletBinding.keterangan.text = walletDataItem?.type
            mItemAdapterHistoryWalletBinding.jenis.text = walletDataItem?.type
            mItemAdapterHistoryWalletBinding.jenis.visibility  = View.GONE
            mItemAdapterHistoryWalletBinding.jam.text = walletDataItem?.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterHistoryWalletBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun addItem(list: List<WalletDataItem?>, clearAll: Boolean = false){
        if(clearAll)
            listItemWallet.removeAll(listItemWallet)

        listItemWallet.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listItemWallet[position])
    }

    override fun getItemCount(): Int {
        return listItemWallet.size
    }

}