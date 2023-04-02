package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

data class AddPurchaseEnvelope(
    @field:Element(name = "Body", required = false)
    var body: AddPurchaseBody? = null
)

@Root(name = "Body")
data class AddPurchaseBody(
    @field:Element(name = "PurchasesResponse", required = false)
    var response: AddPurchaseResponse? = null
)

@Root(name = "PurchasesResponse")
data class AddPurchaseResponse(
    @field:Element(name = "PurchasesResult", required = false)
    var result: AddPurchaseResult? = null
)

@Root(name = "PurchasesResult")
data class AddPurchaseResult(
    @field:Element(name = "Message", required = false)
    var message: String = "",
    @field:Element(name = "ItemName", required = false)
    var itemName: NameCustomerList? = null,
)


