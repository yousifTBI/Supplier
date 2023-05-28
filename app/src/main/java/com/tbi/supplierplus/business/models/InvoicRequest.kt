package com.tbi.supplierplus.business.models

data class InvoicRequest(


    var Item_ID: Int,
    var Item_Count: Double,
    var User_ID: Int,
    var Supplier_ID: Int,
    var Storekeeper_i: Int,
    var sales_id: Int,
    var type: Byte,
)
