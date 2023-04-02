package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class GetFillSpinnerReturnItemsEnvelope(
    @field:Element(name = "Body", required = false)
    var body: GetFillSpinnerReturnItemsBody? = null
)

@Root(name = "Body")
data class GetFillSpinnerReturnItemsBody(
    @field:Element(name = "GetFillSpinnerResponse", required = false)
    var response: GetFillSpinnerReturnItemsResponse? = null
)

@Root(name = "GetFillSpinnerResponse")
data class GetFillSpinnerReturnItemsResponse(
    @field:Element(name = "GetFillSpinnerResult", required = false)
    var result: GetFillSpinnerReturnItemsResult? = null
)

@Root(name = "GetFillSpinnerResult")
data class GetFillSpinnerReturnItemsResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "Cash", required = false)
    var cash: String = "",

    @field:Element(name = "Number_of_invoices", required = false)
    var Number_of_invoices: String = "",

    @field:Element(name = "PriceOfProducts", required = false)
    var PriceOfProducts: String = "",

    @field:Element(name = "NetAmt", required = false)
    var NetAmt: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",


    @field:Element(name = "ItemName", required = false)
    var itemName: GetFillSpinnerReturnItemsNameItemList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemId: GetFillSpinnerReturnItemsIdItemList? = null,
    )

@Root(name = "ItemName")
data class GetFillSpinnerReturnItemsNameItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

@Root(name = "ItemId")
data class GetFillSpinnerReturnItemsIdItemList(
    @field:ElementList(entry = "int", required = false, inline = true, type = String::class)
    var ids: List<String>? = null,
)
