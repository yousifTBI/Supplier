package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


data class GetItemsSettlementEnvelope(
    @field:Element(name = "Body", required = false)
    var body: GetItemsSettlementBody? = null
)

@Root(name = "Body")
data class GetItemsSettlementBody(
    @field:Element(name = "Get_items_settelmentResponse", required = false)
    var response: GetItemsSettlementResponse? = null
)

@Root(name = "Get_items_settelmentResponse")
data class GetItemsSettlementResponse(
    @field:Element(name = "Get_items_settelmentResult", required = false)
    var result: GetItemsSettlementResult? = null
)

@Root(name = "Get_items_settelmentResult")
data class GetItemsSettlementResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userId: String = "",


    @field:Element(name = "ItemName", required = false)
    var itemName: GetItemsSettlementNameItemList? = null,

    )

@Root(name = "ItemName")
data class GetItemsSettlementNameItemList (
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

