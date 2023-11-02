package com.tbi.supplierplus.framework.ui2.mortag3

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.BillActivity2
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.VisitBranchWithoutPayModel
import com.tbi.supplierplus.databinding.ActivitySellOrMortg3Binding
import com.tbi.supplierplus.framework.getLocation.MyLocation
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.backgroundService.BackgroundService
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SellOrMortg3Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySellOrMortg3Binding
    private val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    var loc: Location? = null
    lateinit var message: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_or_mortg3)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sell_or_mortg3);
        message = intent.getStringExtra("Customer_ID").toString()
        val message2 = intent.getStringExtra("Unpaid_deferred")
        val CompanyName = intent.getStringExtra("CompanyName")



        binding.invoiceCard.setOnClickListener {
            val intent = Intent(this, BillActivity2::class.java)
                .apply {
                    putExtra("Customer_ID", message)
                    putExtra("Unpaid_deferred", message2)
                    putExtra("CompanyName", CompanyName)

                }
            startActivity(intent)
        }


        binding.reportsCard.setOnClickListener {
            val intent = Intent(this, Mortag3BillActivity::class.java)
                .apply {
                    putExtra("Customer_ID", message)
                    putExtra("Unpaid_deferred", message2)
                    putExtra("CompanyName", CompanyName)

                }
            startActivity(intent)
        }
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
        myLocation.getLocation(this, locationResult)

        val serviceIntent = Intent(applicationContext, BackgroundService::class.java)
        this.startService(serviceIntent)

        binding.withoutSellingID.setOnClickListener {

            lifecycleScope.launch() {
                availableItemsViewModel.VisitBranchWithoutPay(
                    VisitBranchWithoutPayModel(
                        SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),
                        message.toInt(),
                        loc!!.longitude,
                        loc!!.latitude

                    )
                  ).collect {

                    when (it) {
                        is State.Loading ->{

                        }
                        is State.Success -> {

                            Log.d("VisitBranchWithoutPay", "Success")
                            Toast.makeText(applicationContext,it.data.Message,Toast.LENGTH_SHORT).show()
                           // Log.d("VisitBranchWithoutPay", message+"BranchID")
                           // Log.d("VisitBranchWithoutPay", loc!!.longitude.toString()+"longitude")
                           // Log.d("VisitBranchWithoutPay",  loc!!.latitude.toString()+"latitude")
                           // Log.d("VisitBranchWithoutPay",  SharedPreferencesCom.getInstance().gerSharedUser_ID().toString()+"User_ID")
                           // it.data.message


                        }
                        is State.Error -> {
                            Log.d("VisitBranchWithoutPay","Error")
                        }
                    }
                }

            }

        }


    }
}