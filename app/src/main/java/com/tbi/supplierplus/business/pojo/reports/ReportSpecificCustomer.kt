package com.tbi.supplierplus.business.pojo.reports

data class ReportSpecificCustomer(
    var NumOfBranches :Int,
    var TotalPil      :Int,
    var TotalPillBeforDiscount :Double,
    var TotalPilDiscount       :Double,
    var TotalPilAfterDiscount  :Double,
    var TotalItemsQount        :Double,
    var TotalItemsDiscount     :Double,
    var TotalPilCach           :Double,
    var TotalPilAgel           :Double,
    var TotalPilNet            :Double,
    var ReturnAmount           :Double,
    var Collection             :Double,

)
