package com.tbi.supplierplus.business.pojo.reports

data class Invoices(

   val BillNo          :Int ,
   var Old_RemainingAmount  :Double,
   var RemainingAmount      :Double,
   var Amount_Required      :Double,
   var CollectionAmount     :Double,
   var SalesAmount          :Double,
   var Deferred             :Double,
   var cash                 :Double,
   var Add_date  :String,
   var Typett    :String,

)
