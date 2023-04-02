package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


data class GetItemsVsBillEnvelope(
    @field:Element(name = "Body", required = false)
    var body: GetItemsVsBillBody? = null
)

@Root(name = "Body")
data class GetItemsVsBillBody(
    @field:Element(name = "Get_itemsVS_BillResponse", required = false)
    var response: GetItemsVsBillResponse? = null
)

@Root(name = "Get_itemsVS_BillResponse")
data class GetItemsVsBillResponse(
    @field:Element(name = "Get_itemsVS_BillResult", required = false)
    var result: GetItemsVsBillResult? = null
)

@Root(name = "Get_itemsVS_BillResult")
data class GetItemsVsBillResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userId: String = "",

    @field:Element(name = "Message", required = false)
    var message: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: GetItemsVsBillNameItemList? = null,

    )

@Root(name = "ItemName")
data class GetItemsVsBillNameItemList (
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

