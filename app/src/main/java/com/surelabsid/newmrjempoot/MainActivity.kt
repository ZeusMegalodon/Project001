package com.surelabsid.newmrjempoot

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.cart.CartFragment
import com.surelabsid.newmrjempoot.databinding.ActivityMainBinding
import com.surelabsid.newmrjempoot.favorite.ui.WishListFragment
import com.surelabsid.newmrjempoot.home.HomeFragment
import com.surelabsid.newmrjempoot.settings.akun.ProfileActivity
import com.surelabsid.newmrjempoot.settings.alamat.AddressActivity
import com.surelabsid.newmrjempoot.settings.alamat.SavedAddressActivity
import com.surelabsid.newmrjempoot.settings.changelang.ChangeLanguageActivity
import com.surelabsid.newmrjempoot.settings.helpcenter.HelpCenterActivity
import com.surelabsid.newmrjempoot.settings.notifikasi.NotifikasiActivity
import com.surelabsid.newmrjempoot.settings.privacy.PrivacyActivity
import com.surelabsid.newmrjempoot.wallet.WalletLandingActivity


class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var def: ColorStateList? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainInclude.home.setOnClickListener(this)
        binding.mainInclude.cart.setOnClickListener(this)
        binding.mainInclude.history.setOnClickListener(this)
        binding.mainInclude.wishlist.setOnClickListener(this)


        def = binding.mainInclude.cart.textColors

        changeFragment(HomeFragment())
        configureItemClick()
    }

    fun configureDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun configureItemClick() {
        binding.sidebar.jempootPay.setOnClickListener {
            val wallet = Intent(
                this@MainActivity,
                WalletLandingActivity::class.java
            )
            startActivity(wallet)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.sidebar.editProfile.setOnClickListener {
            val profile = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(profile)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.sidebar.alamatTersimpan.setOnClickListener {
            val address = Intent(this@MainActivity, SavedAddressActivity::class.java)
            startActivity(address)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.sidebar.pengaturanNotif.setOnClickListener {
            val notif = Intent(this@MainActivity, NotifikasiActivity::class.java)
            startActivity(notif)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.sidebar.pilihanBahasa.setOnClickListener {
            val pilihan = Intent(
                this@MainActivity,
                ChangeLanguageActivity::class.java
            )
            startActivity(pilihan)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.sidebar.pusatBantuan.setOnClickListener {
            val pusatBantuanIntent = Intent(
                this@MainActivity,
                HelpCenterActivity::class.java
            )
            startActivity(pusatBantuanIntent)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.sidebar.privacyPolicy.setOnClickListener {
            val i = Intent(this@MainActivity, PrivacyActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            i.putExtra(PrivacyActivity.IS_PRIVACY, PrivacyActivity.IS_PRIVACY)
            i.putExtra("judul", "Kebijakan Privasi")
            startActivity(i)
        }

        binding.sidebar.syaratDanKetentuan.setOnClickListener {
            val i = Intent(this@MainActivity, PrivacyActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            i.putExtra(PrivacyActivity.IS_SYARAT, PrivacyActivity.IS_SYARAT)
            i.putExtra("judul", "Syarat dan Ketentuan")
            startActivity(i)
        }

        binding.sidebar.logout.setOnClickListener { logout() }

        binding.sidebar.username.text = user?.customer_fullname
        binding.sidebar.useremail.text = user?.phone_number
    }

    private fun logout() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home -> {
                binding.mainInclude.home.setTextColor(ActivityCompat.getColor(this@MainActivity, R.color.black2))
                binding.mainInclude.cart.setTextColor(def)
                binding.mainInclude.history.setTextColor(def)
                binding.mainInclude.wishlist.setTextColor(def)
                binding.mainInclude.select.animate().x(0f).duration = 100
                changeFragment(HomeFragment())
            }

            R.id.cart -> {
                binding.mainInclude.home.setTextColor(def)
                binding.mainInclude.cart.setTextColor(ActivityCompat.getColor(this@MainActivity,R.color.black2))
                binding.mainInclude.history.setTextColor(def)
                binding.mainInclude.wishlist.setTextColor(def)
                val size = binding.mainInclude.cart.width
                binding.mainInclude.select.animate().x(size.toFloat()).duration = 100
                changeFragment(CartFragment())
            }
            R.id.history -> {
                binding.mainInclude.home.setTextColor(def)
                binding.mainInclude.history.setTextColor(ActivityCompat.getColor(this@MainActivity,R.color.black2))
                binding.mainInclude.cart.setTextColor(def)
                binding.mainInclude.wishlist.setTextColor(def)
                val size = binding.mainInclude.cart.width * 2
                binding.mainInclude.select.animate().x(size.toFloat()).duration = 100
            }
            R.id.wishlist -> {
                binding.mainInclude.home.setTextColor(def)
                binding.mainInclude.wishlist.setTextColor(ActivityCompat.getColor(this@MainActivity,R.color.black2))
                binding.mainInclude.cart.setTextColor(def)
                binding.mainInclude.history.setTextColor(def)
                val size = binding.mainInclude.cart.width * 3
                binding.mainInclude.select.animate().x(size.toFloat()).duration = 100
                changeFragment(WishListFragment.newInstance(isCalledFromActivity = false))
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
            .commit()
    }
}