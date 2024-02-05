package com.tbi.supplierplus.business.pojo.opening

data class AddCollection(
    var UserID :String,

    var Amount :String,

    var Cus_id :String,


    var RemainingAmount :String,
    var Payment_Method_Id :Int,
    var CheckNumber :String,
    var Bank :String

)
