package com.surelabsid.newmrjempoot.splashscreen.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.FragmentSplashBinding
import com.surelabsid.newmrjempoot.onboard.OnBoardActivity
import com.surelabsid.newmrjempoot.utils.Constant

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private var handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)


        handler.postDelayed({
            if (!Prefs.contains(Constant.PHONE)) {
                checkPermission()
            } else {
                //on board activity
                requireActivity().finishAffinity()
                Intent(requireActivity(), OnBoardActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }, 3000)

    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerSplash, EnableLocationFragment())
                .commit()
        }else{
            requireActivity().finishAffinity()
            Intent(requireActivity(), OnBoardActivity::class.java).apply {
                startActivity(this)
            }
        }
        return
    }
}