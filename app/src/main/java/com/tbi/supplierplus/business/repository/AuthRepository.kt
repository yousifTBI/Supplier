package com.tbi.supplierplus.business.repository

import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.di.TBIService
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.getLoginRequestBody
import com.tbi.supplierplus.framework.datasource.requests.getRegisterRequestBody
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface AuthRepository {
    suspend fun register(
        androidID: String,
        serialID: String,
        userName: String,
    ): Flow<String>

    fun login(
        androidID: String, serialID: String
    ): Flow<User>
}

class AuthRepositoryImpl @Inject constructor(@TBIService private val api: SupplierAPI) : AuthRepository {
    override suspend fun register(
        androidID: String,
        serialID: String,
        userName: String,

        ): Flow<String> = flow {
        try {
            val response =
                api.registerAsync(getRegisterRequestBody(androidID, serialID, userName)).await()
            emit(response.body!!.response!!.result!!.message)
        }catch (ex:Exception){

        }

    }.flowOn(IO)

    override  fun login(
        androidID: String,
        serialID: String): Flow<User> = flow {
        try {
            val response =
                api.loginAsync(getLoginRequestBody(androidID, serialID)).await()
            emit(response.fromEnvelopeToModel())
        }catch (ex:Exception){

        }

    }.flowOn(IO)



}