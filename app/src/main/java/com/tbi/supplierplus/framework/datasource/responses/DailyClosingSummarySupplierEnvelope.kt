package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class DailyClosingSummarySupplierEnvelope(
    @field:Element(name = "Body", required = false)
    var body: DailyClosingSummarySupplierBody? = null
)

@Root(name = "Body")
data class DailyClosingSummarySupplierBody(
    @field:Element(name = "GetClosingDay_Summry_SupplierResponse", required = false)
    var response: DailyClosingSummarySupplierResponse? = null
)

@Root(name = "GetClosingDay_Summry_SupplierResponse")
data class DailyClosingSummarySupplierResponse(
    @field:Element(name = "GetClosingDay_Summry_SupplierResult", required = false)
    var result: DailyClosingSummarySupplierResult? = null
)

@Root(name = "GetClosingDay_Summry_SupplierResult")
data class DailyClosingSummarySupplierResult(
    @field:Element(name = "MassId", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userID: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameDailyClosingSummarySupplierList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemId: IdDailyClosingSummarySupplierList? = null,

    )

@Root(name = "ItemName")
data class NameDailyClosingSummarySupplierList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

@Root(name = "ItemId")
data class IdDailyClosingSummarySupplierList(
    @field:ElementList(entry = "int", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

