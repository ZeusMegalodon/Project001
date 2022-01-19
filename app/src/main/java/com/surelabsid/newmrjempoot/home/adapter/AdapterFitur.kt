package com.surelabsid.newmrjempoot.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.databinding.ItemAdapterFiturBinding
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.utils.Constant

class AdapterFitur(private val onClick: (AllfiturItem?) -> Unit) :
    RecyclerView.Adapter<AdapterFitur.ViewHolder>() {
    private val mListFitur = mutableListOf<AllfiturItem?>()

    inner class ViewHolder(val itemAdapterFiturBinding: ItemAdapterFiturBinding) :
        RecyclerView.ViewHolder(itemAdapterFiturBinding.root) {

        fun onBindItem(allfiturItem: AllfiturItem?) {
            Glide.with(itemView.context)
                .load(Constant.IMAGESFITUR + allfiturItem?.icon)
                .into(itemAdapterFiturBinding.image)

            itemAdapterFiturBinding.text.text = allfiturItem?.service

            itemAdapterFiturBinding.root.setOnClickListener {
                onClick(allfiturItem)
            }
        }
    }


    fun addFitur(listItem: List<AllfiturItem?>, clearAll: Boolean = false) {
        if (clearAll)
            mListFitur.removeAll(mListFitur)

        mListFitur.addAll(listItem)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterFiturBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListFitur[position])
    }

    override fun getItemCount(): Int {
        return mListFitur.size
    }

}