package com.surelabsid.newmrjempoot.gopek

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityDetailGopekBinding
import com.surelabsid.newmrjempoot.model.Cart
import com.surelabsid.newmrjempoot.response.ItemGopek
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.CurrencyHelper
import kotlinx.coroutines.*
import me.abhinay.input.CurrencySymbols
import java.util.*

class DetailGopekActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailGopekBinding
    private var initialCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGopekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemGopek = intent.getParcelableExtra<ItemGopek>(ITEMGOPEK)

        binding.back.setOnClickListener { finish() }

        binding.promo.setCurrency(CurrencySymbols.INDONESIA)
        binding.promo.setDelimiter(false)
        binding.promo.setDecimals(false)

        binding.harga.setCurrency(CurrencySymbols.INDONESIA)
        binding.harga.setDelimiter(false)
        binding.harga.setDecimals(false)

        binding.namaMenu.text = itemGopek?.itemName
        binding.rating.text =
            String.format(Locale.getDefault(), "%.1f", itemGopek?.rating.toString().toDouble())
        if (itemGopek?.promoStatus?.equals("1", true) == true) {
            val potonganPersen = CurrencyHelper.potongan(
                itemGopek.itemPrice.toString().toDouble(),
                itemGopek.promoPrice.toString().toDouble()
            )

            binding.potonganDiskonPersen.text = "${potonganPersen.toInt()} %"
            binding.hargaCoret.visibility = View.VISIBLE
            binding.promo.setText(itemGopek.itemPrice)
            binding.harga.setText(itemGopek.promoPrice)
        } else {
            binding.harga.setText(itemGopek?.promoPrice)
            binding.potonganDiskonPersen.visibility = View.GONE
        }

        Glide.with(this@DetailGopekActivity)
            .load(Constant.IMAGESITEM + itemGopek?.itemImage)
            .into(binding.mealImage)

        binding.plus.setOnClickListener {
            if (initialCount >= 0) {
                ++initialCount
                binding.countText.text = initialCount.toString()
            }
        }

        binding.minus.setOnClickListener {
            if (initialCount > 0) {
                --initialCount
                binding.countText.text = initialCount.toString()
            }
        }

        binding.addToCart.setOnClickListener {
            if (binding.countText.text.toString().equals("0", true)) {
                Toast.makeText(
                    this@DetailGopekActivity,
                    "Jumlah Barang Minimal 1",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                addToCart(itemGopek)
            }
        }
    }

    private fun addToCart(itemGopek: ItemGopek?) {
        val gson = Gson()
        val json = gson.toJson(itemGopek)
        print(json)
        val cartItem = gson.fromJson(json, Cart::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    db.cartDao().insertCart(cartItem)
                    MainScope().launch {
                        Toast.makeText(
                            this@DetailGopekActivity,
                            "Item berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }


    companion object {
        const val ITEMGOPEK = "itemGopek"
    }
}