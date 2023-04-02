package com.tbi.supplierplus.business.models

data class CustomerStatement(
    var billID: String,
    var oldRemainingAmount: String,
    var remainingAmount: String,
    var amountRequired: String,
    var collectionAmount: String,
    var salesAmount: String,
    var deferred: String,
    var cash: String,
    var date: String,
    var type: String,
) {
    fun getTypeString(): String = if (type == "ف") "فاتورة" else if (type == "ت") "تحصيل" else "غير معرف"
}


