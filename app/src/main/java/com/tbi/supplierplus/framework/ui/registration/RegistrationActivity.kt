package com.tbi.supplierplus.framework.ui.registration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tbi.supplierplus.business.pojo.RegistrationModel
import com.tbi.supplierplus.databinding.ActivityRegistrationBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.AccountViewModel
import com.tbi.supplierplus.framework.ui.login.LoginAccActivity
import com.tbi.supplierplus.framework.ui.login.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private val viewModel: AccountViewModel by viewModels()
    private val REQUEST_CODE_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val androidId: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()



      //  SharedPreferencesCom.getInstance().getSerial_ID()
      //  Log.d("getSerial_ID",  SharedPreferencesCom.getInstance().getSerial_ID()+"getSerial_ID")
        binding.spinKit.isVisible = false
        binding.SendRequestId.setOnClickListener {
            if (CheckAllFields()) {
                SharedPreferencesCom.getInstance().setSerial_ID(binding.POSSerial.text.toString())
                viewModel.RegistrationInfo(
                    RegistrationModel(
                        binding.POSSerial.text.toString(),
                        androidId,
                        binding.com.text.toString(),
                        binding.POStxt.text.toString().toInt()

                    ), this
                )
                viewModel.registrationLiveData.observe(this) {
                    when (it) {
                        is State.Loading -> {
                            binding.spinKit.isVisible = true
                        }
                        is State.Success -> {
                            binding.spinKit.isVisible = false

  //                          Log.d("registrationLiveData", it.data.message)
                            Toast.makeText(applicationContext, it.data.message, Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, LoginAccActivity::class.java)
                            startActivity(intent)

                            finish()

                        }
                        is State.Error -> {
                            binding.spinKit.isVisible = false


                            Toast.makeText(applicationContext, "خطا", Toast.LENGTH_SHORT).show()
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
        } else if (binding.POStxt.length() == 0) {
            binding.POStxt.setError("This field is required")
            return false
        }

     else if (binding.POSSerial.length() == 0) {
            binding.POSSerial.setError("This field is required")
            return false
        }

        // after all validation return true.
        return true
    }



    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted
            getCurrentLocation()
        } else {
            // Permission denied
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user
                // You can display a dialog or a Snackbar with an explanation
            } else {
                // No explanation needed, request the permission
                requestLocationPermission()
            }
        } else {
            // Permission already granted
            getCurrentLocation()
        }
    }
    private fun getCurrentLocation() {
        Log.d("getCurrentLocation","getCurrentLocation")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {

                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("getCurrentLocation","innnnn")
                    Log.d("getCurrentLocation",latitude.toString())
                    Log.d("getCurrentLocation",longitude.toString())
                    // Do something with the location coordinates
                }
            }
            .addOnFailureListener { e ->
                // Handle location retrieval failure
                Log.d("getCurrentLocation","Unable to get current location")
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d("getCurrentLocation","onCreate")

        checkLocationPermission()
    }

}