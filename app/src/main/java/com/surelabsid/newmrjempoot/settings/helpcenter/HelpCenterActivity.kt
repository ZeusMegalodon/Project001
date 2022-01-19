package com.surelabsid.newmrjempoot.settings.helpcenter

import android.os.Bundle
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.settings.helpcenter.adapter.LandingHelpCenter

class HelpCenterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_center)

        supportFragmentManager.beginTransaction().replace(R.id.containerHelp, LandingHelpCenter())
            .commit()
    }
}