package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call
data class ExpensesEnvelope(
    @field:Element(name = "Body", required = false)
    var body: ExpensesBody? = null
)

@Root(name = "Body")
data class ExpensesBody(
    @field:Element(name = "GetExpensesResponse", required = false)
    var response: ExpensesResponse? = null
)

@Root(name = "GetExpensesResponse")
data class ExpensesResponse(
    @field:Element(name = "GetExpensesResult", required = false)
    var result: ExpensesResult? = null
)

@Root(name = "GetExpensesResult")
data class ExpensesResult(
    @field:Element(name = "ItemName", required = false)
    var itemName: ExpensesItemName? = null,

)


@Root(name = "ItemName")
data class ExpensesItemName(
    @field:ElementList(entry = "string", required = false, inline = true, type = String::class)
    var names: List<String>? = null
)
