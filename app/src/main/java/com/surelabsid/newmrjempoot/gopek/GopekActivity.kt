package com.surelabsid.newmrjempoot.gopek

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityGopekBinding
import com.surelabsid.newmrjempoot.favorite.WishListActivity
import com.surelabsid.newmrjempoot.goceng.GocengActivity
import com.surelabsid.newmrjempoot.gopek.adapter.AdapterGopek
import com.surelabsid.newmrjempoot.model.FavModel
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.response.GeneralResponse
import com.surelabsid.newmrjempoot.response.ItemGopek
import com.surelabsid.newmrjempoot.response.ResponseGopek
import com.surelabsid.newmrjempoot.search.SearchResultActivity
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.GPSTracker
import kotlinx.coroutines.*

class GopekActivity : BaseActivity() {

    private lateinit var binding: ActivityGopekBinding
    private lateinit var gopekAdapter: AdapterGopek
    private var allfiturItem: AllfiturItem? = null
    private lateinit var gpsTracker: GPSTracker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGopekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gpsTracker = GPSTracker(this@GopekActivity)

        allfiturItem = intent.getParcelableExtra(GocengActivity.SERVICE_DATA)

        gopekAdapter = AdapterGopek(itemClick = {
            Intent(this@GopekActivity, DetailGopekActivity::class.java).apply {
                putExtra(DetailGopekActivity.ITEMGOPEK, it)
                startActivity(this)
            }
        }, favClick = { data, img ->
            addToFavorite(data, img)
        })
        binding.rvTest.apply {
            adapter = gopekAdapter
            layoutManager = GridLayoutManager(this@GopekActivity, 2)
        }

        Glide.with(this)
            .load(Constant.IMAGESFITUR + allfiturItem?.icon)
            .into(binding.menu)

        binding.label.text = allfiturItem?.service
        binding.bottomAction.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.order -> {

                    return@setOnItemSelectedListener true
                }
                R.id.wishlist -> {
                    Intent(this@GopekActivity, WishListActivity::class.java).apply {
                        putExtra(WishListActivity.CALLFROM_ACTIVITY, true)
                        startActivity(this)
                    }

                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }

        fetchData("makanan")

        binding.groupOne.setOnSegmentedGroupListener { tab, checkedId ->
            if (checkedId == R.id.makanan) {
                fetchData("makanan")
            } else if (checkedId == R.id.minuman) {
                fetchData("minuman")
            }
        }

        binding.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                Intent(this@GopekActivity, SearchResultActivity::class.java).apply {
                    putExtra(SearchResultActivity.TERMS, binding.search.text.toString())
                    startActivity(this)
                }
            }
            return@setOnEditorActionListener true
        }
    }

    private fun addToFavorite(data: ItemGopek?, img: ImageView) {
        val favModel = FavModel()
        favModel.userid = user?.id
        favModel.item_id = data?.itemId
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val res = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .addFavorite(favModel)
                    MainScope().launch {
                        updateUIFav(res, img)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateUIFav(res: GeneralResponse, img: ImageView) {
        if (res.code == 200) {
            Glide.with(this)
                .load(R.drawable.heart)
                .into(img)
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
        } else if (res.code == 300) {
            Glide.with(this)
                .load(R.drawable.ic_love_outline)
                .into(img)
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchData(jenis: String) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val res = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .getGopekItem(
                            jenis = jenis,
                            lon = gpsTracker.longitude.toString(),
                            lat = gpsTracker.latitude.toString()
                        )
                    MainScope().launch {
                        updateUI(res)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateUI(res: ResponseGopek) {
        if (res.code == 200) {
            res.itemGopek?.let { gopekAdapter.addItem(it, true) }
        } else {
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
        }
    }
}