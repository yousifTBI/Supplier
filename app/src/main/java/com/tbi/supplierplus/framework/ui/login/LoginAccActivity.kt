package com.tbi.supplierplus.framework.ui.login

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.CallLog
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tbi.supplierplus.MainActivity
import com.tbi.supplierplus.databinding.ActivityLoginAccBinding
import com.tbi.supplierplus.framework.getLocation.MyLocation
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginAccActivity : AppCompatActivity() {
    private val viewModel: AccountViewModel by viewModels()
    private lateinit var binding: ActivityLoginAccBinding
    private val REQUEST_CODE_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var loc: Location? = null
    //  private lateinit var manifestPermissionsRequestor: ManifestPermissionsRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAccBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //   val intent = Intent(this, MainActivity::class.java)
        //   startActivity(intent)
        //   finish()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val androidId: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        Log.d("aziza3s2", androidId)



        checkLocationPermission()
        SharedPreferencesCom.init(this)
        val locationResult: MyLocation.LocationResult = object : MyLocation.LocationResult() {
            override fun gotLocation(location: Location) {
                loc = location
               // Log.d("allah",  loc!!.altitude.toString())
               // Log.d("allah",  loc!!.longitude.toString())
            }
        }
        val myLocation = MyLocation()
        myLocation.getLocation(this@LoginAccActivity, locationResult)


        viewModel.checkInternetConnection()
        viewModel.isInternetAvailable.observe(this) { isConnected ->
            if (!isConnected) {
               // Toast.makeText(applicationContext, "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show()
                binding.msgtxt.setText("لا يوجد اتصال بالانترنت")
                Log.d("aziza3s2", "لا يوجد اتصال بالانترنت")
                // Show "no internet" message or take appropriate action
                // For example, display a Snackbar or Toast
            }
        }


        // SharedPreferencesCom.getInstance().setSharedDistributor_ID("2")
        // SharedPreferencesCom.getInstance().setSharedUser_ID("2")

        // viewModel.loginInfoCombVM("qwertys1",this)
        binding.logoutId.setOnClickListener {
            //   SharedPreferencesCom.getInstance().setSerial_ID("")
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)

        }

        Log.d("loginInfoCombVLiveData",SharedPreferencesCom.getInstance().getSerial_ID())
     //   viewModel.loginInfoCombVM("158223899173",this)
      viewModel.loginInfoCombVM(androidId,this)
      viewModel.loginInfoCombVM(SharedPreferencesCom.getInstance().getSerial_ID(),this)
        viewModel.loginInfoCombVLiveData.observe(this) {

            when (it) {

                is State.Loading ->{}// Log.d("aziza", "")
                is State.Success -> {
                    Log.d("aziza32", it.data.Message.toString())
                    if (it.data!!.State == 0) {
 //                       Log.d("cfgdgdfgdfg", it.data.message)
 //                       your Request is pending
                        Log.d("aziza3s2", "State0")
                    } else if (it.data!!.State == 1) {
                        if (it.data.UserInfo.userID.toString().isNullOrEmpty()){
 //                           Log.d("aziza3", "1")
                        }else{
 //                           Log.d("aziza3", "2")
                        }
                        SharedPreferencesCom.getInstance().setSharedUser_ID(it.data.UserInfo.userID.toString())
                        SharedPreferencesCom.getInstance().setSharedDistributor_ID(it.data.UserInfo.Distributor_ID.toString())
                        //Successfully Login
                        // viewModel.saveInfoRoom(androidId)

//                        viewModel.saveInfoLogin(it.data.item)
//                        Log.d("saveInfoLogin",it.data.item.Name)
//                        viewModel.saveItems(it.data.item.comid.toString(),it.data.item.AndroidID)
                        Log.d("aziza3s2", "State1")
                        Toast.makeText(applicationContext, it.data.State.toString() + it.data.message, Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        // val intent = Intent(this, MainDrawerActivity::class.java)
                        // startActivity(intent)
                        // finish()

                    } else if (it.data!!.State == 2) {
                        //your Request has been pendding
//                        Log.d("aziza3", "2")
                        Log.d("aziza3s2", "State2")
                        Toast.makeText(applicationContext, it.data.State.toString() + it.data.message, Toast.LENGTH_SHORT).show()

                        Toast.makeText(applicationContext, it.data.State.toString() + it.data.message, Toast.LENGTH_SHORT).show()

                        Toast.makeText(applicationContext, it.data.State.toString() + it.data.message, Toast.LENGTH_SHORT).show()

                    } else if (it.data!!.State == 3) {
                        //your Machine has been Registration
//                        Log.d("aziza32", it.data.message)
//                        //  Toast.makeText(applicationContext, it.data.State.toString() + "your Machine has been suspended", Toast.LENGTH_SHORT).show()
//                        Toast.makeText( applicationContext, it.data.State.toString() + it.data.message, Toast.LENGTH_SHORT ).show()
//                        //your Machine has been suspended
//                        binding.textView5.text = it.data.message
//                        //your Machine has been suspended
//                        binding.textView5.setText(it.data.message)
                        Log.d("aziza3s2", "State3")
                        val intent = Intent(this, RegistrationActivity::class.java)
                        startActivity(intent)

                        finish()

                    } else if (it.data!!.State == 4) {
                        //go to registeration first
                        //   Toast.makeText(applicationContext, it.data.State.toString() + "go to registeration first", Toast.LENGTH_SHORT).show()
                     //   Toast.makeText( applicationContext, it.data.State.toString() + it.data.message, Toast.LENGTH_SHORT ).show()
                        //go to registeration first

                        /**
                        val intent = Intent(this, RegisterationActivity::class.java)
                        startActivity(intent)
                         */
                        // go to registration first
                        //  val intent = Intent(this, AccountActivity::class.java)
                        //  startActivity(intent)
                        //  finish()
                    }
                }
                is State.Error ->{Log.d("aziza32", it.messag.toString())}// Log.d("aziza32", it.messag.toString())

            }
        }


    }





    private fun setAsProtectedApp() {
        if ("huawei".equals(Build.MANUFACTURER, ignoreCase = true)
            && !("google".equals(Build.MANUFACTURER, ignoreCase = true))
        ) {
            val alertDialog = AlertDialog.Builder(this)

            alertDialog.setTitle("huawei_headline").setMessage("huawei_text")
                .setPositiveButton("go_to_protected") { dialogInterface, i ->
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                    )
                    startActivity(intent)
                    //  sp.edit().putBoolean("protected", true).commit()
                }.create().show()
        }
    }

    private fun displayLog() {
        var cols = arrayOf(
            CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION,
            CallLog.Calls.DATE
        )
        var rs = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            cols,
            null,
            null,
            "${CallLog.Calls.LAST_MODIFIED} DESC"
        )
        var from = arrayOf(CallLog.Calls.NUMBER, CallLog.Calls.DURATION, CallLog.Calls.TYPE)
        // val adapter= SimpleCursorAdapter(this, R.layout.mylayout,rs,from, intArrayOf(R.id.textView1,R.id.textView2,R.id.textView3),0)
        // val listview=findViewById<ListView>(R.id.listview) as ListView
        // listview.adapter=adapter
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
             //   Log.d("getCurrentLocation","Unable to get current location")
             //   Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //Log.d("getCurrentLocation","onCreate")

        checkLocationPermission()
    }


}