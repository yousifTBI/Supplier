package com.tbi.supplierplus.business.models

data class Customer(

    var customerName: String,
    var customerID: String,
    var region: String = "",
    var deferred: String = "",
    var code: String = "",
    var regionID: String = "",
    var rangeID: String = "",
    var message: String = ""

)
