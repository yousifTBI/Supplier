package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


data class AddItemsReturnEnvelope(
    @field:Element(name = "Body", required = false)
    var body: AddItemsReturnBody? = null
)

@Root(name = "Body")
data class AddItemsReturnBody(
    @field:Element(name = "AddItemsReturnResponse", required = false)
    var response: AddItemsReturnResponse? = null
)

@Root(name = "AddItemsReturnResponse")
data class AddItemsReturnResponse(
    @field:Element(name = "AddItemsReturnResult", required = false)
    var result: AddItemsReturnResult? = null
)

@Root(name = "AddItemsReturnResult")
data class AddItemsReturnResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userId: String = "",

    @field:Element(name = "Message", required = false)
    var message: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: AddItemsReturnNameItemList? = null,

    )

@Root(name = "ItemName")
data class AddItemsReturnNameItemList (
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

