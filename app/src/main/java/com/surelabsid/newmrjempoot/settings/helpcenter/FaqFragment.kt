package com.surelabsid.newmrjempoot.settings.helpcenter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.FragmentFaqBinding
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.ResponseFaq
import com.surelabsid.newmrjempoot.settings.helpcenter.adapter.AdapterFaq
import com.surelabsid.newmrjempoot.settings.helpcenter.adapter.LandingHelpCenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FaqFragment : Fragment(R.layout.fragment_faq) {

    private lateinit var binding: FragmentFaqBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFaqBinding.bind(view)

        binding.finish.setOnClickListener {
            (requireActivity() as HelpCenterActivity).supportFragmentManager.beginTransaction().replace(R.id.containerHelp, LandingHelpCenter()).commit()
        }

        getDataFaq()
    }

    private fun getDataFaq() {
        val user = (requireActivity() as BaseActivity).user
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = Network.getRetrofitSurelabs(user?.phone_number, user?.password).getFaq()
                    withContext(Dispatchers.Main) {
                        updateUI(data)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateUI(data: ResponseFaq) {
        data.dataFaq?.let {
            val adapterFaq = AdapterFaq(requireActivity(), it)
            binding.expandableListView.setAdapter(adapterFaq)

        }
    }
}