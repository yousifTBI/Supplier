package com.tbi.supplierplus.business.models

data class ItemsBillsModel(
    var billNo: Int,
    var sales: Double,
    var Return: Double,
    var BillDate: String,
    var _BillDate: String,
    var BranchName: String,
)
