package com.tbi.supplierplus.framework.ui.registration

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tbi.supplierplus.business.pojo.RegistrationModel
import com.tbi.supplierplus.databinding.ActivityRegistrationBinding
import com.tbi.supplierplus.framework.ui.login.AccountViewModel
import com.tbi.supplierplus.framework.ui.login.LoginAccActivity
import com.tbi.supplierplus.framework.ui.login.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private val viewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val androidId: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )

binding.spinKit.isVisible=false
        binding.SendRequestId.setOnClickListener{
          if (  CheckAllFields() ) {
            viewModel.RegistrationInfo(RegistrationModel(
                androidId,
                binding.com.text.toString(),
                binding.POStxt.text.toString().toInt()

            )
            ,this)
            viewModel.registrationLiveData.observe(this){
                when (it) {
                    is State.Loading -> {binding.spinKit.isVisible=true}
                    is State.Success -> {
                        binding.spinKit.isVisible=false

                        Log.d("registrationLiveData",it.data.message)
                        Toast.makeText(applicationContext,it.data.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginAccActivity::class.java)
                        startActivity(intent)

                        finish()

                    }
                    is State.Error -> {
                        binding.spinKit.isVisible=false


                        Toast.makeText(applicationContext,"خطا", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        }
    }

    private fun CheckAllFields(): Boolean {
        if (binding.com.length() == 0) {
            binding.com.setError("This field is required")
            return false
        }
        else if (binding.POStxt.length() == 0) {
            binding.POStxt.setError("This field is required")
            return false
        }

        // after all validation return true.
        return true
    }
}