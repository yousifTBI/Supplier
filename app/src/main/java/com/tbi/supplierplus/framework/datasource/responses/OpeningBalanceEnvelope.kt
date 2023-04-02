package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class OpeningBalanceEnvelope(
    @field:Element(name = "Body", required = false)
    var body: OpeningBalanceBody? = null
)

@Root(name = "Body")
data class OpeningBalanceBody(
    @field:Element(name = "GetAllcustomersOpeningBalanceResponse", required = false)
    var response: OpeningBalanceResponse? = null
)

@Root(name = "GetAllcustomersOpeningBalanceResponse")
data class OpeningBalanceResponse(
    @field:Element(name = "GetAllcustomersOpeningBalanceResult", required = false)
    var result: OpeningBalanceResult? = null
)

@Root(name = "GetAllcustomersOpeningBalanceResult")
data class OpeningBalanceResult(
    @field:Element(name = "ItemName", required = false)
    var balances: NameItemList? = null,
)

