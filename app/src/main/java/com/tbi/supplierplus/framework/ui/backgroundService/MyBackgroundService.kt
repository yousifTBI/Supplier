package com.tbi.supplierplus.framework.ui.backgroundService

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import com.tbi.supplierplus.framework.getLocation.MyLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BackgroundService : Service() {

    private val executorService = Executors.newSingleThreadScheduledExecutor()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val task = Runnable {
            var loc: Location? = null
            val locationResult: MyLocation.LocationResult = object : MyLocation.LocationResult() {
                override fun gotLocation(location: Location) {
                    loc = location
                    //   System.out.println("allah: " + loc!!.latitude)
                    //   System.out.println("allah: " + loc!!.longitude)
                    //     Log.d("allahAkbr",  loc!!.latitude.toString())
                    //  Log.d("allahAkbr",  loc!!.longitude.toString())
                }
            }
            Log.d("CoroutineScope","CoroutineScope1")
            val myLocation = MyLocation()
            myLocation.getLocation(this, locationResult)
            myMethod( loc!!.longitude, loc!!.latitude)
        }

        // Schedule the task to run every 1 minute
        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES)
        Log.d("CoroutineScope","CoroutineScope2")
        // Return START_STICKY to indicate that the service should be restarted if it gets killed
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the executor service when the service is destroyed
        executorService.shutdownNow()
    }

    private fun myMethod(longitude: Double, latitude: Double ) {
        // Your code logic here

        CoroutineScope(Dispatchers.IO).launch  {
//            availableItemsViewModel.AddLocationPointToUser( longitude, latitude).collect {
//
//                when (it) {
//                    is State.Loading ->{
//
//                    }
//                    is State.Success -> {
//                        Log.d("billNumToCreateQR","Success")
//
//
//                    }
//                    is State.Error -> {
//                        Log.d("billNumToCreateQR","Error")
//                    }
//                }
//            }

        }
    }
}