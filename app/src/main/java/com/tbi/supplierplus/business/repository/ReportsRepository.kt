package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.reports.*
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.di.TBIService
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Query
import javax.inject.Inject


interface ReportsRepository {
    suspend    fun GetselesSummryForSpecificCustomer(
       User_ID:String,
        CusID:String
    ): Flow<Tasks<ReportSpecificCustomer>>

    suspend   fun getItemsReport( User_ID:String):    Flow<Tasks<ItemReport>>
    suspend    fun getSelesReport( User_ID:String): Flow<Tasks<SelesReport>>

    suspend    fun getCustomerStatement( CusID:String): Flow<Tasks<Invoices>>



    suspend   fun getSalesSummary(userID: String): Flow<SalesSummary>
    suspend  fun getItemsSummary(userID: String): Flow<List<ItemsSummary>>
   // fun getCustomerStatement(customerID: String): Flow<List<CustomerStatement>>
 suspend   fun getBillDetails(customerID: String, billNo: String): Flow<Tasks<InvoiceDetails>>

}
//GetselesSummryForSpecificCustomerAPI
class ReportsRepositoryImpl @Inject constructor(@TBIService private val api: SupplierAPI) :
    ReportsRepository {
    override suspend fun GetselesSummryForSpecificCustomer(
        User_ID: String,
        CusID: String
    ): Flow<Tasks<ReportSpecificCustomer>> = flow {

        try {
            val response = api.GetselesSummryForSpecificCustomerAPI(User_ID, CusID).await()
            emit(response)
        }catch (ex:Exception){

        }
    }.flowOn(IO)

    override suspend   fun getItemsReport(User_ID: String): Flow<Tasks<ItemReport>> = flow {
        try {
            val response = api.getItemsReportAPI(User_ID).await()
            emit(response)
        }catch (ex:Exception){

        }

    }.flowOn(IO)

    override  suspend   fun getSelesReport(User_ID: String): Flow<Tasks<SelesReport>> = flow {
        try {
            val response = api.getselesReportAPI(User_ID).await()
            emit(response)
        }catch (ex:Exception){

        }

    }.flowOn(IO)

    override suspend   fun getSalesSummary(userID: String): Flow<SalesSummary> = flow {
        try {
            val response = api.getSalesSummaryAsync(getSalesSummaryRequestBody(userID)).await()
            emit(response.fromEnvelopeToModel())
        }catch (ex:Exception){

        }

    }.flowOn(IO)

    override  suspend   fun getItemsSummary(userID: String): Flow<List<ItemsSummary>> = flow {

        try {
            val response = api.getItemsSummaryAsync(getItemsSummaryRequestBody(userID)).await()
            emit(response.fromEnvelopeToModel())

        }catch (ex:Exception){

        }

    }.flowOn(
        IO
    )

    override  suspend  fun getCustomerStatement(CusID: String): Flow<Tasks<Invoices>> =
        flow {
            try {
                val response =
                    api.getCustomerStatementAPI(CusID).await()
                emit(response)
            }catch (ex:Exception){

            }

        }.flowOn(
            IO
        )

    override  suspend fun getBillDetails(customerID: String, billNo: String): Flow<Tasks<InvoiceDetails>> = flow {
        try {
            val response =
                api.getPillDetailsAPI(billNo, customerID).await()
            emit(response)
        }catch (ex:Exception){
            Log.d("List<Returns>",ex.message.toString()+"rpo")

        }

    }.flowOn(
        IO
    )

}