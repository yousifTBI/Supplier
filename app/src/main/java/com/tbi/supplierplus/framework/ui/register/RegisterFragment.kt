package com.tbi.supplierplus.framework.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.utils.getAndroidID
import com.tbi.supplierplus.business.utils.getSerialID
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.FragmentRegisterBinding
import com.tbi.supplierplus.databinding.FragmentSplashBinding
import com.tbi.supplierplus.framework.ui.splash.SplashFragmentDirections
import com.tbi.supplierplus.framework.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.btnRegister.setOnClickListener {
            lifecycleScope.launch {
                viewModel.register().collect {
                    if (it.isNotEmpty())
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.login().collect {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                if (it.userID != "0" && it.userID.isNotEmpty()) {
                    findNavController().navigate(
                        RegisterFragmentDirections.actionRegisterFragmentToMainFragment(
                           // it
                        )
                    )
                    Log.i("ResultUserID", it.userID + it.userID.length)
                    viewModel.saveUserData(it)
                }
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.onShowMsgDone()
            }
        }
        return binding.root
    }
}

