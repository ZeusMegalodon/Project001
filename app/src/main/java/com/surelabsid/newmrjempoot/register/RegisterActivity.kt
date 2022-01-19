package com.surelabsid.newmrjempoot.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mukesh.countrypicker.CountryPicker
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.BuildConfig
import com.surelabsid.newmrjempoot.databinding.ActivityRegisterBinding
import com.surelabsid.newmrjempoot.landing.SuccessActivity
import com.surelabsid.newmrjempoot.login.LoginActivity
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.network.verihubs.VerihubsViewModel
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.db
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerModel: UserModel
    private lateinit var picker: CountryPicker
    private var isVerified = false

    private lateinit var verihubsViewModel: VerihubsViewModel
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        verihubsViewModel = ViewModelProvider(this).get(VerihubsViewModel::class.java)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        val usermodel = intent.getParcelableExtra<UserModel>(USERMODEDATA)
        if(usermodel != null){
            this.registerModel = usermodel

            updateUI()
        }

        binding.back.setOnClickListener {
            finish()
        }

        verihubsViewModel.err.observe(this) {
            Toast.makeText(this@RegisterActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
        }
        getCountryCode()

        binding.countryCode.setOnClickListener {
            picker.showBottomSheet(this)
        }

        binding.login.setOnClickListener {
            finishAffinity()
            Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.syaratDanKetentuan.setOnClickListener {

        }

        binding.submit.setOnClickListener {
            if (binding.email.text.toString().isEmpty().or(binding.nama.text.toString().isEmpty())
                    .or(binding.phonenumber.text.toString().isEmpty())
                    .or(binding.password.text.toString().isEmpty())
            ) {
                Toast.makeText(this, "Isi semua kolom yang disediakan", Toast.LENGTH_SHORT).show()
            } else if (!binding.accept.isChecked) {
                Toast.makeText(
                    this,
                    "Anda harus menerima syarat dan ketentuan terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var number = ""
                if (binding.phonenumber.text.toString().first() == '0') {
                    number = binding.phonenumber.text.toString().substring(0, 1).replace("0", "")
                    number += binding.phonenumber.text.toString()
                        .substring(1, binding.phonenumber.text.toString().length)
                } else {
                    number = binding.phonenumber.text.toString()
                }

                var countryCode =
                    binding.countryCode.text.toString().substring(0, 1).replace("+", "")
                countryCode += binding.countryCode.text.toString()
                    .substring(1, binding.countryCode.text.toString().length)

                val cleanNumber = countryCode + number

                registerModel = UserModel(id = "CUS-${System.currentTimeMillis()}")
                registerModel.email = binding.email.text.toString()
                registerModel.phone_number = cleanNumber
                registerModel.customer_fullname = binding.nama.text.toString()
                registerModel.password = binding.password.text.toString()
                registerModel.phone = binding.phonenumber.text.toString()
                registerModel.countrycode = countryCode

                if(BuildConfig.DEBUG){
                    doRegister()
                }else{
                    verihubsViewModel.requestOtp(cleanNumber)
                    verihubsViewModel.send.observe(this) {
                        if (it.code == 201) {
                            binding.viewflipper.displayedChild = 1
                            codeListener(cleanNumber)
                            binding.verifyCode.sendtotxt.text = cleanNumber
                        }
                    }
                }
            }
        }
        binding.verifyCode.buttonconfirm.setOnClickListener {
            doRegister()
        }
    }

    private fun updateUI() {
        binding.nama.setText(registerModel.customer_fullname)
        binding.email.setText(registerModel.email)
    }

    private fun getCountryCode() {
        val builder = CountryPicker.Builder()
            .with(this)
            .listener {
                binding.countryCode.text = it.dialCode
                Glide.with(this)
                    .load(it.flag)
                    .into(binding.countryFlag)
            }.canSearch(false)
            .sortBy(CountryPicker.SORT_BY_NAME)
        picker = builder.build()
        val country = picker.countryFromSIM
        if(country != null) {
            binding.countryCode.text = country.dialCode
            Glide.with(this)
                .load(country.flag)
                .into(binding.countryFlag)
        }else{
            binding.countryCode.text = "+62"
        }
    }

    private fun codeListener(cleanNumber: String) {
        binding.verifyCode.numone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.verifyCode.numtwo.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.verifyCode.numtwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.verifyCode.numthree.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.verifyCode.numthree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.verifyCode.numfour.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.verifyCode.numfour.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.verifyCode.numfive.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.verifyCode.numfive.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.verifyCode.numsix.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.verifyCode.numsix.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    val finalCode = binding.verifyCode.numone.text.toString() +
                            binding.verifyCode.numtwo.text.toString() +
                            binding.verifyCode.numthree.text.toString() +
                            binding.verifyCode.numfour.text.toString() +
                            binding.verifyCode.numfive.text.toString() +
                            binding.verifyCode.numsix.text.toString()

                    verihubsViewModel.verifyOtp(cleanNumber, finalCode)
                    verihubsViewModel.verify.observe(this@RegisterActivity) {
                        isVerified = it.code == 200
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun doRegister() {
        registerViewModel.doRegister(registerModel)
        registerViewModel.res.observe(this) {
            if (it.code == 200) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        db?.userDao()?.insertUser(registerModel)
                        Prefs.putString(Constant.PHONE, registerModel.phone_number)
                        Intent(this@RegisterActivity, SuccessActivity::class.java).apply {
                            startActivity(this)
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }
        registerViewModel.err.observe(this) {

        }

    }

    companion object{
        const val USERMODEDATA = "usermodel"
    }
}