package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class DailyClosingPurchasesEnvelope(
    @field:Element(name = "Body", required = false)
    var body: DailyClosingPurchasesBody? = null
)

@Root(name = "Body")
data class DailyClosingPurchasesBody(
    @field:Element(name = "GetPurchasesResponse", required = false)
    var response: DailyClosingPurchasesResponse? = null
)

@Root(name = "GetPurchasesResponse")
data class DailyClosingPurchasesResponse(
    @field:Element(name = "GetPurchasesResult", required = false)
    var result: DailyClosingPurchasesResult? = null
)

@Root(name = "GetPurchasesResult")
data class DailyClosingPurchasesResult(
    @field:Element(name = "MassId", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userID: String = "",

    @field:Element(name = "Message", required = false)
    var message: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameDailyClosingPurchasesList? = null,

    )

@Root(name = "ItemName")
data class NameDailyClosingPurchasesList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

