package com.surelabsid.newmrjempoot.goban

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ActivityGobanVipBinding
import com.surelabsid.newmrjempoot.goceng.GocengActivity
import com.surelabsid.newmrjempoot.model.ExtraVIPModel
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.utils.Constant

class GobanVipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGobanVipBinding
    private var extraVIPModel = ExtraVIPModel()
    private var fasilitasTambahan = mutableListOf<String?>()
    private var allfiturItem: AllfiturItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGobanVipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allfiturItem = intent.getParcelableExtra(GocengActivity.SERVICE_DATA)

        binding.rgKendaraan.setOnCheckedChangeListener { group, checkedId ->
//            val selectedId = group.checkedRadioButtonId
//            val radio = findViewById<RadioButton>(selectedId)
//            val text = radio.text
            extraVIPModel.kendaraan = checkedId

            if (checkedId == R.id.billis1) {
                binding.billis1.background = ContextCompat.getDrawable(this, R.drawable.bg_red_)
                binding.billis1.setTextColor(ContextCompat.getColor(this, R.color.kuning))
                binding.billis2.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_white_border_red)
                binding.billis2.setTextColor(ContextCompat.getColor(this, R.color.red2))
            } else if (checkedId == R.id.billis2) {
                binding.billis1.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_white_border_red)
                binding.billis1.setTextColor(ContextCompat.getColor(this, R.color.red2))
                binding.billis2.background = ContextCompat.getDrawable(this, R.drawable.bg_red_)
                binding.billis2.setTextColor(ContextCompat.getColor(this, R.color.kuning))
            }
        }

        binding.softDrink.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fasilitasTambahan.add(getString(R.string.soft_drink))
                Constant.setDrawableColor(this@GobanVipActivity, binding.softDrink, R.color.kuning)
                binding.softDrink.setTextColor(ContextCompat.getColor(this, R.color.kuning))
                binding.softDrink.background = ContextCompat.getDrawable(this, R.drawable.bg_red_)
            } else {
                fasilitasTambahan.remove(getString(R.string.soft_drink))
                Constant.setDrawableColor(this@GobanVipActivity, binding.softDrink, R.color.red2)
                binding.softDrink.setTextColor(ContextCompat.getColor(this, R.color.red2))
                binding.softDrink.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_white_border_red)
            }
        }

        binding.barTable.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fasilitasTambahan.add(getString(R.string.bar_table))
                Constant.setDrawableColor(this@GobanVipActivity, binding.barTable, R.color.kuning)
                binding.barTable.setTextColor(ContextCompat.getColor(this, R.color.kuning))
                binding.barTable.background = ContextCompat.getDrawable(this, R.drawable.bg_red_)

            } else {
                fasilitasTambahan.remove(getString(R.string.bar_table))
                Constant.setDrawableColor(this@GobanVipActivity, binding.barTable, R.color.red2)
                binding.barTable.setTextColor(ContextCompat.getColor(this, R.color.red2))
                binding.barTable.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_white_border_red)
            }
        }

        binding.barTable2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fasilitasTambahan.add(getString(R.string.bar_table2))
                Constant.setDrawableColor(this@GobanVipActivity, binding.barTable2, R.color.kuning)
                binding.barTable2.setTextColor(ContextCompat.getColor(this, R.color.kuning))
                binding.barTable2.background = ContextCompat.getDrawable(this, R.drawable.bg_red_)
            } else {
                fasilitasTambahan.remove(getString(R.string.bar_table2))
                Constant.setDrawableColor(this@GobanVipActivity, binding.barTable2, R.color.red2)
                binding.barTable2.setTextColor(ContextCompat.getColor(this, R.color.red2))
                binding.barTable2.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_white_border_red)
            }
        }

        binding.finish.setOnClickListener {
            finish()
        }

        binding.pesanVip.setOnClickListener {
            extraVIPModel.tambahanFasilitasi = fasilitasTambahan.toList()
            Intent(this@GobanVipActivity, RingkasanExtraVipActivity::class.java).apply {
                putExtra(RingkasanExtraVipActivity.EXTRAVIP_DATA, extraVIPModel)
                putExtra(GocengActivity.SERVICE_DATA, allfiturItem)
                startActivity(this)
            }
        }
    }
}