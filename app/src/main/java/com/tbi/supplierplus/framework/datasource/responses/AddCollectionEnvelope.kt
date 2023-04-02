package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call

data class AddCollectionEnvelope(
    @field:Element(name = "Body", required = false)
    var body: AddCollectionBody? = null
)

@Root(name = "Body")
data class AddCollectionBody(
    @field:Element(name = "AddCollectionResponse", required = false)
    var response: AddCollectionResponse? = null
)

@Root(name = "AddCollectionResponse")
data class AddCollectionResponse(
    @field:Element(name = "AddCollectionResult", required = false)
    var result: AddCollectionResult? = null
)

@Root(name = "AddCollectionResult")
data class AddCollectionResult(
    @field:Element(name = "Message", required = false)
    var message: String = "",
)