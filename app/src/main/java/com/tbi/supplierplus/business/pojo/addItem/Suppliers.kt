package com.tbi.supplierplus.business.pojo.addItem

import ir.mirrajabi.searchdialog.core.Searchable

data class Supplier(
    val CompanyName:String,
    val Supplier_ID:Int,

): Searchable{
    override fun getTitle(): String {
        return CompanyName
    }
    fun getItemGroupID():String{
        return  Supplier_ID.toString()
    }
}
