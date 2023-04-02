package com.tbi.supplierplus.business.pojo.addCustomer

import ir.mirrajabi.searchdialog.core.Searchable

data class Regions(

   var RecordID:Int,
   var Region:String

): Searchable {
   override fun getTitle(): String {
      return Region
   }
   fun RecordIDs(): String {
      return RecordID.toString()
   }

}
