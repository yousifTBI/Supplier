package com.tbi.supplierplus.business.pojo.reports

data class SelesReport(

   val TotalPil              :Int,
   val TotalPillBeforDiscount :Double,
   val TotalPilDiscount       :Double,
   val TotalPilAfterDiscount  :Double,
   val TotalItemsQount        :Double,
   val TotalItemsDiscount     :Double,
   val TotalPilCach           :Double,
   val TotalPilAgel           :Double,
   val Msrofat                :Double,
   val TotalPilNet            :Double,
   val ReturnAmount           :Double,
   val Collection             :Double,

)
