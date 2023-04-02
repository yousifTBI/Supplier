package com.tbi.supplierplus.framework.datasource.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import retrofit2.Call

data class RegisterEnvelope(
    @field:Element(name = "Body", required = false)
    var body: Body? = null
)

@Root(name = "Body")
data class Body(
    @field:Element(name = "Registration_RequestResponse", required = false)
    var response: RegistrationResponse? = null
)

@Root(name = "Registration_RequestResponse")
data class RegistrationResponse(
    @field:Element(name = "Registration_RequestResult", required = false)
    var result: RegistrationResult? = null
)

@Root(name = "Registration_RequestResult")
data class RegistrationResult(
    @field:Element(name = "MassID", required = false)
    var massID: String = "",

    @field:Element(name = "UserID", required = false)
    var userID: String = "",

    @field:Element(name = "Message", required = false)
    var message: String = ""
)
