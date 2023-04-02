package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class ExpensesTypesEnvelope(
    @field:Element(name = "Body", required = false)
    var body: ExpensesTypesBody? = null
)

@Root(name = "Body")
data class ExpensesTypesBody(
    @field:Element(name = "GetFillSpinnerResponse", required = false)
    var response: ExpensesTypesResponse? = null
)

@Root(name = "GetFillSpinnerResponse")
data class ExpensesTypesResponse(
    @field:Element(name = "GetFillSpinnerResult", required = false)
    var result: ExpensesTypesResult? = null
)

@Root(name = "GetFillSpinnerResult")
data class ExpensesTypesResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "Cash", required = false)
    var Cash: String = "",

    @field:Element(name = "Number_of_invoices", required = false)
    var numberOfInvoices: String = "",

    @field:Element(name = "PriceOfProducts", required = false)
    var priceOfProducts: String = "",

    @field:Element(name = "NetAmt", required = false)
    var netAmt: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameExpensesItemList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemID: IdExpensesItemList? = null,

    )

@Root(name = "ItemName")
data class NameExpensesItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

@Root(name = "ItemId")
data class IdExpensesItemList(
    @field:ElementList(entry = "int", required = false, inline = true, type = String::class)
    var ids: List<String>? = null
)