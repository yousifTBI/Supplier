package com.tbi.supplierplus.business.pojo.addCustomer

import ir.mirrajabi.searchdialog.core.Searchable

data class Ranges(

   var Range:String,
 var RecordID:Int

): Searchable {
    override fun getTitle(): String {
        return Range
    }
    fun RecordIDs(): String {
        return RecordID.toString()
    }
}
