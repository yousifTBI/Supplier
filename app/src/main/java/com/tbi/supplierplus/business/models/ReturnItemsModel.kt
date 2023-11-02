package com.tbi.supplierplus.business.models

data class ReturnItemsModel(

    var item_id :Int,
    var Branch_id :Int,
    var user_id :Int,
    var size :Double,
    var Amount :Double,
    var Date :String,
    var BillNo   :String?
    )
