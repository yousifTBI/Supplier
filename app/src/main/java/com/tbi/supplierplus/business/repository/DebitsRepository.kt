package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.opening.AddCollection
import com.tbi.supplierplus.business.pojo.opening.AddOpening
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface DebitsRepository {
    fun getCustomerDebits(distributorID: String): Flow<Tasks<OpeningBalance>>
    fun addBalance(add: AddOpening): Flow<Tasks<OpeningBalance>>
    fun getOpeningBalances(distributorID: String): Flow<Tasks<OpeningBalance>>
    fun addCollection(
        addCollection: AddCollection
    ): Flow<Tasks<AddCollection>>
}

class DebitsRepositoryImpl @Inject constructor(private val api: SupplierAPI) : DebitsRepository {
    override fun getCustomerDebits(distributorID: String): Flow<Tasks<OpeningBalance>> =
        flow {
            try {
                val response =
                    api.Get_AllDebtsAPI(distributorID).await()


                emit(response)

            } catch (e: Exception) {

            }
        }.flowOn(IO)

    override fun getOpeningBalances(distributorID: String): Flow<Tasks<OpeningBalance>> =
        flow {
            try {
                val response = api.getAllcustomersOpeningBalanceAPI(
                    SharedPreferencesCom.getInstance().gerSharedUser_ID()
                ).await()
                emit(response)
            } catch (e: Exception) {
            }
        }.flowOn(IO)

    override fun addCollection(addCollection: AddCollection): Flow<Tasks<AddCollection>> =
        flow {
            try {
                val response = api.AddCollectionAPI(addCollection).await()
                emit(response)
            } catch (e: Exception) {
            }

        }.flowOn(IO)

    override fun addBalance(add: AddOpening): Flow<Tasks<OpeningBalance>> =
        flow {

            try {
                val response = api.addCustomerOpeningBalanceAPI(add).await()
                emit(response)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(IO)


}