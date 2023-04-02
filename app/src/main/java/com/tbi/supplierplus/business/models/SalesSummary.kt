package com.tbi.supplierplus.business.models

data class SalesSummary(

    var totalBill: String,

    var totalBillBeforeDiscount: String,

    var totalBillDiscount: String,

    var totalBillAfterDiscount: String,

    var totalItemsCount: String,

    var totalItemsDiscount: String,

    var totalBillCash: String,

    var totalBillDeferred: String,

    var expenses: String,

    var totalBillNet: String,

    var returnAmount: String,

    var collection: String,

    var message: String
)

data class SalesSummaryTitles(
    var title: String,

    var value: String,
)
