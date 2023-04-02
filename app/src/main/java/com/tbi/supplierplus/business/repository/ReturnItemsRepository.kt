package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.models.ReturnItemsSettelment
import com.tbi.supplierplus.business.models.SetDailyClosing
import com.tbi.supplierplus.business.models.SetReturnItemsSettlement
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.returns.ReturnItems
import com.tbi.supplierplus.business.pojo.returns.Suppliers
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface ReturnItemsRepository {
    fun getReturnItemsSettelmentRecycler(userID: String,supplierId:String): Flow<Tasks<ReturnItems>>
    fun getReturnItemsFillSpinner(userID: String): Flow<Tasks<Suppliers>>

  // fun getReturnItemsFillSpinner(userID: String): Flow<List<Item>>
  // fun getReturnItemsSettelmentRecycler(userID: String,supplierId:String): Flow<List<ReturnItemsSettelment>>
  // fun setReturnItemsSettlement(userID: String,supplierId:String,itemId:String,itemCount:String): Flow<SetReturnItemsSettlement>

}

class ReturnItemsRepositoryImpl @Inject constructor(private val api: SupplierAPI) :
    ReturnItemsRepository {
    //  override fun getReturnItemsFillSpinner(userID: String): Flow<List<Item>> =
    //      flow<List<Item>> {
    //          try {
    //              val response = api.getFillSpinnerReturnItemsAsync(
    //                  getFillSpinnerReturnItemsRequestBody(userID, spinnerId = "Supplier")
    //              ).await()
    //              emit(response.fromEnvelopeToModel())
    //          }catch (ex:Exception){
//
    //          }
//
    //      }.flowOn(IO)
//
    //  override fun getReturnItemsSettelmentRecycler(userID: String,supplierId:String): Flow<List<ReturnItemsSettelment>> =
    //      flow<List<ReturnItemsSettelment>> {
    //          try {
    //              val response = api.getReturnItemsSettelmentAsync(
    //                  getReturnItemsSettelmentRequestBody(userID,supplierId )
    //              ).await()
    //              emit(response.fromEnvelopeToModel())
    //          }catch (ex:Exception){
//
    //          }
//
    //      }.flowOn(IO)
//
    //  override fun setReturnItemsSettlement(
    //      userID: String,
    //      supplierId: String,
    //      itemId: String,
    //      itemCount: String
    //  ): Flow<SetReturnItemsSettlement> =
    //      flow<SetReturnItemsSettlement> {
    //          try {
    //              val response = api.setReturnItemsSettlementAsync(
    //                  setItemsReturnsSettlementRequestBody(userID,supplierId,itemId,itemCount )
    //              ).await()
    //              emit(response.fromEnvelopeToModel())
    //          } catch (e: Exception) {
    //          }
    //      }.flowOn(IO)
    override fun getReturnItemsSettelmentRecycler(
        userID: String,
        supplierId: String): Flow<Tasks<ReturnItems>> = flow {
            try {
                val response = api.getReturnitemsAPI(userID, supplierId).await()
                            emit(response)
            }catch (e: Exception){

            }


    }.flowOn(IO)

    override fun getReturnItemsFillSpinner(
        userID: String): Flow<Tasks<Suppliers>> = flow {
        try {
            val response = api.getSuppliersAPI(userID).await()
            emit(response)
        }catch (e: Exception){

            Log.d("getReturn",e.message.toString())

        }
    }.flowOn(IO)


}