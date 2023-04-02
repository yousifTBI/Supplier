package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


data class AddExpensesEnvelope(
    @field:Element(name = "Body", required = false)
    var body: addExpensesBody? = null
)

@Root(name = "Body")
data class addExpensesBody(
    @field:Element(name = "AddExpensesResponse", required = false)
    var response: AddExpensesResponse? = null
)

@Root(name = "AddExpensesResponse")
data class AddExpensesResponse(
    @field:Element(name = "AddExpensesResult", required = false)
    var result: addExpensesResponseResult? = null
)

@Root(name = "AddExpensesResult")
data class addExpensesResponseResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "Message", required = false)
    var Message: String = "",

    @field:Element(name = "ItemName", required = false)
    var itemName: NameAddExpensesResponseItemList? = null,


    )

@Root(name = "ItemName")
data class NameAddExpensesResponseItemList(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null,
)

