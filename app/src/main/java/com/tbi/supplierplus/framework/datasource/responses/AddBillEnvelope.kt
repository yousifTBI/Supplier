package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

data class AddBillEnvelope(
    @field:Element(name = "Body", required = false)
    var body: AddBillBody? = null
)

@Root(name = "Body")
data class AddBillBody(
    @field:Element(name = "setpillResponse", required = false)
    var response: AddBillResponse? = null
)

@Root(name = "setpillResponse")
data class AddBillResponse(
    @field:Element(name = "setpillResult", required = false)
    var result: AddBillResult? = null
)

@Root(name = "setpillResult")
data class AddBillResult(
    @field:Element(name = "Message", required = false)
    var message: String = "",
    @field:Element(name = "BillNo", required = false)
    var billNumber: String = "",

)


