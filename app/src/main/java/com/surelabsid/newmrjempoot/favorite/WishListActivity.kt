package com.surelabsid.newmrjempoot.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.favorite.ui.WishListFragment

class WishListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wish_list_activity)
        val callFromActivity = intent.getBooleanExtra(CALLFROM_ACTIVITY, false)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WishListFragment.newInstance(callFromActivity))
                .commitNow()
        }
    }
    companion object{
        const val CALLFROM_ACTIVITY = "callFromActivity"
    }
}