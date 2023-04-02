package com.tbi.supplierplus.business.models

data class GetItemsSettlement(
    var itemId: String,
    var itemName: String,
    var purchases: String,
    var balance: String,
    var salesUnit: String,
    var unitPrice: String,
    var salesPriceTotal: String,
    var returnAmount: String,
    var returnSize: String,
)