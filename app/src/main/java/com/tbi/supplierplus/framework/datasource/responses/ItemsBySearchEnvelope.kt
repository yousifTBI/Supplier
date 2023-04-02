package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class ItemsBySearchEnvelope(
    @field:Element(name = "Body", required = false)
    var body: ItemsBySearchBody? = null
)

@Root(name = "Body")
data class ItemsBySearchBody(
    @field:Element(name = "GetItemBySearchResponse", required = false)
    var response: ItemsBySearchResponse? = null
)

@Root(name = "GetItemBySearchResponse")
data class ItemsBySearchResponse(
    @field:Element(name = "GetItemBySearchResult", required = false)
    var result: ItemsBySearchResult? = null
)

@Root(name = "GetItemBySearchResult")
data class ItemsBySearchResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameItemList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemID: IDItemList? = null,

    )

@Root(name = "ItemName")
data class NameItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

@Root(name = "ItemId")
data class IDItemList(
    @field:ElementList(entry = "int", required = false, inline = true, type = String::class)
    var ids: List<String>? = null
)