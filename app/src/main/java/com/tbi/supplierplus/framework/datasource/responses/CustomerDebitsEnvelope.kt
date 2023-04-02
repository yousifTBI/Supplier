package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class CustomerDebitsEnvelope(
    @field:Element(name = "Body", required = false)
    var body: CustomerDebitsBody? = null
)

@Root(name = "Body")
data class CustomerDebitsBody(
    @field:Element(name = "Get_AllDebtsResponse", required = false)
    var response: CustomerDebitsResponse? = null
)

@Root(name = "Get_AllDebtsResponse")
data class CustomerDebitsResponse(
    @field:Element(name = "Get_AllDebtsResult", required = false)
    var result: CustomerDebitsResult? = null
)

@Root(name = "Get_AllDebtsResult")
data class CustomerDebitsResult(
    @field:Element(name = "ItemName", required = false)
    var debits: NameItemList? = null,
)

