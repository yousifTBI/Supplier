package com.tbi.supplierplus.business.pojo

import ir.mirrajabi.searchdialog.core.Searchable

data class AllCustomers(
  val item: String,
  val Unpaid_deferred: Double,
  val Customer_ID: Int,

  val CompanyName: String,
  val Region: String,
  val Range: String,
  val Region_ID: Int,
  val Range_ID: Int,


): Searchable {
  override fun getTitle(): String {
   return item
  }
  fun getTitlea(): String {
    return Range
  }
}