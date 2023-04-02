package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class DailyClosingExpensesEnvelope(
    @field:Element(name = "Body", required = false)
    var body: DailyClosingExpensesBody? = null
)

@Root(name = "Body")
data class DailyClosingExpensesBody(
    @field:Element(name = "GetClosingDay_Summry_ExpensesResponse", required = false)
    var response: DailyClosingExpensesResponse? = null
)

@Root(name = "GetClosingDay_Summry_ExpensesResponse")
data class DailyClosingExpensesResponse(
    @field:Element(name = "GetClosingDay_Summry_ExpensesResult", required = false)
    var result: DailyClosingExpensesResult? = null
)

@Root(name = "GetClosingDay_Summry_ExpensesResult")
data class DailyClosingExpensesResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "Message", required = false)
    var Message: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameDailyClosingExpensesItemList? = null,

    )

@Root(name = "ItemName")
data class NameDailyClosingExpensesItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

