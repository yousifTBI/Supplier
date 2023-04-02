package com.tbi.supplierplus.business.pojo.addItem

import ir.mirrajabi.searchdialog.core.Searchable

data class TypeOfcategory(

    val CategoryName : String,
    val ItemGroup_ID : Int
): Searchable{
    override fun getTitle(): String {
        return CategoryName.toString()
    }
    fun getItemGroupID():String{
        return ItemGroup_ID.toString()
    }

}
