package com.tbi.supplierplus.framework.ui

import android.content.ContentResolver
import android.content.Intent
import android.location.Location
import android.opengl.Visibility
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.databinding.FragmentMainBinding
import com.tbi.supplierplus.framework.getLocation.MyLocation
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.addNewItem.AddItemActivity
import com.tbi.supplierplus.framework.ui.backgroundService.BackgroundService
import com.tbi.supplierplus.framework.ui.closingLast.ClosingActivity
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.purchase.PurchaseActivity
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.ChangePriceActivity
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
   private val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    var loc: Location? = null
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

         val androidId: String = Settings.Secure.getString(
             requireActivity().contentResolver,
             Settings.Secure.ANDROID_ID
         )

    //     Log.d("androidId",androidId)

         val locationResult: MyLocation.LocationResult = object : MyLocation.LocationResult() {
             override fun gotLocation(location: Location) {
                 loc = location
             //   System.out.println("allah: " + loc!!.latitude)
             //   System.out.println("allah: " + loc!!.longitude)
             //     Log.d("allahAkbr",  loc!!.latitude.toString())
             //  Log.d("allahAkbr",  loc!!.longitude.toString())
             }
         }

         val myLocation = MyLocation()
         myLocation.getLocation(context, locationResult)

         val serviceIntent = Intent(requireContext(), BackgroundService::class.java)
         requireContext().startService(serviceIntent)

         lifecycleScope.launch() {
             availableItemsViewModel.TestHeader().collect {

                 when (it) {
                     is State.Loading ->{

                     }
                     is State.Success -> {
                      //   Log.d("TestHeader",it.data.Message.toString())

                     }
                     is State.Error -> {
                       //  Log.d("TestHeader",it.messag)
                     }
                 }
             }

         }

         binding.stock.setOnClickListener {

            findNavController().navigate(
                MainFragmentDirections.actionGlobalStoreFragment(
                )
            )



        }

        binding.sales.setOnClickListener {

            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToCustomersFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")
                )

            )

        }

        binding.saless.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSalesSettingFragment()

            )
        }


        binding.car.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSettingsFragment()

            )
        }



        binding.sales2.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDailyClosingFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )
        }

        binding.saless2.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalCustomerSettingFragment(

                )
            )
        }



      binding.car2.setOnClickListener {
          findNavController().navigate(
              MainFragmentDirections.actionMainFragmentToItemsSettlementFragment(

                  User("peter_tbi", "", "", "3", "", "2", "", "")

              )

          )
      }

          binding.sales3.setOnClickListener {
              findNavController().navigate(
                  MainFragmentDirections.actionGlobalReportsFragment()

              )
          }

         lifecycleScope.launch() {
             availableItemsViewModel.GetUserInfo().collect {

                 when (it) {
                     is State.Loading ->{

                     }
                     is State.Success -> {
                         Log.d("billNumToCreateQR","Success")
                         binding.textView101.setText(it.data.item.toString())

                     }
                     is State.Error -> {
                         Log.d("billNumToCreateQR","Error")
                     }
                 }
             }

         }


         GlobalScope.launch {
             main()

        //     Log.d("stlillwork","stillwork")
         }

        return binding.root
    }

    fun main() {
        val executorService = Executors.newSingleThreadScheduledExecutor()

        // Define the task to be executed
        val task = {
            // Call your method here
            myMethod()
        }

        // Schedule the task to run every 5 minutes
        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES)
    }

    fun myMethod() {
        Log.d("stlillwork","stillwork")
        Log.d("billNumToCreateQR","myMethod")
        // Implement your method logic here
        // This method will be executed every 5 minutes
        lifecycleScope.launch() {
            availableItemsViewModel.AddLocationPointToUser( loc!!.longitude, loc!!.latitude).collect {

                when (it) {
                    is State.Loading ->{

                    }
                    is State.Success -> {
                        Log.d("billNumToCreateQR","Success")
                        
                    }
                    is State.Error -> {
                        Log.d("billNumToCreateQR","Error")
                    }
                }
            }

        }
    }
}


