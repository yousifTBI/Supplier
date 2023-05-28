package com.tbi.supplierplus.framework.ui.sales.add_customer

data class CustomerTask<T>(
    var State :Int,
    var data :List<T>,
    var Message :String,
    var reson :String,

    )
