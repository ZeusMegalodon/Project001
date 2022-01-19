package com.surelabsid.newmrjempoot.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivitySearchResultBinding
import com.surelabsid.newmrjempoot.favorite.adapter.AdapterWishList
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.ResponseGopek
import kotlinx.coroutines.*

class SearchResultActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var adapterSearch: AdapterWishList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchTerms = intent.getStringExtra(TERMS)
        goSearch(searchTerms)

        binding.finish.setOnClickListener {
            finish()
        }


        adapterSearch = AdapterWishList {

        }
        binding.rvSeach.apply {
            adapter = adapterSearch
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
        }
    }

    private fun goSearch(q: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .searchGopek(q)
                    MainScope().launch {
                        updateUI(data)
                    }
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }

    private fun updateUI(data: ResponseGopek) {
        if (data.code == 200) {
            data.itemGopek?.let { adapterSearch.addItem(it) }
        }
    }

    companion object {
        const val TERMS = "term"
    }
}