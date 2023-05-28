package com.tbi.supplierplus.framework.ui.sales.addCompany

import ir.mirrajabi.searchdialog.core.Searchable

data class Data(
    var Id    : Int    ,
    var Value : String

)
    : Searchable {

    override fun getTitle(): String {
        return Value!!
    }

    fun ID(): Int {
        return Id!!
    }
}
