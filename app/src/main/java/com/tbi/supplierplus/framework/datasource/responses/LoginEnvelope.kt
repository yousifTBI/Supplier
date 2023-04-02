package com.tbi.supplierplus.framework.datasource.responses

import com.tbi.supplierplus.business.models.User
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import retrofit2.Call

data class LoginEnvelope(
    @field:Element(name = "Body", required = false)
    var body: LoginBody? = null
)

@Root(name = "Body")
data class LoginBody(
    @field:Element(name = "GetLogin_By_Mac_IDResponse", required = false)
    var response: GetLogin_By_Mac_IDResponse? = null
)

@Root(name = "GetLogin_By_Mac_IDResponse")
data class GetLogin_By_Mac_IDResponse(
    @field:Element(name = "GetLogin_By_Mac_IDResult", required = false)
    var result: LoginResult? = null
)

@Root(name = "GetLogin_By_Mac_IDResult")
data class LoginResult(
    @field:Element(name = "UserName", required = false)
    var UserName: String = "",

    @field:Element(name = "GroupName", required = false)
    var GroupName: String = "",

    @field:Element(name = "Distributor", required = false)
    var Distributor: String = "",

    @field:Element(name = "Distributor_ID", required = false)
    var Distributor_ID: String = "",

    @field:Element(name = "Message", required = false)
    var Message: String = "",

    @field:Element(name = "MassID", required = false)
    var MassID: String = "",

    @field:Element(name = "UserId", required = false)
    var UserId: String = "",

    @field:Element(name = "PrintrName", required = false)
    var PrintrName: String = "",

)

