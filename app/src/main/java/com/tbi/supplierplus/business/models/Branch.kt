package com.tbi.supplierplus.business.models

import ir.mirrajabi.searchdialog.core.Searchable

data class Branch(

    var UserId: Int,
    var billNumber: Boolean,
    var salesStatus: String,
    var SalesName: String,
    var comid: Int,
    var Unpaid_deferred: Double,
    var item: String,
    var branchName: String,
    var Region: String,
    var Range: String,
    var Region_ID: String,
    var Range_ID: String,
    var Branch_ID: Int

) : Searchable {
    override fun getTitle(): String {
        return item!!
    }

    fun ID(): Int {
        return Branch_ID!!
    }
    fun Unpaid_deferred(): Double {
        return Unpaid_deferred!!
    }

}
