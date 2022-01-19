package com.surelabsid.newmrjempoot.settings.alamat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ItemAdapterSavedAddressBinding
import com.surelabsid.newmrjempoot.model.SavedAddress

class AdapterSavedAddress(private val onclickHapusEdit: (SavedAddress) -> Unit) :
    RecyclerView.Adapter<AdapterSavedAddress.ViewHolder>() {

    private val data = mutableListOf<SavedAddress>()

    inner class ViewHolder(private val mItemAdapterSavedAddressBinding: ItemAdapterSavedAddressBinding) :
        RecyclerView.ViewHolder(mItemAdapterSavedAddressBinding.root) {
        private var isClosed = true

        fun onBindItem(savedAddress: SavedAddress) {
            mItemAdapterSavedAddressBinding.label.text = savedAddress.label
            mItemAdapterSavedAddressBinding.alamat.text = savedAddress.address
            mItemAdapterSavedAddressBinding.edit.setOnClickListener {
                onclickHapusEdit(savedAddress)
            }

            mItemAdapterSavedAddressBinding.arrow.setOnClickListener {
                if (isClosed) {
                    Glide.with(itemView.context)
                        .load(R.drawable.ic_arrow_up)
                        .into(mItemAdapterSavedAddressBinding.arrow)

                    mItemAdapterSavedAddressBinding.containerAction.visibility = View.VISIBLE
                    isClosed = false
                } else {
                    Glide.with(itemView.context)
                        .load(R.drawable.ic_down_)
                        .into(mItemAdapterSavedAddressBinding.arrow)
                    mItemAdapterSavedAddressBinding.containerAction.visibility = View.GONE
                    isClosed = true
                }
            }

            mItemAdapterSavedAddressBinding.hapus.setOnClickListener {
                onclickHapusEdit(savedAddress)
            }
        }
    }

    fun addItem(list: List<SavedAddress>?, clearAll: Boolean = false) {
        if (clearAll)
            data.removeAll(data)

        list?.let { data.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterSavedAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}