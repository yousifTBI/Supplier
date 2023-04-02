package com.tbi.supplierplus.business.utils

import com.google.gson.Gson
import com.tbi.supplierplus.business.models.User


fun <A> String.fromJson(type: Class<A>): A =
    Gson().fromJson(this, type)

fun <A> A.toJson(): String = Gson().toJson(this)