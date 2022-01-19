package com.surelabsid.newmrjempoot.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.databinding.ItemAdapterWishListBinding
import com.surelabsid.newmrjempoot.response.ItemGopek
import com.surelabsid.newmrjempoot.utils.Constant
import me.abhinay.input.CurrencySymbols

class AdapterWishList(private val removeFromFav: (ItemGopek?) -> Unit) :
    RecyclerView.Adapter<AdapterWishList.ViewHolder>() {

    private val listFav = mutableListOf<ItemGopek?>()

    inner class ViewHolder(private val itemAdapterWishListBinding: ItemAdapterWishListBinding) :
        RecyclerView.ViewHolder(itemAdapterWishListBinding.root) {

        fun onBindItem(favMealItem: ItemGopek?) {
            itemAdapterWishListBinding.namaMenu.text = favMealItem?.itemName
            itemAdapterWishListBinding.description.text = favMealItem?.itemDesc
            Glide.with(itemView.context)
                .load(Constant.IMAGESITEM + favMealItem?.itemImage)
                .into(itemAdapterWishListBinding.fotoMenu)
            itemAdapterWishListBinding.rating.text =
                String.format("%.1f", favMealItem?.rating?.toDouble())

            itemAdapterWishListBinding.harga.setDelimiter(false)
            itemAdapterWishListBinding.harga.setDecimals(false)
            itemAdapterWishListBinding.harga.setCurrency(CurrencySymbols.INDONESIA)
            itemAdapterWishListBinding.harga.setText(favMealItem?.itemPrice)

            itemAdapterWishListBinding.contain.visibility = View.VISIBLE
            itemAdapterWishListBinding.shimmerFrameLayout.visibility = View.VISIBLE
            itemAdapterWishListBinding.restoName.text = favMealItem?.merchantName
            itemAdapterWishListBinding.jamOperasi.text = favMealItem?.openHour + "-" + favMealItem?.closeHour

            itemAdapterWishListBinding.fav.setOnClickListener {
                removeFromFav(favMealItem)
            }
        }
    }

    fun addItem(list: List<ItemGopek?>, clearAll: Boolean = false) {
        if (clearAll)
            listFav.removeAll(listFav)

        listFav.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterWishListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listFav[position])
    }

    override fun getItemCount(): Int {
        return listFav.size
    }

}