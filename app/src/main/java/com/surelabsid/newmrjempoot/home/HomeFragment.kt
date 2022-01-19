package com.surelabsid.newmrjempoot.home

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.MainActivity
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.FragmentHomeBinding
import com.surelabsid.newmrjempoot.goban.LandingGobanActivity
import com.surelabsid.newmrjempoot.goceng.GocengActivity
import com.surelabsid.newmrjempoot.goceng.LandingGocengActivity
import com.surelabsid.newmrjempoot.gopek.GopekActivity
import com.surelabsid.newmrjempoot.home.adapter.AdapterFitur
import com.surelabsid.newmrjempoot.home.adapter.AdapterMeal
import com.surelabsid.newmrjempoot.model.JsonRequest
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.onboard.OnBoardActivity
import com.surelabsid.newmrjempoot.response.*
import com.surelabsid.newmrjempoot.search.SearchResultActivity
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.GPSTracker
import kotlinx.coroutines.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var gpsTracker: GPSTracker

    private lateinit var adapterFavMealItem: AdapterMeal
    private lateinit var adapterNearbyMeal: AdapterMeal
    private lateinit var adapterFitur: AdapterFitur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        gpsTracker = GPSTracker(requireActivity())
        getLocation()
        startShimmer()
        viewModel.res.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                setHomData(it)
            }
        }

        binding.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                Intent(requireActivity(), SearchResultActivity::class.java).apply {
                    putExtra(SearchResultActivity.TERMS, binding.search.text.toString())
                    startActivity(this)
                }
            }
            return@setOnEditorActionListener true
        }

        viewModel.err.observe(viewLifecycleOwner) {
            setError(it)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it.iterator().hasNext()) {

                //set welcome data
                binding.welcomeUser.text = "Hai, ${it.iterator().next().customer_fullname}"

                val jsonRequest = JsonRequest()
                jsonRequest.id = it.iterator().next().id
                jsonRequest.latitude = gpsTracker.latitude.toString()
                jsonRequest.longitude = gpsTracker.longitude.toString()
                jsonRequest.phone_number = it.iterator().next().phone_number
                getHomeData(jsonRequest)
            } else {
                Constant.alertOkOnly(
                    "Session sudah berakhir",
                    "Info",
                    requireActivity(),
                    "OK"
                ) { logout() }
            }
        }

        binding.menu.setOnClickListener {
            (requireActivity() as MainActivity).configureDrawer()
        }

        adapterFavMealItem = AdapterMeal {

        }
        binding.favoritMeal.apply {
            adapter = adapterFavMealItem
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        }

        adapterNearbyMeal = AdapterMeal {

        }
        binding.nearbyMeal.apply {
            adapter = adapterNearbyMeal
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        }

        adapterFitur = AdapterFitur {
            when (it?.serviceId) {
                "15" -> {
                    Intent(requireActivity(), LandingGocengActivity::class.java).apply {
                        putExtra(GocengActivity.SERVICE_DATA, it)
                        startActivity(this)
                    }
                }
                "16" -> {
                    Intent(requireActivity(), LandingGobanActivity::class.java).apply {
                        putExtra(GocengActivity.SERVICE_DATA, it)
                        startActivity(this)
                    }
                }
                "17" -> {}
                "21" -> {
                    Intent(requireActivity(), GopekActivity::class.java).apply {
                        putExtra(GocengActivity.SERVICE_DATA, it)
                        startActivity(this)
                    }
                }
                "37" -> {
                    Intent(requireActivity(), LandingGocengActivity::class.java).apply {
                        putExtra(GocengActivity.SERVICE_DATA, it)
                        startActivity(this)
                    }
                }
            }
        }
        binding.layanan.apply {
            adapter = adapterFitur
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun getLocation() {
        val location = Location(getString(R.string.app_name))
        location.latitude = gpsTracker.latitude
        location.longitude = gpsTracker.longitude

        val address = gpsTracker.getCoder(location)
        if (address.isNotEmpty()) {
            var finalAddress = address.iterator().next().subLocality + ", "
            finalAddress += address.iterator().next().locality + ", "
            finalAddress += address.iterator().next().adminArea + ", "
            finalAddress += address.iterator().next().postalCode
            binding.alamat.text = finalAddress
            binding.alamat.isSelected = true
        }

        getAllGopek()
    }

    private fun getAllGopek() {
        val user = (requireActivity() as BaseActivity).user
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .getAllGopek(
                            gpsTracker.latitude.toString(),
                            gpsTracker.longitude.toString()
                        )
                    MainScope().launch {
                        updateUI(data)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateUI(data: ResponseGopek) {
        if (data.code == 200)
            setNearbyMerchant(data.itemGopek)
        else
            Toast.makeText(requireActivity(), data.message, Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        Prefs.clear()
        requireActivity().finishAffinity()
        Intent(requireActivity(), OnBoardActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun setError(throwable: Throwable) {
        Toast.makeText(requireActivity(), throwable.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private suspend fun setHomData(responseHome: ResponseHome) {
        if (responseHome.code == "200") {
            stopShimmer()
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    setLayanan(responseHome.allfitur)
                    setSlider(responseHome.slider)
                }
            }

        }
    }

    private fun setFavMeal(favMeal: List<ItemGopek?>?) {
        favMeal?.let { adapterFavMealItem.addMealData(it, true) }
        binding.favoritMeal.visibility = View.VISIBLE
    }

    private fun setNearbyMerchant(nearbyMeal: List<ItemGopek?>?) {
        nearbyMeal?.let { adapterNearbyMeal.addMealData(it, true) }
        binding.nearbyMeal.visibility = View.VISIBLE
    }

    private fun setSlider(list: List<SliderItem?>?) {
        binding.sliderPromo.setImageListener { position, imageView ->
            Glide.with(requireActivity())
                .load(Constant.IMAGESSLIDER + list?.get(position)?.photo)
                .into(imageView)
        }
        binding.sliderPromo.pageCount = list?.size ?: 0

        binding.sliderPromo.visibility = View.VISIBLE
    }

    private fun setLayanan(allfitur: List<AllfiturItem?>?) {
        allfitur?.map {
            (requireActivity() as MainActivity).insertService(it!!)
        }
        allfitur?.let { adapterFitur.addFitur(it, true) }
        binding.layanan.visibility = View.VISIBLE
    }

    private fun getHomeData(jsonRequest: JsonRequest) {
        viewModel.getHomeData(jsonRequest, user = (requireActivity() as BaseActivity).user)

    }

    private fun startShimmer() {
        binding.shimmerLayanan.startShimmer()
        binding.shimmepromo.startShimmer()
        binding.shimListFavorit.startShimmer()
        binding.shimListNearbyMeal.startShimmer()
    }

    private fun stopShimmer() {
        binding.shimmerLayanan.stopShimmer()
        binding.shimmepromo.stopShimmer()
        binding.shimListFavorit.stopShimmer()
        binding.shimListNearbyMeal.stopShimmer()
        binding.shimmerLayanan.visibility = View.GONE
        binding.shimmepromo.visibility = View.GONE
        binding.shimListFavorit.visibility = View.GONE
        binding.shimListNearbyMeal.visibility = View.GONE
    }


}