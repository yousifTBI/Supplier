package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface CustomersRepository {
    fun getRegions(userID: String): Flow<List<Region>>
    fun addCustomer(
        companyName: String,
        contactName: String,
        telephone1: String,
        telephone2: String,
        email: String,
        address: String,
        regionID: String,
        userID: String,
        distributorID: String
    ): Flow<List<Customer>>
}

class CustomersRepositoryImpl @Inject constructor(private val api: SupplierAPI) :
    CustomersRepository {
    override fun getRegions(userID: String): Flow<List<Region>> =

        flow {
            try {
                val response = api.getRegionsAsync(getRegionsRequestBody(userID)).await()
                emit(response.fromEnvelopeToModel())
            }catch (ex:Exception){

            }

        }.flowOn(IO)

    override fun addCustomer(
        companyName: String,
        contactName: String,
        telephone1: String,
        telephone2: String,
        email: String,
        address: String,
        regionID: String,
        userID: String,
        distributorID: String
    ): Flow<List<Customer>> = flow {
        try {
            emit(
                api.addCustomerAsync(
                    getAddCustomerRequestBody(
                        companyName = companyName,
                        userID = userID,
                        contactName = contactName,
                        telephone1 = telephone1,
                        telephone2 = telephone2,
                        email = email,
                        address = address,
                        distributorID = distributorID,
                        regionID = regionID
                    )
                ).await().fromEnvelopeToModel()
            )
        } catch (e: Exception) {
        }
    }.flowOn(IO)
}