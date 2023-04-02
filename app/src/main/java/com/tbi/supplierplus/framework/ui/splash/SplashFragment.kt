package com.tbi.supplierplus.framework.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

 // val usar:User("","","","","","","","")
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       // var usar:User("s","dd","f","g","","","","")
       // usar("","")
        binding = FragmentSplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
    //   lifecycleScope.launch {
    //       delay(2000)
    //       lifecycleScope.launch {
    //           viewModel.apply {
    //               this.user.observe(viewLifecycleOwner) {
    //                   if (it != null) {
    //                       findNavController().navigate(
    //                           SplashFragmentDirections.actionSplashFragmentToMainFragment(
    //                               it
    //                           )
    //                       )
    //                       onDoneNavigation()
    //                   }
    //               }
    //               this.navToRegister.observe(viewLifecycleOwner) {
    //                   if (it) {
    //                       findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())
    //                       onDoneNavigation()
    //                   }
    //               }
    //           }
    //       }
    //   }


        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToMainFragment(

                //User("peter_tbi", "", "", "3", "", "2", "", "")
            )
        )
        return binding.root
    }
}
