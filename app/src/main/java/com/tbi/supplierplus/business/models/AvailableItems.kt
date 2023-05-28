package com.tbi.supplierplus.business.models

data class AvailableItems(

    var ItemID: Int,
    var Supplier_Id: Int,
    var ItemName: String,
    var Barcode: String,
    var availableCount: Double,
    var Selling_Price: Double,
    var Supply_Price: Double,
)
