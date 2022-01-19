package com.surelabsid.newmrjempoot.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.mukesh.countrypicker.CountryPicker
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.BuildConfig
import com.surelabsid.newmrjempoot.databinding.ActivityLoginBinding
import com.surelabsid.newmrjempoot.landing.SuccessActivity
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.network.verihubs.VerihubsViewModel
import com.surelabsid.newmrjempoot.register.RegisterActivity
import com.surelabsid.newmrjempoot.utils.Constant
import com.surelabsid.newmrjempoot.utils.db
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userModel: UserModel
    private lateinit var picker: CountryPicker
    private var isVerified = false
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var verihubsViewModel: VerihubsViewModel
    private lateinit var gso: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        verihubsViewModel = ViewModelProvider(this).get(VerihubsViewModel::class.java)

        setContentView(binding.root)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .requestProfile()
            .build()

        getCountryCode()

        binding.clickdaftar.setOnClickListener {
            Intent(this@LoginActivity, RegisterActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.buttonlogin.setOnClickListener {
            if (binding.phonenumber.text.toString().isEmpty() || binding.password.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "Nomor telepon dan password harus diisi",
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

                userModel = UserModel(id = "")
                userModel.phone_number = cleanNumber
                userModel.password = binding.password.text.toString()
                userModel.token = Prefs.getString(Constant.TOKEN)

                if (BuildConfig.DEBUG) {
                    doLogin()
                } else {
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

        binding.countryCode.setOnClickListener {
            picker.showBottomSheet(this)
        }

        binding.verifyCode.buttonconfirm.setOnClickListener {
            doLogin()
        }

        binding.loginGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 102)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            val account = task?.getResult(ApiException::class.java)
            userModel = UserModel(id = "")
            userModel.email = account?.email
            userModel.customer_fullname = account?.displayName
            userModel.google_token = account?.id
            userModel.email_verified = "Y"
            Intent(this@LoginActivity, RegisterActivity::class.java).apply {
                putExtra(RegisterActivity.USERMODEDATA, userModel)
                startActivity(this)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    private fun doLogin() {
        loginViewModel.doLogin(userModel)
        loginViewModel.res.observe(this) {
            if(it.code == 200){
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val gson = Gson()
                        val str = gson.toJson(it.userData)
                        val userModel = gson.fromJson(str, UserModel::class.java)
                        db?.userDao()?.insertUser(userModel)
                        Prefs.putString(Constant.PHONE, userModel.phone_number)
                        Intent(this@LoginActivity, SuccessActivity::class.java).apply {
                            startActivity(this)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {

            }
        }
        loginViewModel.err.observe(this) {
            it.printStackTrace()
        }
    }

    private fun getCountryCode() {
        val builder = CountryPicker.Builder()
            .with(this)
            .listener {
                binding.countryCode.text = it.dialCode
                Glide.with(this)
                    .load(it.flag)
                    .into(binding.countryFlag)
            }.canSearch(false).sortBy(CountryPicker.SORT_BY_NAME)

        picker = builder.build()
        val country = picker.countryFromSIM
        if (country != null) {
            binding.countryCode.text = country.dialCode
            Glide.with(this)
                .load(country.flag)
                .into(binding.countryFlag)
        } else {
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
                    verihubsViewModel.verify.observe(this@LoginActivity) {
                        isVerified = it.code == 200
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}