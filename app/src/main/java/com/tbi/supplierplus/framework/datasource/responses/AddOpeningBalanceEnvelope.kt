package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class AddOpeningBalanceEnvelope(
    @field:Element(name = "Body", required = false)
    var body: AddOpeningBalanceBody? = null
)

@Root(name = "Body")
data class AddOpeningBalanceBody(
    @field:Element(name = "AddOpeningBalanceResponse", required = false)
    var response: AddOpeningBalanceResponse? = null
)

@Root(name = "AddOpeningBalanceResponse")
data class AddOpeningBalanceResponse(
    @field:Element(name = "AddOpeningBalanceResult", required = false)
    var result: AddOpeningBalanceResult? = null
)

@Root(name = "AddOpeningBalanceResult")
data class AddOpeningBalanceResult(

    @field:Element(name = "Message", required = false)
    var Message: String = "",
)

