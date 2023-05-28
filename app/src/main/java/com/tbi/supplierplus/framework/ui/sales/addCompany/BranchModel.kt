package com.tbi.supplierplus.framework.ui.sales.addCompany

data class BranchModel<t>(
   var Data    : ArrayList<t> = arrayListOf(),
   var State   : Int          ,
   var Message : String
)
