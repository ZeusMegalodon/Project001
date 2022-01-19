package com.surelabsid.newmrjempoot.settings.helpcenter.adapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.FragmentLandingHelpCenterBinding
import com.surelabsid.newmrjempoot.settings.helpcenter.FaqFragment
import com.surelabsid.newmrjempoot.settings.helpcenter.HelpCenterActivity


class LandingHelpCenter : Fragment(R.layout.fragment_landing_help_center) {
    private lateinit var binding: FragmentLandingHelpCenterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLandingHelpCenterBinding.bind(view)

        binding.finish.setOnClickListener { requireActivity().finish() }

        binding.faq.setOnClickListener {
            (requireActivity() as HelpCenterActivity).supportFragmentManager.beginTransaction().replace(R.id.containerHelp, FaqFragment()).commit()
        }

        binding.chatCs.setOnClickListener {

        }
    }
}