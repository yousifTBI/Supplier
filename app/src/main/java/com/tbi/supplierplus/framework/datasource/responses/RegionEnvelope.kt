package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class RegionEnvelope(
    @field:Element(name = "Body", required = false)
    var body: RegionBody? = null
)

@Root(name = "Body")
data class RegionBody(
    @field:Element(name = "Get_RegionResponse", required = false)
    var response: RegionResponse? = null
)

@Root(name = "Get_RegionResponse")
data class RegionResponse(
    @field:Element(name = "Get_RegionResult", required = false)
    var result: RegionResult? = null
)

@Root(name = "Get_RegionResult")
data class RegionResult(
    @field:Element(name = "ItemName", required = false)
    var regions: NameItemList? = null,
)

