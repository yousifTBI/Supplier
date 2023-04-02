package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.utils.fromJson
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentCustomerProfileBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.customers.CustomersFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class CustomerProfileFragment : Fragment()
    //, OnMapReadyCallback
{

    private lateinit var binding: FragmentCustomerProfileBinding
    val viewModel: SalesViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isPermissionGranted by Delegates.notNull<Boolean>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

   //    isPermissionGranted = isLocationPermissionGranted()

   //    binding.mapView.onCreate(savedInstanceState)
   //    binding.mapView.onResume()
   //    binding.mapView.getMapAsync(this)



   //    fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

   //    viewModel.items.observe(viewLifecycleOwner) {
   //        val adapter = ItemsAdapter(context!!, it)
   //        binding.spinner.adapter = adapter
   //    }

   //    binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
   //        @RequiresApi(Build.VERSION_CODES.O)
   //        override fun onItemSelected(
   //            parentView: AdapterView<*>?,
   //            selectedItemView: View?,
   //            position: Int,
   //            id: Long
   //        ) {
   //            viewModel.setCurrentItemID(
   //                viewModel.items.value!![position].id,
   //            )

   //        }

   //        override fun onNothingSelected(parentView: AdapterView<*>?) {
   //        }
   //    }
   //    binding.viewPager.adapter = BillsPager(parentFragmentManager)
   //    binding.tablayout.setupWithViewPager(binding.viewPager)

   //    viewModel.message.observe(viewLifecycleOwner) {
   //        if (it != null) {
   //            showSnackbar(activity!!, it)
   //            viewModel.onDoneMsg()
   //        }
   //    }
   //    val returnsAdapter = ReturnsAdapter()
   //    viewModel.returns.observe(viewLifecycleOwner) {
   //        if (it!!.size > 0) {
   //            Log.i("ReturnsSize", it.size.toString())
   //            binding.returnsRecycler.adapter = returnsAdapter
   //            returnsAdapter.submitList(it)
   //            binding.mortg3atLayout.visibility = View.VISIBLE
   //        } else binding.mortg3atLayout.visibility = View.GONE
   //    }

   //    val billAdapter = BillAdapter()
   //    viewModel.bill.observe(viewLifecycleOwner) {
   //        if (it!!.size > 0) {
   //            Log.i("BillSize", it.size.toString())
   //            binding.billRecycler.adapter = billAdapter
   //            billAdapter.submitList(it)
   //            binding.billLayout.visibility = View.VISIBLE
   //        } else binding.billLayout.visibility = View.GONE
   //    }

   //    viewModel.currentItem.observe(viewLifecycleOwner) {
   //        if (it == null)
   //            binding.itemCard.visibility = View.GONE
   //        else binding.itemCard.visibility = View.VISIBLE
   //    }

   //    viewModel.currentItemID.observe(viewLifecycleOwner) { currentItemID ->
   //        try {
   //            if (currentItemID != null) {
   //                viewModel.updateItem()
   //                binding.spinner.setSelection(viewModel.items.value!!.indexOf(viewModel.items.value!!.filter {
   //                    currentItemID.equals(
   //                        it.id
   //                    )
   //                }[0]))
   //            } else viewModel.getAllItems()
   //        } catch (e: Exception) {
   //        }
   //    }

   //    binding.btnQr.setOnClickListener {
   //        findNavController().navigate(
   //            CustomerProfileFragmentDirections.actionCustomerProfileFragmentToCustomerQRCodeScannerFragment(
   //                "item"
   //            )
   //        )

   //    }

   //    viewModel.navigateToExecute.observe(viewLifecycleOwner) {
   //        if (it) {
   //            findNavController().navigate(CustomerProfileFragmentDirections.actionCustomerProfileFragmentToExecuteCustomerFragment())
   //            viewModel.onDoneNavigateToExecute()
   //        }
   //    }

//        if (viewModel.bill.value!!.isEmpty() && viewModel.returns.value!!.isEmpty() && viewModel.customer.value!!.deferred.toFloat() == 0.0f)
//            binding.finishBtn.visibility = View.GONE
//        else binding.finishBtn.visibility = View.VISIBLE

        return binding.root
    }

 //  private fun isLocationPermissionGranted(): Boolean {

 //      return if (ActivityCompat.checkSelfPermission(
 //              activity!!,
 //              android.Manifest.permission.ACCESS_COARSE_LOCATION
 //          ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
 //              activity!!,
 //              android.Manifest.permission.ACCESS_FINE_LOCATION
 //          ) != PackageManager.PERMISSION_GRANTED
 //      ) {
 //          ActivityCompat.requestPermissions(
 //              activity!!,
 //              arrayOf(
 //                  android.Manifest.permission.ACCESS_FINE_LOCATION,
 //                  android.Manifest.permission.ACCESS_COARSE_LOCATION
 //              ),
 //              1000

 //          )
 //          binding.mapView.onResume()
 //          binding.mapView.getMapAsync(this)
 //          false
 //      } else {
 //          true
 //      }
 //  }

 //  @RequiresApi(Build.VERSION_CODES.O)
 //  @SuppressLint("MissingPermission")
 //  override fun onMapReady(map: GoogleMap) {
 //      mMap = map

 //      if (isPermissionGranted) {
 //          map.isMyLocationEnabled = true
 //      }
 //      getLastKnownLocation()


 //  }

 //  @RequiresApi(Build.VERSION_CODES.O)
 //  @SuppressLint("MissingPermission")
 //  fun getLastKnownLocation() {
 //      if (isPermissionGranted) {
 //          fusedLocationClient.lastLocation
 //              .addOnSuccessListener { location ->
 //                  if (location != null) {
 //                      drawMarker(location.latitude, location.longitude, "Current Location")
 //                      viewModel.getPlace(location.latitude, location.longitude)
 //                  }

 //              }
 //      } else Log.i(
 //          "Location",
 //          "Not granted"
 //      )

 //  }

 //  private fun drawMarker(lat: Double, lang: Double, title: String) {
 //      mMap.addMarker(
 //          MarkerOptions()
 //              .position(LatLng(lat, lang))
 //              .title(title)
 //      )
 //  }

}