package com.tbi.supplierplus.business.pojo.billModels

import com.tbi.supplierplus.business.pojo.Tasks
import kotlin.contracts.Returns

data class Billdetails(

    var  CusID :       String,
    var  PillDiscount :String,
    var  Editor :      String,
    var  Sales_ID :    String,
    var  TotalAmountBeforDiscount :String,
    var  TotalAmountAfterDiscount :String,
    var  Cash :    String,
    var  Deferred :String,
    var  collection :  String,
    var  ReturnAmount :String,

    var pil_Details:List<SaleingBill> ,
    var PillReturn :List<ReturnBill>     ,
)
