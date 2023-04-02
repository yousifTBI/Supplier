package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



data class SetDailyClosingEnvelope(
    @field:Element(name = "Body", required = false)
    var body: SetDailyClosingBody? = null,
)

@Root(name = "Body")
data class SetDailyClosingBody(
    @field:Element(name = "SetClosingDayResponse", required = false)
    var response: SetDailyClosingResponse? = null
)

@Root(name = "SetClosingDayResponse")
data class SetDailyClosingResponse(
    @field:Element(name = "SetClosingDayResult", required = false)
    var result: SetDailyClosingResult? = null
)

@Root(name = "SetClosingDayResult")
data class SetDailyClosingResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "Message", required = false)
    var Message: String = "",

    )


