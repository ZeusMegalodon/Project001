package com.surelabsid.newmrjempoot.gopek.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.databinding.ItemAdapterGopekExtraBinding
import com.surelabsid.newmrjempoot.databinding.ItemAdapterGopekNormalBinding
import com.surelabsid.newmrjempoot.response.ItemGopek
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.CurrencyHelper
import java.util.*


class AdapterGopek(
    private val itemClick: (ItemGopek?) -> Unit,
    private val favClick: (ItemGopek?, ImageView) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listItemGopek = mutableListOf<ItemGopek?>()


    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return 1
        return 0
    }

    fun addItem(list: List<ItemGopek?>, clearAll: Boolean = false) {
        if (clearAll)
            listItemGopek.removeAll(listItemGopek)

        listItemGopek.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return ViewHolderNormal(
                ItemAdapterGopekNormalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ViewHolderExtra(
                ItemAdapterGopekExtraBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0->{
                val holderNormal = holder as ViewHolderNormal
                holderNormal.onBindItem(listItemGopek[position])
            }
            1->{
                val holderExtra = holder as ViewHolderExtra
                holderExtra.onBindItem(listItemGopek[position], listItemGopek.size)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemGopek.size
    }

    inner class ViewHolderNormal(private val mItemAdapterGopekNormalBinding: ItemAdapterGopekNormalBinding) :
        RecyclerView.ViewHolder(mItemAdapterGopekNormalBinding.root) {
        fun onBindItem(itemGopek: ItemGopek?) {
            mItemAdapterGopekNormalBinding.rating.text =
                String.format(Locale.getDefault(), "%.1f", itemGopek?.rating.toString().toDouble())
            if(itemGopek?.promoStatus?.equals("0", true) == true){
                mItemAdapterGopekNormalBinding.harga.text =
                    CurrencyHelper.coolNumberFormat(itemGopek.itemPrice.toString().toLong())
            }else{
                mItemAdapterGopekNormalBinding.harga.text =
                    CurrencyHelper.coolNumberFormat(itemGopek?.promoPrice.toString().toLong())
            }
            mItemAdapterGopekNormalBinding.namaMenu.text = itemGopek?.itemName
            mItemAdapterGopekNormalBinding.extra.text = itemGopek?.itemDesc
            Glide.with(itemView.context)
                .load(Constant.IMAGESITEM + itemGopek?.itemImage)
                .into(mItemAdapterGopekNormalBinding.imageMenu)

            mItemAdapterGopekNormalBinding.root.setOnClickListener {
                itemClick(itemGopek)
            }

            mItemAdapterGopekNormalBinding.favButton.setOnClickListener {
                favClick(itemGopek, mItemAdapterGopekNormalBinding.favButton)
            }

        }
    }

    inner class ViewHolderExtra(private val mItemAdapterGopekExtraBinding: ItemAdapterGopekExtraBinding) :
        RecyclerView.ViewHolder(mItemAdapterGopekExtraBinding.root) {

        fun onBindItem(itemGopek: ItemGopek?, size: Int) {
            mItemAdapterGopekExtraBinding.foundedItem.text =
                String.format(Locale.getDefault(), "%d item", size)
            mItemAdapterGopekExtraBinding.rating.text =
                String.format(Locale.getDefault(), "%.1f", itemGopek?.rating.toString().toDouble())
            if(itemGopek?.promoStatus?.equals("0", true) == true){
                mItemAdapterGopekExtraBinding.harga.text =
                    CurrencyHelper.coolNumberFormat(itemGopek.itemPrice.toString().toLong())
            }else{
                mItemAdapterGopekExtraBinding.harga.text =
                    CurrencyHelper.coolNumberFormat(itemGopek?.promoPrice.toString().toLong())
            }

            mItemAdapterGopekExtraBinding.namaMenu.text = itemGopek?.itemName
            mItemAdapterGopekExtraBinding.extra.text = itemGopek?.itemDesc
            Glide.with(itemView.context)
                .load(Constant.IMAGESITEM + itemGopek?.itemImage)
                .into(mItemAdapterGopekExtraBinding.imageMenu)

            mItemAdapterGopekExtraBinding.root.setOnClickListener {
                itemClick(itemGopek)
            }

            mItemAdapterGopekExtraBinding.favIcon.setOnClickListener {
                favClick(itemGopek, mItemAdapterGopekExtraBinding.favIcon)
            }
        }
    }

}