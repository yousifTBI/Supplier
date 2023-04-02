package com.tbi.supplierplus.business.pojo.addCustomer

data class NewCustomer(

   var CompanyName:String,
   var ContactName:String,
   var Telephone1:String,
   var Telephone2:String,
   var Email:String,
   var Address:String,

   var UserID:String,
   var Distributor_ID:String,
   var Region_ID:String,

   var Longitude:Double,
   var Latitude:Double,
)
