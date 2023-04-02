package com.tbi.supplierplus.framework.ui.sales.customer_profile

import com.tbi.supplierplus.business.pojo.AllCustomers
import ir.mirrajabi.searchdialog.core.Searchable

class SearchModel(private val list: ArrayList<AllCustomers>):Searchable {
    override fun getTitle(): String? {
        TODO("Not yet implemented")
        return list.toString()

    }


}