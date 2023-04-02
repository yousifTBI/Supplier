package com.tbi.supplierplus.business.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext


@SuppressLint("HardwareIds")
fun getAndroidID(@ApplicationContext context : Context): String = Settings.Secure.getString(
    context.contentResolver,
    Settings.Secure.ANDROID_ID
)


@RequiresApi(Build.VERSION_CODES.O)
fun getSerialID(): String = Build.SERIAL
