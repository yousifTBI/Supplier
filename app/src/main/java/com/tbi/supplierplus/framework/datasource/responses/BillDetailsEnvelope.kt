package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class BillDetailsEnvelope(
    @field:Element(name = "Body", required = false)
    var body: BillDetailsBody? = null
)

@Root(name = "Body")
data class BillDetailsBody(
    @field:Element(name = "Get_Pill_DetailsResponse", required = false)
    var response: BillDetailsResponse? = null
)

@Root(name = "Get_Pill_DetailsResponse")
data class BillDetailsResponse(
    @field:Element(name = "Get_Pill_DetailsResult", required = false)
    var result: BillDetailsResult? = null
)

@Root(name = "Get_Pill_DetailsResult")
data class BillDetailsResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "BillDetels", required = false)
    var bill: NameItemList? = null,

    @field:Element(name = "BillReturn", required = false)
    var returns: NameItemList? = null,

    @field:Element(name = "ItemId", required = false)
    var itemID: IDItemList? = null,

    )

