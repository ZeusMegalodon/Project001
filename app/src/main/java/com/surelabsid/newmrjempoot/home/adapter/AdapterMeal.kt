package com.surelabsid.newmrjempoot.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.databinding.ItemAdapterMealBinding
import com.surelabsid.newmrjempoot.response.FavMealItem
import com.surelabsid.newmrjempoot.response.ItemGopek
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.CurrencyHelper

class AdapterMeal(private val onClick: (ItemGopek?) -> Unit) : RecyclerView.Adapter<AdapterMeal.ViewHolder>() {

    private val mListData = mutableListOf<ItemGopek?>()

    inner class ViewHolder(private val itemAdapterMealBinding: ItemAdapterMealBinding) :
        RecyclerView.ViewHolder(itemAdapterMealBinding.root) {
        fun onBindItem(favMealItem: ItemGopek?) {
            Glide.with(itemView.context)
                .load(Constant.IMAGESITEM + favMealItem?.itemImage)
                .into(itemAdapterMealBinding.imageMenu)

            itemAdapterMealBinding.harga.text = CurrencyHelper.coolNumberFormat(favMealItem?.itemPrice.toString().toLong())
            itemAdapterMealBinding.namaMenu.text = favMealItem?.itemName
            itemAdapterMealBinding.rating.text = String.format("%.1f", favMealItem?.rating?.toDouble())
            itemAdapterMealBinding.extra.text = favMealItem?.itemDesc

            itemAdapterMealBinding.root.setOnClickListener {
                onClick(favMealItem)
            }
        }
    }

    fun addMealData(list: List<ItemGopek?>, clearAll: Boolean = false) {
        if (clearAll)
            mListData.removeAll(mListData)

        mListData.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterMealBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListData[position])
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

}