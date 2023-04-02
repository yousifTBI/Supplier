package com.tbi.supplierplus.business.pojo

data class Tasks<T>(
    var State :Int,
    var data :List<T>,
    var Message :String,
    val Item : T?
)
