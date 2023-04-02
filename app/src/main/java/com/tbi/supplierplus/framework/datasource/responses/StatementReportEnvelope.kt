package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class StatementReportEnvelope(
    @field:Element(name = "Body", required = false)
    var body: StatementReportBody? = null
)

@Root(name = "Body")
data class StatementReportBody(
    @field:Element(name = "Get_Customer_StatementResponse", required = false)
    var response: StatementReportResponse? = null
)

@Root(name = "Get_Customer_StatementResponse")
data class StatementReportResponse(
    @field:Element(name = "Get_Customer_StatementResult", required = false)
    var result: StatementReportResult? = null
)

@Root(name = "Get_Customer_StatementResult")
data class StatementReportResult(

    @field:Element(name = "ItemName", required = false)
    var itemName: NameItemList? = null,

    )
