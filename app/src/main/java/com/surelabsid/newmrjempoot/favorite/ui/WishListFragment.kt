package com.surelabsid.newmrjempoot.favorite.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.FragmentWishListBinding
import com.surelabsid.newmrjempoot.favorite.adapter.AdapterWishList
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.ResponseGopek
import kotlinx.coroutines.*

class WishListFragment : Fragment(R.layout.fragment_wish_list) {

    companion object {
        fun newInstance(isCalledFromActivity: Boolean = false): WishListFragment {
            val wishListFragment = WishListFragment()
            val args = Bundle()
            args.putBoolean("isCalledFromActivity", isCalledFromActivity)
            wishListFragment.arguments = args
            return wishListFragment
        }
    }

    private var isCallFromActivity: Boolean? = false
    private lateinit var binding: FragmentWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var adapterWishList: AdapterWishList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            isCallFromActivity = arguments?.getBoolean("isCalledFromActivity", false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WishListViewModel::class.java)
        binding = FragmentWishListBinding.bind(view)

        adapterWishList = AdapterWishList {

        }

        val user = (requireActivity() as BaseActivity).user
        fetchFavorite(user)

        binding.rvWishList.apply {
            adapter = adapterWishList
            layoutManager = LinearLayoutManager(requireActivity())
        }

        if (isCallFromActivity == false) {
            binding.finish.visibility = View.GONE
        }

        binding.finish.setOnClickListener {
            requireActivity().finish()
        }

    }

    private fun fetchFavorite(user: UserModel?) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = Network.getRetrofitSurelabs(user?.phone_number, user?.password)
                        .getFavoriteList(user?.id)
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
        if(data.code == 200){
            data.itemGopek?.let { adapterWishList.addItem(it, clearAll = true) }
        }else{
            Toast.makeText(requireActivity(), data.message, Toast.LENGTH_SHORT).show()
        }
    }

}