package com.tbi.supplierplus.business.models

data class ItemTask<t>(
    var data          :List<t>,
    var Details      : String        ,
    var Item         : String        ,
    var State        : Int           ,
    var Message      : String        ,
    var Message2     : String        ,
    var ErrorMessage : String

)
