package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class ItemsSummaryReportEnvelope(
    @field:Element(name = "Body", required = false)
    var body: ItemsSummaryReportBody? = null
)

@Root(name = "Body")
data class ItemsSummaryReportBody(
    @field:Element(name = "GetItemsSummryReportResponse", required = false)
    var response: ItemsSummaryReportResponse? = null
)

@Root(name = "GetItemsSummryReportResponse")
data class ItemsSummaryReportResponse(
    @field:Element(name = "GetItemsSummryReportResult", required = false)
    var result: ItemsSummaryReportResult? = null
)

@Root(name = "GetItemsSummryReportResult")
data class ItemsSummaryReportResult(

    @field:Element(name = "ItemName", required = false)
    var itemName: NameItemList? = null,

    )
