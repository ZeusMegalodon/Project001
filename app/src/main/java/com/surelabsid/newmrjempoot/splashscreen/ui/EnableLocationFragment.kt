package com.surelabsid.newmrjempoot.splashscreen.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.FragmentEnableLocationBinding
import com.surelabsid.newmrjempoot.onboard.OnBoardActivity


class EnableLocationFragment : Fragment(R.layout.fragment_enable_location) {

    private lateinit var binding: FragmentEnableLocationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnableLocationBinding.bind(view)

        binding.enableLocationBtn.setOnClickListener {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), 2000
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requireActivity().finishAffinity()
                Intent(requireActivity(), OnBoardActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }
}