package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import retrofit2.Call

data class ItemInfoEnvelope(
    @field:Element(name = "Body", required = false)
    var body: ItemInfoBody? = null
)

@Root(name = "Body")
data class ItemInfoBody(
    @field:Element(name = "GetItemByBarcodeResponse", required = false)
    var response: ItemInfoResponse? = null
)

@Root(name = "GetItemByBarcodeResponse")
data class ItemInfoResponse(
    @field:Element(name = "GetItemByBarcodeResult", required = false)
    var result: ItemInfoResult? = null
)

@Root(name = "GetItemByBarcodeResult")
data class ItemInfoResult(
    @field:Element(name = "Itemsize", required = false)
    var itemSize: String = "",
    @field:Element(name = "Item_Selling_Price", required = false)
    var itemSellingPrice: String = "",
    @field:Element(name = "Item_Supply_Price", required = false)
    var itemSupplyPrice: String = "",
    @field:Element(name = "Price_Id", required = false)
    var priceID: String = "",
    @field:Element(name = "Supplier_Id_1", required = false)
    var supplierID: String = "",
    @field:Element(name = "ItemGroup_Id_1", required = false)
    var itemGroupID: String = "",
    @field:Element(name = "Item_Id_1", required = false)
    var itemID: String = "",
    @field:Element(name = "ItemName1", required = false)
    var itemName: String = "",
    @field:Element(name = "SupplierName", required = false)
    var SupplierName: String = "",
    @field:Element(name = "CategoryName", required = false)
    var categoryName: String = "",
)