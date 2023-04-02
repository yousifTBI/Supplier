package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class PurchaseEnvelope(
    @field:Element(name = "Body", required = false)
    var body: PurchaseBody? = null
)

@Root(name = "Body")
data class PurchaseBody(
    @field:Element(name = "GetPurchasesResponse", required = false)
    var response: PurchaseResponse? = null
)

@Root(name = "GetPurchasesResponse")
data class PurchaseResponse(
    @field:Element(name = "GetPurchasesResult", required = false)
    var result: PurchaseResult? = null
)

@Root(name = "GetPurchasesResult")
data class PurchaseResult(
    @field:Element(name = "ItemName", required = false)
    var itemName: NameCustomerList? = null,
)


