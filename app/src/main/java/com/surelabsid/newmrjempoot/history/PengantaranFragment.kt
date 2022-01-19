package com.surelabsid.newmrjempoot.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.FragmentFinishedBinding
import com.surelabsid.newmrjempoot.history.adapter.AdapterHistory
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.ResponseHistory
import kotlinx.coroutines.*

private const val TITLE_TARIF = "titleTarif"
private const val STATUS = "status"
private const val SERVICE = "service"

class PengantaranFragment : Fragment(R.layout.fragment_finished) {

    private lateinit var binding: FragmentFinishedBinding
    private lateinit var adapterHistory: AdapterHistory
    private var titleTarif: String? = null
    private var status: Int? = null
    private var service: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleTarif = it.getString(TITLE_TARIF)
            status = it.getInt(STATUS)
            service = it.getInt(SERVICE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFinishedBinding.bind(view)


        adapterHistory = AdapterHistory(titleTarif)
        binding.rvHistory.apply {
            adapter = adapterHistory
            layoutManager = LinearLayoutManager(requireActivity())
        }

        getHistory()
    }

    private fun getHistory() {
        val user = (requireActivity() as BaseActivity).user
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data =
                        Network.getRetrofitSurelabs(user?.phone_number, user?.password).getHistory(
                            status = status,
                            service = service,
                            userid = user?.id
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

    private fun updateUI(responseHistory: ResponseHistory) {
        responseHistory.history?.let { adapterHistory.addItemHistory(it) }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param titleTarif Parameter 1.
         * @param status Parameter 2.
         * @param service Parameter 2.
         * @return A new instance of fragment FinishedFragment.
         */
        @JvmStatic
        fun newInstance(titleTarif: String, status: Int, service: Int) =
            PengantaranFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE_TARIF, titleTarif)
                    putInt(STATUS, status)
                    putInt(SERVICE, service)
                }
            }
    }
}