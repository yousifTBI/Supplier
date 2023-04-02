package com.tbi.supplierplus.business.models

data class Item(

    var name: String,
    var id: String,
    var size: String = "",
    var sellingPrice: String = "",
    var supplyPrice: String = "",
    var priceID: String = "",
    var supplierID: String = "",
    var groupID: String = "",
    var supplierName: String = "",
    var categoryName: String = "",
    var discount: String = "",
    var quantity: String = "",
    var total : String = ""

    ) {
    fun totalSale(): Float {
        return if (sellingPrice.isEmpty() || quantity.isEmpty())
            0.0f
        else quantity.toFloat() * sellingPrice.toFloat().minus(discount.toFloat())
    }


    fun totalReturn(): Float {
        return if (sellingPrice.isEmpty() || size.isEmpty())
            0.0f
        else size.toFloat() * sellingPrice.toFloat()
    }
}
