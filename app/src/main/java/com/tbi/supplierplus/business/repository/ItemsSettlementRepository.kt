package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.addItem.TypeOfcategory
import com.tbi.supplierplus.business.pojo.returns.Suppliers
import com.tbi.supplierplus.business.pojo.settelment.ItemsSettelment
import com.tbi.supplierplus.business.pojo.settelment.SetItemsSettelment
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject


interface ItemsSettlementRepository {

    fun Setitemssettelment( setItemsSettelment: SetItemsSettelment):  Flow<Tasks<TypeOfcategory>>

    fun getReturnItemsFillSpinner(userID: String): Flow<Tasks<Suppliers>>
    fun getItemsSettelMent(  user_id:String, supplier_id:String):  Flow<Tasks<ItemsSettelment>>

    fun getItemsSettlementFillSpinner(userID: String): Flow<List<Item>>

    fun getItemsSettlement(userID: String,supplierId:String): Flow<List<GetItemsSettlement>>

    fun getItemsVsBill(userID: String,itemId:String): Flow<List<ItemsVsBill>>
    fun setItemsSettlement(userID: String,itemId:String,supplierId: String,itemCount: String)
    : Flow<SetReturnItemsSettlement>
}

class ItemsSettlementRepositoryImpl @Inject constructor(private val api: SupplierAPI) :
    ItemsSettlementRepository {
    override fun Setitemssettelment( setItemsSettelment: SetItemsSettelment):  Flow<Tasks<TypeOfcategory>> = flow {
        try {
            val response = api.SetitemssettelmentAPI(setItemsSettelment).await()
            emit(response)
        }catch (e: Exception){
             Log.d("returnCount",e.message.toString())


            Log.d("getReturn",e.message.toString())

        }
    }.flowOn(IO)

    override fun getItemsSettelMent(  user_id:String, supplier_id:String):  Flow<Tasks<ItemsSettelment>> = flow {
        try {
            val response = api.Get_items_settelmentAPI(user_id,supplier_id).await()
            emit(response)
        }catch (e: Exception){

            Log.d("getReturn",e.message.toString())

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

    override fun getItemsSettlementFillSpinner(userID: String): Flow<List<Item>> =
        flow {
            try {
                val response = api.getFillSpinnerReturnItemsAsync(
                    getFillSpinnerReturnItemsRequestBody(userID, spinnerId = "Supplier")
                ).await()
                emit(response.fromEnvelopeToModel())
            }catch (ex:Exception){

            }

        }.flowOn(IO)

    override fun getItemsSettlement(userID: String,supplierId:String): Flow<List<GetItemsSettlement>>  =
        flow {
            try {
                val response = api.getItemsSettlementAsync(
                    getItemsSettlementRequestBody(userID,supplierId)
                ).await()
                emit(response.fromEnvelopeToModel())
            }catch (ex:Exception){

            }

        }.flowOn(IO)

    override fun getItemsVsBill(userID: String, itemId: String): Flow<List<ItemsVsBill>>  =
        flow {
            try {
                val response = api.getItemsVsBillAsync(
                    getItemsVsBillRequestBody(userID,itemId)
                ).await()
                emit(response.fromEnvelopeToModel())
            }catch (ex:Exception){

            }

        }.flowOn(IO)

    override fun setItemsSettlement(userID: String, itemId: String, supplierId: String, itemCount: String): Flow<SetReturnItemsSettlement> =
        flow {
            try {
                val response = api.setItemsSettlementAsync(
                    setItemsSettlementRequestBody(userID,supplierId,itemId,itemCount)
                ).await()
                emit(response.fromEnvelopeToModel())
                Log.i("",response.fromEnvelopeToModel().toString())
            }catch (ex:Exception){

            }

        }.flowOn(IO)
}