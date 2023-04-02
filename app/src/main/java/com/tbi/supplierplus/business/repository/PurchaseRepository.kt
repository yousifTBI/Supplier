package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.models.PurchaseItem
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.puarchase.AddBurchace
import com.tbi.supplierplus.business.pojo.puarchase.ItemsPRCh
import com.tbi.supplierplus.business.pojo.puarchase.Puarchase
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.business.utils.isInternetAvailable
import com.tbi.supplierplus.di.TBIService
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


interface PurchaseRepository {

   fun getItemsforPuarches( UserID:String ): Flow<Tasks<ItemsPRCh>>
   fun getPurchases( User_ID:String): Flow<Tasks<Puarchase>>
   fun addPurchases( addBurchace: AddBurchace): Flow<Tasks<Puarchase>>
    fun getItemByBarcodeV1API( salas_Id:String,
                               Barcode:String,Cus_id:String): Flow<State<Tasks<Items>>>
//  fun getPurchases(userID: String): Flow<List<PurchaseItem>>

//  fun addPurchase(
//      itemID: String,
//      itemCount: Int,
//      userID: String,
//      supplierID: String,
//  ): Flow<List<PurchaseItem>?>
}

class PurchaseRepositoryImpl @Inject constructor(@TBIService private val api: SupplierAPI) :
    PurchaseRepository {
    override  fun getItemsforPuarches(UserID: String): Flow<Tasks<ItemsPRCh>> = flow {
        try {
            val response = api.getItemsforPuarches(UserID).await()
            emit(response)
        } catch (ex: Exception) {
          //  Log.d("nnooono", ex.message.toString())

        }

    }.flowOn(IO)
    override fun getItemByBarcodeV1API(salas_Id: String, Barcode: String,Cus_id:String): Flow<State<Tasks<Items>>> = flow  {
        try {
            if (isInternetAvailable()){
                emit(State.Loading)

                kotlin.runCatching {
                    api. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).await()
                  //  api.getItemByBarcodeV1API(salas_Id, Barcode, Cus_id).await()
                }.onFailure {

                    emit(State.Error(it.toString()))


                }.onSuccess {
                    try {
                        emit( State.Success(it))
                    }catch (e :Exception){
                        Log.d("getItemByBarcodeV1API",e.message.toString())
                    }
                }

            }else {
                emit(State.Error("لايوجد اتصال بالانترنت "))
            }
            //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
        } catch (e: Exception) {
            Log.d("makeText",e.message.toString())


            //      // emit(State.Error(e.toString()))
            //      // Log.e(TAG, "runApi: ${e.message}")
        }
      // try {
      //     val response=api. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).await()
      //     emit(response)
      // }catch (ex:Exception){
      //     Log.d("messagee",ex.message.toString())

      // }
    }.flowOn(IO)
    override fun getPurchases(User_ID: String): Flow<Tasks<Puarchase>> = flow {
        try {
            val response = api.GetPurchases(User_ID).await()
            emit(response)
        } catch (ex: Exception) {
          //  Log.d("nnooono", ex.message.toString())

        }
    }.flowOn(IO)

    override fun addPurchases(addBurchace: AddBurchace): Flow<Tasks<Puarchase>> = flow {

        try {
            val response = api.addPurchases(addBurchace).await()
            emit(response)
            Log.d("sccss", response.Item?.Total.toString())

        } catch (ex: Exception) {
            Log.d("nnooono", ex.message.toString())

        }
    }.flowOn(IO)

//_اسم المدرسه _اللوكيشن _ ارقام العربيات المدرسه <<المدرسه
    // اسم الابن -اسمه-رقم تليفونه - الحاله الاجتماعيه -المدرسه - عنوان الاب -<<الووالد
//   override fun getPurchases(userID: String): Flow<List<PurchaseItem>> = flow {

//       try {
//           val response = api.getPurchasesAsync(getPurchaseRequestBody(userID)).await()
//           emit(response.fromEnvelopeToModel())
//       }catch (ex:Exception){

//       }

//   }.flowOn(IO)

//   override fun addPurchase(
//       itemID: String,
//       itemCount: Int,
//       userID: String,
//       supplierID: String,

//   ): Flow<List<PurchaseItem>?> = flow<List<PurchaseItem>?> {

//       try {
//           val response = api.addPurchaseAsync(
//               addPurchaseRequestBody(
//                   itemID = itemID,
//                   count = itemCount,
//                   userID = userID,
//                   supplierID = supplierID
//               )
//           ).await()
//           emit(response.fromEnvelopeToModel())
//       }catch (ex:Exception){

//       }


//   }.flowOn(IO)

}

