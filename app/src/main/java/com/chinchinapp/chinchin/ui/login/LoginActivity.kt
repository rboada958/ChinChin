package com.chinchinapp.chinchin.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.databinding.ActivityLoginBinding
import com.chinchinapp.chinchin.ui.MainActivity
import com.chinchinapp.chinchin.ui.login.mvvm.LoginViewModel
import com.chinchinapp.chinchin.utils.base.BaseActivity
import com.chinchinapp.chinchin.utils.enums.Status
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.enableButton.observe(this, Observer {
            if (it != null) {
                when (it) {
                    Status.ENABLE.name -> btn_login.alpha = 1.0F
                    Status.DISABLE.name -> btn_login.alpha = 0.1F
                }
            }
        })

        viewModel.mutableErrorMessage.observe(this, Observer {
            it.getContentIfNotHandled()?.let { msg ->
                showSnackError(msg, true)
            }
        })

        viewModel.mutableSuccessMessage.observe(this, Observer {
            if (it.getContentIfNotHandled() != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}