package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class DailyClosingSummaryItemsEnvelope(
    @field:Element(name = "Body", required = false)
    var body: DailyClosingSummaryItemsBody? = null
)

@Root(name = "Body")
data class DailyClosingSummaryItemsBody(
    @field:Element(name = "GetClosingDay_Summry_itemsResponse", required = false)
    var response: DailyClosingSummaryItmesResponse? = null
)

@Root(name = "GetClosingDay_Summry_itemsResponse")
data class DailyClosingSummaryItmesResponse(
    @field:Element(name = "GetClosingDay_Summry_itemsResult", required = false)
    var result: DailyClosingSummaryItemsResult? = null
)

@Root(name = "GetClosingDay_Summry_itemsResult")
data class DailyClosingSummaryItemsResult(
    @field:Element(name = "MassId", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userID: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameDailyClosingSummaryItemList? = null,

    )

@Root(name = "ItemName")
data class NameDailyClosingSummaryItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

