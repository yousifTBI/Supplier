package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


data class SetReturnItemsSettlementEnvelope(
    @field:Element(name = "Body", required = false)
    var body: SetReturnItemsSettlementBody? = null
)

@Root(name = "Body")
data class SetReturnItemsSettlementBody(
    @field:Element(name = "Set_items_settelmentResponse", required = false)
    var response: SetReturnItemsSettlementResponse? = null
)

@Root(name = "Set_items_settelmentResponse")
data class SetReturnItemsSettlementResponse(
    @field:Element(name = "Set_items_settelmentResult", required = false)
    var result: SetReturnItemsSettlementResult? = null
)

@Root(name = "Set_items_settelmentResult")
data class SetReturnItemsSettlementResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userId: String = "",


    @field:Element(name = "Message", required = false)
    var message: String = "",

    )


