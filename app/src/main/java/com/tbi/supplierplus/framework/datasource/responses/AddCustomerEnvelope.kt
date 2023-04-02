package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class AddCustomerEnvelope(
    @field:Element(name = "Body", required = false)
    var body: AddCustomerBody? = null
)

@Root(name = "Body")
data class AddCustomerBody(
    @field:Element(name = "AddCustomerResponse", required = false)
    var response: AddCustomerResponse? = null
)

@Root(name = "AddCustomerResponse")
data class AddCustomerResponse(
    @field:Element(name = "AddCustomerResult", required = false)
    var result: AddCustomerResult? = null
)

@Root(name = "AddCustomerResult")
data class AddCustomerResult(

    @field:Element(name = "Message", required = false)
    var Message: String = "",

    @field:Element(name = "ItemName", required = false)
    var customers: NameItemList? = null,
)

