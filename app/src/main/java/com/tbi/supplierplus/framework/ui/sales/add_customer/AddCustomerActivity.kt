package com.tbi.supplierplus.framework.ui.sales.add_customer

import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.zxing.integration.android.IntentIntegrator
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.ActivityAddCustomerBinding
import com.tbi.supplierplus.databinding.FragmentClientOperationBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.CustomerModel
import com.tbi.supplierplus.framework.ui.scanner.capture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddCustomerBinding
    lateinit var viewModel: AddCustomerViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
     lateinit var locationRequest: LocationRequest

    var long = 0.0
    var lat = 0.0



    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_add_customer)


        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_customer)
        viewModel = ViewModelProvider(this).get(AddCustomerViewModel::class.java)



//Log.d("djsod", SharedPreferencesCom.getInstance().gerSharedUser_ID()+
//    SharedPreferencesCom.getInstance().gerSharedDistributor_ID())
      //  viewModel.addCustomerVM()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.SubmittttttID.setOnClickListener {


//            checkPermissions()
//           fetchLocation()



            if (CheckAllFields()) {
                binding.spinKit.isVisible=true

                viewModel.addCustomerVM(
                    CustomerModel(
                        1,
                        binding.com.text.toString(),
                        "",
                       "",
                       "",
                        "",
                       "",
                        "",
                        "",
                        "",
                        "",
                        SharedPreferencesCom.getInstance().gerSharedDistributor_ID().toInt(),
                        0,
                        1,
                        "",
                        SharedPreferencesCom.getInstance().gerSharedUser_ID(),
                    )
                )
                        //  SharedPreferencesCom.getInstance().gerSharedDistributor_ID(),

//                        binding.regin.text.toString(),
//                        binding.postalcode.text.toString(),
                        //                        it.latitude,
                        //                        it.longitude

            }


        }
        binding.spinKit.isVisible=false
        viewModel.CustomerLiveData.observe(this) {
            when (it) {
                is State.Loading -> {}
                is State.Success -> {
                    binding.spinKit.isVisible=false


                   binding.com.setText("")
                    binding.numOfRecord.setText("")
                            binding.comCode.setText("")
                    binding.branchcode.setText("")
                    binding.streat.setText("")
                    binding.BuildingNumber.setText("")
                    if (it.data.State == 1) {
                        Toast.makeText(
                            applicationContext,
                            it.data.Message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(
                            applicationContext,
                            it.data.Message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (it.data.State == 2) {
                        Toast.makeText(
                            applicationContext,
                            it.data.Message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(
                            applicationContext,
                            it.data.Message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            it.data.Message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(
                            applicationContext,
                            it.data.Message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }

                is State.Error -> {

                    binding.spinKit.isVisible=false
                }



            }
        }


    }

    private fun CheckAllFields(): Boolean {
        if (binding.com.length() == 0) {
            binding.com.setError("This field is required")
            return false
        }

        // after all validation return true.
        return true
    }

     private fun checkPermissions() {
         if (ActivityCompat.checkSelfPermission(
                 this,
                 android.Manifest.permission.ACCESS_FINE_LOCATION
             )
             != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 this,
                 android.Manifest.permission.ACCESS_COARSE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             ActivityCompat.requestPermissions(
                 this, arrayOf(
                     android.Manifest.permission.ACCESS_FINE_LOCATION,
                     android.Manifest.permission.ACCESS_COARSE_LOCATION
                 ), 101
             )
             checkGPS()
         } else {
             checkGPS()
         }
     }


     private fun fetchLocation() {

         var task = fusedLocationClient.lastLocation
 //        val location: Location? = task.result

 //        Log.d("gmmIntentUri", long+"lll")
         task.addOnSuccessListener {
  //         Log.d("gmmIntentUri",it.latitude.toString())


             if (it != null) {

                 if (CheckAllFields()) {
                     binding.spinKit.isVisible=true

                     viewModel.addCustomerVM(
                         CustomerModel(
                             1,
                             binding.com.text.toString(),
                             binding.numOfRecord.text.toString(),
                             binding.comCode.text.toString(),
                             binding.branchcode.text.toString(),
                             binding.streat.text.toString(),
                             binding.BuildingNumber.text.toString(),
                             "",
                             "sample string 9",
                             "2023-05-20T11:07:07.9181084+03:00",
                             "sample string 10",
                             1,
                             0,
                             1,
                             "sample string 11",
                             SharedPreferencesCom.getInstance().gerSharedUser_ID(),
                           //  SharedPreferencesCom.getInstance().gerSharedDistributor_ID(),

//                        binding.regin.text.toString(),
//                        binding.postalcode.text.toString(),
     //                        it.latitude,
     //                        it.longitude
                         )
                     )



                 }
                 long = it.longitude
                 lat = it.latitude
//                Log.d("gmmIntentUdsdri", long.toString()+"two")

//                 val gmmIntentUri =
//                     Uri.parse("geo:$lat,$long?q=hotels")
// //                Log.d("gmmIntentUri", long+"three")
//                 val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                 mapIntent.setPackage("com.google.android.apps.maps")
//                 startActivity(mapIntent)
             }
         }

     }

       private fun checkGPS() {
         locationRequest = LocationRequest.create()
         locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
         locationRequest.interval = 5000
         locationRequest.fastestInterval = 2000

         val builder = LocationSettingsRequest.Builder()
             .addLocationRequest(locationRequest)

         val result = LocationServices.getSettingsClient(
             this
         ).checkLocationSettings(builder.build())

         result.addOnCompleteListener {

             try {

                 val response = it.getResult(ApiException::class.java)


             } catch (e: ApiException) {

                 e.printStackTrace()

                 when (e.statusCode) {

                     LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {

                         val resolveApiException = e as ResolvableApiException
                         resolveApiException.startResolutionForResult(this, 200)

                     } catch (sendIntentException: IntentSender.SendIntentException) {

                     }
                 }
             }
         }
     }
//    fun onLocationChanged(location: Location) {
//        //  val locationTv = findViewById<View>(R.id.latlongLocation) as TextView
//        val latitude: Double = location.getLatitude()
//        val longitude: Double = location.getLongitude()
//        val latLng = LatLng(latitude, longitude)
//        Log.d("onLocationChanged",latitude.toString())
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun obtieneLocalizacion(){
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location: Location? ->
//                lat = location?.latitude!!
//                long = location?.longitude!!
//
//                Log.d("gmmIntentUri", long.toString())
//
//            }
//    }

//    private fun checkLocation(){
//        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            showAlertLocation()
//        }
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        getLocationUpdates()
//    }
//
//    private fun showAlertLocation() {
//        val dialog = AlertDialog.Builder(this)
//        dialog.setMessage("Your location settings is set to Off, Please enable location to use this application")
//        dialog.setPositiveButton("Settings") { _, _ ->
//            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            startActivity(myIntent)
//        }
//        dialog.setNegativeButton("Cancel") { _, _ ->
//            finish()
//        }
//        dialog.setCancelable(false)
//        dialog.show()
//    }
//
//    private fun getLocationUpdates() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        locationRequest = LocationRequest()
//        locationRequest.interval = 50000
//        locationRequest.fastestInterval = 50000
//        locationRequest.smallestDisplacement = 170f //170 m = 0.1 mile
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //according to your app
//
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                locationResult ?: return
//                if (locationResult.locations.isNotEmpty()) {
//                    /*val location = locationResult.lastLocation
//                    Log.e("location", location.toString())*/
//                    val addresses: List<Address>?
//                    val geoCoder = Geocoder(applicationContext, Locale.getDefault())
//                    addresses = geoCoder.getFromLocation(
//                        locationResult.lastLocation!!.latitude,
//                        locationResult.lastLocation!!.longitude,
//                        1
//                    )
//                    if (addresses != null && addresses.isNotEmpty()) {
//                        val address: String = addresses[0].getAddressLine(0)
//                        val city: String = addresses[0].locality
//                        val state: String = addresses[0].adminArea
//                        val country: String = addresses[0].countryName
//                        val postalCode: String = addresses[0].postalCode
//                        val knownName: String = addresses[0].featureName
//                        Log.e("location", "$address $city $state $postalCode $country $knownName")
//                    }
//                }
//            }
//        }
//    }

    // Start location updates
//    private fun startLocationUpdates() {
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            null /* Looper */
//        )
//    }
//
//    // Stop location updates
//    private fun stopLocationUpdates() {
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }
//
//    // Stop receiving location update when activity not visible/foreground
//    override fun onPause() {
//        super.onPause()
//        stopLocationUpdates()
//    }
//
//    // Start receiving location update when activity  visible/foreground
//    override fun onResume() {
//        super.onResume()
//        startLocationUpdates()
//    }
//    public fun getLastKnownLocation(context: Context) {
//        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val providers: List<String> = locationManager.getProviders(true)
//        var location: Location? = null
//        for (i in providers.size - 1 downTo 0) {
//            location= locationManager.getLastKnownLocation(providers[i])
//            if (location != null)
//                break
//        }
//        val gps = DoubleArray(2)
//        if (location != null) {
//            gps[0] = location.getLatitude()
//            gps[1] = location.getLongitude()
//            Log.e("gpsLat",gps[0].toString())
//            Log.e("gpsLong",gps[1].toString())
//            long = gps[0]
//            lat =  gps[1]
//
//        }
//
//    }
    fun  ProgressDialog(context: Context){
        val mProgressDialog = ProgressDialog(context)

    }
}