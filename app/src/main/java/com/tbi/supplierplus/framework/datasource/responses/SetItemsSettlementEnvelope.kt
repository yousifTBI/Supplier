package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


data class SetItemsSettlementEnvelope(
    @field:Element(name = "Body", required = false)
    var body: SetItemsSettlementBody? = null
)

@Root(name = "Body")
data class SetItemsSettlementBody(
    @field:Element(name = "Set_items_settelmentResponse", required = false)
    var response: SetItemsSettlementResponse? = null
)

@Root(name = "Set_items_settelmentResponse")
data class SetItemsSettlementResponse(
    @field:Element(name = "Set_items_settelmentResult", required = false)
    var result: SetItemsSettlementResult? = null
)

@Root(name = "Set_items_settelmentResult")
data class SetItemsSettlementResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserId", required = false)
    var userId: String = "",


    @field:Element(name = "Message", required = false)
    var message: String = "",

    )


