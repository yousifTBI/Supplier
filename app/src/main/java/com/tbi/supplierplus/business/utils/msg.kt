package com.tbi.supplierplus.business.utils

import android.R
import android.app.Activity
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(activity : Activity, msg: String) {
    Snackbar.make(
        activity.findViewById(R.id.content),
        msg,
        Snackbar.LENGTH_SHORT // How long to display the message.
    ).show()
}