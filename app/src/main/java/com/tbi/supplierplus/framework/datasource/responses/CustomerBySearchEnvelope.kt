package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class CustomerBySearchEnvelope(
    @field:Element(name = "Body", required = false)
    var body: CustomerBySearchBody? = null
)

@Root(name = "Body")
data class CustomerBySearchBody(
    @field:Element(name = "GetCusBySearchResponse", required = false)
    var response: CustomerBySearchResponse? = null
)

@Root(name = "GetCusBySearchResponse")
data class CustomerBySearchResponse(
    @field:Element(name = "GetCusBySearchResult", required = false)
    var result: CustomerBySearchResult? = null
)

@Root(name = "GetCusBySearchResult")
data class CustomerBySearchResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameCustomerList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemID: IDCustomerList? = null,
)

@Root(name = "ItemName")
data class NameCustomerList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null
)

@Root(name = "ItemId")
data class IDCustomerList(
    @field:ElementList(entry = "int", required = false, inline = true, type = String::class)
    var ids: List<String>? = null
)