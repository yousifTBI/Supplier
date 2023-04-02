package com.tbi.supplierplus.business.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tbi.supplierplus.DialogBill
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.addItem.NewItem
import com.tbi.supplierplus.business.pojo.addItem.Supplier
import com.tbi.supplierplus.business.pojo.addItem.TypeOfcategory
import com.tbi.supplierplus.business.pojo.closing.SupplierReport
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.business.utils.isInternetAvailable
import com.tbi.supplierplus.di.TBIService
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.datasource.requests.getRegisterRequestBody
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject
interface AddItemRepository{
    fun getItemGroup(UserId:String, Supplier_ID:String): Flow<Tasks<TypeOfcategory>>
    fun GetSuppliers( UserId:String): Flow<Tasks<Supplier>>
    fun setNewItem( setItem: NewItem):Flow<State<Tasks<Supplier>>>

}
class AddItemRepositoryImpl @Inject constructor(@TBIService private val api: SupplierAPI) : AddItemRepository {




    override fun getItemGroup(UserId: String, Supplier_ID: String): Flow<Tasks<TypeOfcategory>>  = flow{
       //try {
       //    if (isInternetAvailable()){
       //        emit(State.Loading)

       //        kotlin.runCatching {
       //            api.GetItemGroupAPI(UserId,Supplier_ID).await()
       //          //  api. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).await()
       //            //  api.getItemByBarcodeV1API(salas_Id, Barcode, Cus_id).await()
       //        }.onFailure {

       //            emit(State.Error(it.toString()))


       //        }.onSuccess {
       //            try {
       //                emit( State.Success(it))
       //            }catch (e :Exception){
       //                Log.d("getItemByBarcodeV1API",e.message.toString())
       //            }
       //        }

       //    }else {
       //        emit(State.Error("لايوجد اتصال بالانترنت "))
       //    }
       //    //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
       //} catch (e: Exception) {
       //    Log.d("makeText",e.message.toString())


       //    //      // emit(State.Error(e.toString()))
       //    //      // Log.e(TAG, "runApi: ${e.message}")
       //}
        try {
            val response =
                api.GetItemGroupAPI(UserId,Supplier_ID).await()
                emit(response)
        }catch (ex:Exception){

        }
      // when( it){

      //     is State.Loading ->Log.d("","")
            // Toast.makeText(baseContext, it.toLoading(), Toast.LENGTH_SHORT).show()

       //    is State.Success ->if (it.data.State==1){
       //        var split= DialogBill(it.data.Item!!.item)
       //        binding.textView8.setText(split.name)
       //        binding.Size1.setText(split.size)
       //        binding.sours1.setText(split.sur)
       //        // binding.textView2.setText(it.Supply_Price.toString())
       //        binding.textView2.setText(it.data.Item.Supply_Price.toString())
       //        item_id= it.data.Item.Item_ID.toString()
       //        supllyid=it.data.Item.getSupplier_ID()
       //    }else{

       //        Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
       //        Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

       //    }
       //    is State.Error ->  Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()

       //}
        }.flowOn(Dispatchers.IO)

    override fun GetSuppliers(UserId: String): Flow<Tasks<Supplier>>  = flow{
        try {
            val response =
                api.GetSuppliersAPI(UserId).await()
            emit(response)
        }catch (ex:Exception){

        }
    }.flowOn(Dispatchers.IO)

    override fun setNewItem(setItem: NewItem): Flow<State<Tasks<Supplier>>> = flow {
        try {
            if (isInternetAvailable()){
                emit(State.Loading)

                kotlin.runCatching {
                    api.Set_New_ItemAPI(setItem).await()
                    //  api. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).await()
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
    //   try {
    //       val response =
    //           api.Set_New_ItemAPI(setItem).await()
    //       emit(response)
    //   }catch (ex:Exception){
    //       Log.d("sqsq",ex.message.toString())

    //   }
    //


    }.flowOn(Dispatchers.IO)

}