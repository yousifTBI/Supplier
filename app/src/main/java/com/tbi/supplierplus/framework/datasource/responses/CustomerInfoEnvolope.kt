package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import retrofit2.Call

data class CustomerInfoEnvelope(
    @field:Element(name = "Body", required = false)
    var body: CustomerInfoBody? = null
)

@Root(name = "Body")
data class CustomerInfoBody(
    @field:Element(name = "GetCusResponse", required = false)
    var response: CustomerInfoResponse? = null
)

@Root(name = "GetCusResponse")
data class CustomerInfoResponse(
    @field:Element(name = "GetCusResult", required = false)
    var result: CustomerInfoResult? = null
)

@Root(name = "GetCusResul")
data class CustomerInfoResult(
    @field:Element(name = "Cus_Name", required = false)
    var customerName: String = "",

    @field:Element(name = "Region", required = false)
    var region: String = "",

    @field:Element(name = "Message", required = false)
    var message: String = "",

    @field:Element(name = "Deferred", required = false)
    var deferred: String = "",

    @field:Element(name = "Cus_Code", required = false)
    var customerCode: String = "",

    @field:Element(name = "Region_ID_1", required = false)
    var regionID: String = "",

    @field:Element(name = "Range_ID_1", required = false)
    var rangeID: String = "",
)
