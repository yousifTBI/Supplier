package com.tbi.supplierplus.business.models

data class LocationDetails(
    var latitude: Double,
    var longitude: Double,
    var address: String,
    var placeID: String,
    var compoundCode: String,
    var globalCode: String
)