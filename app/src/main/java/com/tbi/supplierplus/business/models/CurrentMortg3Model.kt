package com.tbi.supplierplus.business.models

data class CurrentMortg3Model(

    var Record_ID: Int,
    var Branch_id: Int,
    var user_id: Int,
    var size: Double,
    var Amount: Double,
    var Item_Count: Double,
    var BillNo: Int,
    var Itemname: String
)
