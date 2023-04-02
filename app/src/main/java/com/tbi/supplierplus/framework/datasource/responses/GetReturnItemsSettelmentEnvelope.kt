package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class GetReturnItemsSettelmentEnvelope(
    @field:Element(name = "Body", required = false)
    var body: GetReturnItemsSettelmentBody? = null
)

@Root(name = "Body")
data class GetReturnItemsSettelmentBody(
    @field:Element(name = "Get_Return_items_settelmentResponse", required = false)
    var response: GetReturnItemsSettelmentResponse? = null
)

@Root(name = "Get_Return_items_settelmentResponse")
data class GetReturnItemsSettelmentResponse(
    @field:Element(name = "Get_Return_items_settelmentResult", required = false)
    var result: GetReturnItemsSettelmentResult? = null
)

@Root(name = "Get_Return_items_settelmentResult")
data class GetReturnItemsSettelmentResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userId: String = "",


    @field:Element(name = "ItemName", required = false)
    var itemName: GetReturnItmesSettelmentNameItemList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemId: GetReturnItmesSettelmentIdItemList? = null,
    )

@Root(name = "ItemName")
data class GetReturnItmesSettelmentNameItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

@Root(name = "ItemId")
data class GetReturnItmesSettelmentIdItemList(
    @field:ElementList(entry = "int", required = false, inline = true, type = String::class)
    var ids: List<String>? = null,
)
