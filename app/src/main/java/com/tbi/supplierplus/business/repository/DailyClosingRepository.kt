package com.tbi.supplierplus.business.repository

import com.tbi.supplierplus.business.models.DailyClosing
import com.tbi.supplierplus.business.models.SetDailyClosing
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.closing.SupplierReport
import com.tbi.supplierplus.business.pojo.reports.InvoiceDetails
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Query
import javax.inject.Inject


interface DailyClosingRepository {


    //fun getClosingDaySupplier( User_ID:String):Flow<Tasks<SupplierReport>>


    fun getDailyClosingExpenses(userID: String): Flow<Tasks<SupplierReport>>

    fun getDailyClosingSummaryItems(userID: String): Flow<Tasks<SupplierReport>>

    fun getDailyClosingPurchases(userID: String): Flow<Tasks<SupplierReport>>

    fun getDailyClosingSummarySupplier(userID: String): Flow<Tasks<SupplierReport>>

    fun setDailyClosing(userID: String): Flow<Tasks<InvoiceDetails>>

}

class DailyClosingRepositoryImpl @Inject constructor(private val api: SupplierAPI) :
    DailyClosingRepository {

    override fun getDailyClosingExpenses(userID: String): Flow<Tasks<SupplierReport>> =
        flow {
            try {
                val response = api.getClosingDayExpensesAPI(userID).await()
                emit(response)
            }catch (ex:Exception){

            }

        }.flowOn(IO)


    override fun getDailyClosingSummaryItems(userID: String): Flow<Tasks<SupplierReport>> =
        flow {
            try {
                val response = api.getClosingDayitemsAPI(userID).await()
                emit(response)
            }catch (ex:Exception){

            }

        }.flowOn(IO)


    override fun getDailyClosingPurchases(userID: String): Flow <Tasks<SupplierReport>> =
        flow {
            try {
                val response = api.getClosingDayPurchasesAPI(userID).await()
                emit(response)
            }catch (ex:Exception){

            }

        }.flowOn(IO)

    //1

    override fun getDailyClosingSummarySupplier(userID: String): Flow<Tasks<SupplierReport>> =
        flow{
            try {
                val response = api.getClosingDaySupplierAPI(userID).await()
                emit(response)
            }catch (ex:Exception){

            }

        }.flowOn(IO)

    override fun setDailyClosing(userID: String): Flow<Tasks<InvoiceDetails>> =
        flow<Tasks<InvoiceDetails>> {
            try {
                val response = api.SetClosingDayAPI((userID)
                ).await()
                emit(response)
            }catch (ex:Exception){

            }

        }.flowOn(IO)}


