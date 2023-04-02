package com.tbi.supplierplus.business.models

data class PurchaseItem(
    var itemName: String,
    var itemID: String,
    var price: String,
    var total: String,
    var quantity: String,
    var message: String = ""
)
