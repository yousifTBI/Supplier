package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import retrofit2.Call

data class SalesSummaryEnvelope(
    @field:Element(name = "Body", required = false)
    var body: SalesSummaryBody? = null
)

@Root(name = "Body")
data class SalesSummaryBody(
    @field:Element(name = "GetselesSummryResponse", required = false)
    var response: SalesSummaryResponse? = null
)

@Root(name = "GetselesSummryResponse")
data class SalesSummaryResponse(
    @field:Element(name = "GetselesSummryResult", required = false)
    var result: SalesSummaryResult? = null
)

@Root(name = "GetselesSummryResult")
data class SalesSummaryResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "TotalPil", required = false)
    var totalBill: String = "",

    @field:Element(name = "TotalPillBeforDiscount", required = false)
    var totalBillBeforeDiscount: String = "",

    @field:Element(name = "TotalPilDiscount", required = false)
    var totalBillDiscount: String = "",

    @field:Element(name = "TotalPilAfterDiscount", required = false)
    var totalBillAfterDiscount: String = "",

    @field:Element(name = "TotalItemsQount", required = false)
    var totalItemsCount: String = "",

    @field:Element(name = "TotalItemsDiscount", required = false)
    var totalItemsDiscount: String = "",

    @field:Element(name = "TotalPilCach", required = false)
    var totalBillCash: String = "",

    @field:Element(name = "TotalPilAgel", required = false)
    var totalBillDeferred: String = "",

    @field:Element(name = "Msrofat", required = false)
    var expenses: String = "",

    @field:Element(name = "TotalPilNet", required = false)
    var totalBillNet: String = "",

    @field:Element(name = "ReturnAmount", required = false)
    var returnAmount: String = "",

    @field:Element(name = "Collection", required = false)
    var collection: String = "",

    @field:Element(name = "Message", required = false)
    var message: String = "",


    )
