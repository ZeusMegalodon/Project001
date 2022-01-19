package com.surelabsid.newmrjempoot.goceng

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.base.BaseActivity
import com.surelabsid.newmrjempoot.databinding.ActivityLandingGocengBinding
import com.surelabsid.newmrjempoot.history.PengantaranFragment
import com.surelabsid.newmrjempoot.member.LandingMemberActivity
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.utils.Constant

class LandingGocengActivity : BaseActivity() {

    private lateinit var binding: ActivityLandingGocengBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingGocengBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val allfiturItem = intent.getParcelableExtra<AllfiturItem>(GocengActivity.SERVICE_DATA)

        Glide.with(this)
            .load(Constant.IMAGESFITUR + allfiturItem?.icon)
            .into(binding.menu)

        binding.label.text = allfiturItem?.service
        binding.backhome.setOnClickListener {
            finish()
        }

        binding.memberShip.visibility = View.VISIBLE
        binding.memberShip.setOnClickListener {
            Intent(this@LandingGocengActivity, LandingMemberActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.goTo.setOnClickListener {
            Intent(this@LandingGocengActivity, GocengActivity::class.java).apply {
                putExtra(GocengActivity.SERVICE_DATA, allfiturItem)
                startActivity(this)
            }
        }

        binding.groupOne.setOnSegmentedGroupListener { tab, checkedId ->
            when (checkedId) {
                R.id.element_one_one -> {
                    binding.container.visibility = View.GONE
                    binding.aktifContainer.visibility = View.VISIBLE
                }
                R.id.element_one_two -> {
                    binding.container.visibility = View.VISIBLE
                    binding.aktifContainer.visibility = View.GONE
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container, PengantaranFragment.newInstance(
                            titleTarif = "Tarif Jempoot Motorbike",
                            status = 3,
                            service = allfiturItem?.serviceId.toString().toInt(),
                        )
                    ).commit()
                }
                R.id.element_one_three -> {
                    binding.container.visibility = View.VISIBLE
                    binding.aktifContainer.visibility = View.GONE
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container, PengantaranFragment.newInstance(
                            titleTarif = "Tarif Jempoot Motorbike",
                            status = 4,
                            service = allfiturItem?.serviceId.toString().toInt(),
                        )
                    ).commit()
                }
            }
        }
    }
}