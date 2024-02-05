package com.tbi.supplierplus.business.models

import ir.mirrajabi.searchdialog.core.Searchable

data class ItemDetailsModel(
   var Record_ID    : Int  ,
   var ItemName    : String,
   var Description : String,
   var Editor      : String,
   var Date        : String,
   var ItemGroupID : Int   ,
   var Status      : Int   ,
   var size        : Double   ,
   var Supplier_ID  : Int  ,
   var GuId        : String,
   var Barcode     : String
) : Searchable {
   override fun getTitle(): String {
      return ItemName!!
   }
   fun RecordID(): Int {
      return Record_ID!!
   }
}
