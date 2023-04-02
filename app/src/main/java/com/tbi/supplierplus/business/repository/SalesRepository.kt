package com.tbi.supplierplus.business.repository

import android.content.Context
import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.*
import com.tbi.supplierplus.business.pojo.addCustomer.NewCustomer
import com.tbi.supplierplus.business.pojo.addCustomer.Ranges
import com.tbi.supplierplus.business.pojo.addCustomer.Regions
import com.tbi.supplierplus.business.pojo.addCustomer.RusNewCustomer
import com.tbi.supplierplus.business.pojo.bills.NewBill
import com.tbi.supplierplus.business.pojo.price.EditProductprice
import com.tbi.supplierplus.business.pojo.price.SpecialPrice
import com.tbi.supplierplus.business.pojo.reports.ReportSpecificCustomer
import com.tbi.supplierplus.business.utils.*
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import com.tbi.supplierplus.framework.ui.login.wrapWithFlowApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface SalesRepository {

    fun EditItemByBarcode(
        editProductprice : EditProductprice
    ): Flow<Tasks<ReportSpecificCustomer>>
    fun AddCustomer(newCustomer: NewCustomer): Flow<Tasks<RusNewCustomer>>
    fun Get_Region(User_ID: String, Range_Id: String): Flow<Tasks<Regions>>

    fun GetRange(UserId: String): Flow<Tasks<Ranges>>

    fun getItems(
        userID: String
    ): Flow<List<Item>>

    fun getItemss(
        userID: Int,
        Cus_id: Int
    ): Flow<Tasks<Items>>

    fun getCustomers(
        userID: String
    ): Flow<List<Customer>>

    fun getAllCustomers(
        userID: String
    ): Flow<Tasks<AllCustomers>>

    fun getAllCustomerTesthandel(
        userID: String
    ): Flow<State<Tasks<AllCustomers>>>

    fun getProducerBycode(
        sales_Id: Int, Item_ID: String,
        Cus_id: String?
    ): Flow<Tasks<Producer>>

    fun getItemByBarcodeV1API(
        salas_Id: String,
        Barcode: String, Cus_id: String
    ): Flow<State<Tasks<Items>>>

    /// fun getAllCustomersa(
    ///     userID: String
    /// ): Flow<Tasks<AllCustomers>>

    fun getCustomerInfo(userID: String, customerID: String): Flow<Customer>
    fun getItemInfo(userID: String, customerID: String, itemID: String): Flow<Item>

    fun saveBill(
        bill: List<Item>,
        customerID: String,
        billDiscount: String,
        editor: String,
        salesID: String,
        total: String,
        cash: String,
        oldDeferred: String,
        deferred: String,
        returnAmount: String,
        returns: List<Item>
    ): Flow<BillResult>

    fun addItemsReturnForUsers(
        userID: String,
        amount: String,
        size: String,
        customerID: String,
        itemId: String
    ): Flow<List<AddItemsReturn>>

    fun addNewPill(bill: NewBill): Flow<Tasks<Tesssst>>
    fun SetSpecialItemPrice(specialPrice: SpecialPrice): Flow<Tasks<SpecialPrice>>

}

class SalesRepositoryImpl @Inject constructor(private val api: SupplierAPI) : SalesRepository {

    override fun getProducerBycode(
        sales_Id: Int,
        Item_ID: String,
        Cus_id: String?
    ): Flow<Tasks<Producer>> = flow {
        try {
            val responses = api.GetItemByBarcode(sales_Id, Item_ID, Cus_id).await()
            Log.d("poppo", responses.Item!!.ItemName1)

            emit(responses)
        } catch (ex: Exception) {
            Log.d("poppo", ex.message.toString())
        }

    }.flowOn(IO)


    override fun getItemss(
        userID: Int,
        Cus_id: Int
    ): Flow<Tasks<Items>> = flow {
        try {
            val responses = api.getitem(userID, Cus_id).await()
            emit(responses)
        } catch (ex: Exception) {
            Log.d("sjdkshd", ex.message.toString())
        }


    }.flowOn(IO)


    override fun getAllCustomers(
        userID: String
    ):
            Flow<Tasks<AllCustomers>> = flow {

        try {
            val tas = api.getAllCus(userID).await()
            Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
            emit(tas)
        } catch (ex: Exception) {
            Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

        }

    }.flowOn(IO)

    override fun EditItemByBarcode(editProductprice: EditProductprice): Flow<Tasks<ReportSpecificCustomer>> = flow{
        try {
            val tas = api.EditItemByBarcodeAPI(editProductprice).await()
            Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
            emit(tas)
        } catch (ex: Exception) {
            Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

        }
    }

     fun  customer(editProductprice: String ,context: Context) = wrapWithFlowApi(

         fetch = {
             api.getAllCus(editProductprice)
          //   api.getAllCus(userID).await()
         }
             ,context
     ).flowOn(IO)

    override fun AddCustomer(newCustomer: NewCustomer): Flow<Tasks<RusNewCustomer>> = flow {
        try {
            val tas = api.AddCustomerAPI(newCustomer).await()
            Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
            emit(tas)
        } catch (ex: Exception) {
            Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

        }
    }.flowOn(IO)

    override fun Get_Region(User_ID: String, Range_Id: String): Flow<Tasks<Regions>> = flow {
        try {
            val tas = api.Get_RegionAPI(User_ID, Range_Id).await()
            // Log.d("Get_Region",tas.State.toString()+"aaaaaaaaaa")
            emit(tas)
        } catch (ex: Exception) {
            Log.d("Get_Region", ex.message + "aaaaaaaaaa")

        }
    }

    override fun GetRange(UserId: String): Flow<Tasks<Ranges>> = flow {
        try {
            val tas = api.GetRangeAPI(UserId).await()
            Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
            emit(tas)
        } catch (ex: Exception) {
            Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

        }
    }.flowOn(IO)
    //      Flow<Tasks<AllCustomers>> = flow {
    //  try {
    //          val tas=api.getAllCus(userID).await()
    //  emit(tas)

    //  }catch (ex:Exception){


    //  }
    //  try {
    //      val responses=  api.GetCusBySearch(userID).await()
    //      emit(responses)

    //  }catch (ex:Exception){
    //      Log.d("sjdkshd",ex.message.toString())
    //  }


    //     val response =
    //         api.getItemsBySearchAsync(getItemsBySearchRequestBody(userID)).await()
    //     emit(response.fromEnvelopeToModel())
    // }catch (e:java.lang.Exception){
    //     Log.d("test api",e.toString())
    // }

    //  }.flowOn(IO)
    override fun getItems(
        userID: String
    ): Flow<List<Item>> = flow {
        try {

            //  val responses=  api.GetCusBySearch(userID).await()
            //   emit(responses)
            val response =
                api.getItemsBySearchAsync(getItemsBySearchRequestBody(userID)).await()
            emit(response.fromEnvelopeToModel())
        } catch (e: java.lang.Exception) {
            Log.d("test api", e.toString())
        }

    }.flowOn(IO)

    override fun getCustomers(
        userID: String
    ): Flow<List<Customer>> = flow {
        try {
            val response =
                api.getCustomerBySearchAsync(getCustomerBySearchBody(userID)).await()
            emit(response.fromEnvelopeToModel())
        } catch (ex: Exception) {
            Log.d("yuosif", ex.toString())
        }

    }.flowOn(IO)

    override fun getCustomerInfo(userID: String, customerID: String): Flow<Customer> =
        flow {
            try {
                val response =
                    api.getCustomerInfoAsync(getCustomerInfoRequestBody(userID, customerID)).await()
                emit(response.fromEnvelopeToModel(customerID))
            } catch (ex: Exception) {
            }

        }.flowOn(IO)

    override fun getItemInfo(userID: String, customerID: String, itemID: String): Flow<Item> =
        flow {
            try {
                val response =
                    api.getItemInfoAsync(
                        getInfoItemByIDRequestBody(
                            userID = userID,
                            customerID = customerID,
                            itemID = itemID
                        )
                    ).await()
                emit(response.fromEnvelopeToModel())
            } catch (ex: Exception) {
            }

        }.flowOn(IO)

    override fun saveBill(
        bill: List<Item>,
        customerID: String,
        billDiscount: String,
        editor: String,
        salesID: String,
        total: String,
        cash: String,
        oldDeferred: String,
        deferred: String,
        returnAmount: String,
        returns: List<Item>
    ): Flow<BillResult> = flow {

        try {
            var billString = ""
            repeat(bill.size) {
                billString = billString + "*" + it.toString() +
                        "|" + bill[it].name +
                        "|" + bill[it].quantity +
                        "|" + bill[it].size +
                        "|" + bill[it].sellingPrice +
                        "|" + bill[it].discount +
                        "|" + bill[it].totalSale().toString() +
                        "|" + bill[it].id +
                        "|" + bill[it].supplierID +
                        "|" + bill[it].supplyPrice
            }

            var returnString = ""
            repeat(returns.size) {
                returnString = returnString + "*" + it.minus(1).toString() +
                        "|" + returns[it].name +
                        "|" + returns[it].size +
                        "|" + returns[it].totalReturn().toString() +
                        "|" + returns[it].id
            }

            Log.i("BillDetails", billString)
            Log.i("ReturnString", returnString)
            Log.i("CustomerID", customerID)
            val response =
                api.addBillAsync(
                    addBillRequestBody(
                        billDetails = billString,
                        customerID = customerID,
                        billDiscount = billDiscount,
                        editor = editor,
                        salesID = salesID,
                        totalAmountBeforeDiscount = total,
                        totalAmountAfterDiscount = total.toFloat().minus(billDiscount.toFloat())
                            .toString(),
                        cash = cash,
                        deferred = deferred,
                        returnAmount = returnAmount,
                        billReturn = returnString,
                        oldDeferred = oldDeferred
                    )
                ).await()
            emit(response.fromEnvelopeToModel())
        } catch (ex: Exception) {
        }

    }.flowOn(IO)

    override fun addItemsReturnForUsers(
        userID: String, amount: String, size: String, customerID: String, itemId: String
    ): Flow<List<AddItemsReturn>> =
        flow {
            try {
                val response =
                    api.addItemsReturnAsync(
                        addItemsReturnRequestBody(
                            userID = userID,
                            customerID = customerID,
                            itemId = itemId,
                            amount = amount,
                            size = size
                        )
                    ).await()
                emit(response.fromEnvelopeToModel())
            } catch (e: Exception) {
            }
        }.flowOn(IO)

    override fun addNewPill(bill: NewBill): Flow<Tasks<Tesssst>> =
        flow {

            try {
                val response =
                    api.setPillAPI(bill).await()
                emit(response)
            } catch (e: Exception) {
                Log.e("haaaaaaaager", e.message.toString() + "rpo")

            }

        }.flowOn(IO)

    override fun SetSpecialItemPrice(specialPrice: SpecialPrice): Flow<Tasks<SpecialPrice>> =
        flow {
            try {
                val response =
                    api.SetSpecialItemPriceAPI(specialPrice).await()
                emit(response)
            } catch (e: Exception) {
                // Log.e("haaaaaaaager", e.message.toString()+"rpo")
                Log.d("skowxal", e.message.toString())

            }
        }.flowOn(IO)


    override fun getItemByBarcodeV1API(
        salas_Id: String,
        Barcode: String,
        Cus_id: String
    ): Flow <State<Tasks<Items>>> = flow {

        try {
              if (isInternetAvailable()){

            kotlin.runCatching {
               api.getItemByBarcodeV1API(salas_Id, Barcode, Cus_id).await()
            }.onFailure {


                emit(State.Error(it.toString()))

                //  Log.e(TAG, "runApi: 3")
                //  when (it) {
                //      is java.net.UnknownHostException ->
                //          _apiStateFlow.value =
                //        NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                //      is java.net.ConnectException ->
                //          _apiStateFlow.value =
                //              NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                //      else -> _apiStateFlow.value =
                //          NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                //  }

            }.onSuccess {
                try {
                    emit( State.Success(it))
                }catch (e :Exception){
                    Log.d("getItemByBarcodeV1API",e.message.toString())
                }

             //   emit( State.Success(it))



                //
                //

                //  Log.e(TAG, "runApi: 4")
                //     if (it != null)
                //        // emit(it)
                //     //   _apiStateFlow.value = NetworkState.Result(it)
                //     else {

                //         //     Log.e(TAG, "runApi: ${it.errorBody()}")
                //         // _apiStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                //     }
            }

            // }
         //
         //
         //        }
            }else {
                emit(State.Error("لايوجد اتصال بالانترنت "))
            }
            //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
        } catch (e: Exception) {
            Log.d("makeText",e.message.toString())


            //      // emit(State.Error(e.toString()))
            //      // Log.e(TAG, "runApi: ${e.message}")
        }

   //    try {
   //        val response = api.getItemByBarcodeV1API(salas_Id, Barcode, Cus_id).await()
   //        emit(response)
   //    } catch (ex: Exception) {
   //        Log.d("messagee", ex.message.toString())

   //    }
    }.flowOn(IO)




    override fun getAllCustomerTesthandel(userID: String)


                                          //,_addRateStateFlow:MutableStateFlow<NetworkState>): Flow<Tasks<AllCustomers>> =
      //  flow {
       //    _addRateStateFlow.value = NetworkState.Loading

       //    //  val tas=api.getAllCus(userID).await()
       //    // // Log.d("ssssssssssssss",tas.State.toString()+"aaaaaaaaaa")
       //    //  emit(tas)
       //    runApi(
       //        _addRateStateFlow,
       //        api.getAllCus(userID)
       //    )

          :  Flow<State<Tasks<AllCustomers>>> = flow {
        Log.d("makeText","Loadingflow ")
       // runApi( api.getAllCustomers(userID))
      //  try {
      //      runApi(api.getAllCustomers(userID))
      //  }catch (e:Exception){
      //      Log.d("makeText",e.message.toString())
//
      //  }
       // runApi( api.getAllCustomers(userID))
      // runApi(  api.getAllCustomers(userID))
       try {
           //  if (isInternetAvailable())
          // CoroutineScope(IO).launch {
               Log.d("makeText","Loadingds")

               kotlin.runCatching {
                   Log.d("makeText","runCatching")

                   api.getAllCustomers(userID).await()
                //   block
               }.onFailure {
                   Log.d("makeText","Loadingd")
                   emit(State.Error(it.toString()))


                   //  Log.e(TAG, "runApi: 3")
                   //  when (it) {
                   //      is java.net.UnknownHostException ->
                   //          _apiStateFlow.value =
                         //        NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                   //      is java.net.ConnectException ->
                   //          _apiStateFlow.value =
                   //              NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                   //      else -> _apiStateFlow.value =
                   //          NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                   //  }

               }.onSuccess {

                   try {
                       emit( State.Success(it))
                   }catch (e :Exception){
                       Log.d("makeText",e.message.toString())
                   }


                  //
                //

                   //  Log.e(TAG, "runApi: 4")
                   //     if (it != null)
                   //        // emit(it)
                   //     //   _apiStateFlow.value = NetworkState.Result(it)
                   //     else {

                   //         //     Log.e(TAG, "runApi: ${it.errorBody()}")
                   //         // _apiStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                   //     }
               }

               // }
               // else {
          // }
           //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
       } catch (e: Exception) {
           Log.d("makeText",e.message.toString())


     //      // emit(State.Error(e.toString()))
     //      // Log.e(TAG, "runApi: ${e.message}")
       }
             // runApi(api.getAllCus(userID))

    //    wrapWithFlow(api.getAllCus(userID))
     //  if (isInternetAvailable()){


     //      emit(State.Loading)
     //      kotlin.runCatching {
     //          val tas = api.getAllCus(userID).await()
     //         // Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
     //       //   emit(State.Success( tas))
     //          // block
     //      }.onFailure{

     //      }.onSuccess{

     //     //    emit(State.Success( it))
     //      }
      //     try {
      //         val tas = api.getAllCus(userID).await()

      //         Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
      //         emit(State.Success( tas))
      //     } catch (ex: Exception) {
      //         emit(State.Error( ex.message.toString()))
      //         Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

      //     }
      //  else{
      //      emit(State.Error( "الاتصال ب الانترنت ضعيف"))
      //  }

      // try {
      //     if (isInternetAvailable())
      //     //  CoroutineScope(IO).launch {

      //         kotlin.runCatching {
      //             val tas = api.getAllCustomers(userID).await()
      //             emit(tas)
      //
      //           //  block
      //         }.onFailure {

      //             //  Log.e(TAG, "runApi: 3")
      //             //  when (it) {
      //             //      is java.net.UnknownHostException ->
      //             //          _apiStateFlow.value =
      //             //              NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
      //             //      is java.net.ConnectException ->
      //             //          _apiStateFlow.value =
      //             //              NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
      //             //      else -> _apiStateFlow.value =
      //             //          NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
      //             //  }

      //         }.onSuccess {

      //            // emit(it)
      //             //  Log.e(TAG, "runApi: 4")
      //             if (it != null)
      //             // emit(it)
      //             //   _apiStateFlow.value = NetworkState.Result(it)
      //             else {

      //                 //     Log.e(TAG, "runApi: ${it.errorBody()}")
      //                 // _apiStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
      //             }
      //         }

      //     // }
      //     else {
      //     }
      //     //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
      // } catch (e: Exception) {
      //     // Log.e(TAG, "runApi: ${e.message}")
      // }

            }
     //   .flowOn(IO)
        }

 //  fun  runApi(
 //     // _apiStateFlow: MutableStateFlow<NetworkState>,
 //      block:(String)->


 //      Deferred<Tasks<AllCustomers>>
 //  )
 //          {
 // //     Log.d("makeText","Loadings1")

 //   //   return flow<State<Tasks<T>>> {
 //          Log.d("makeText","Loadings")

 //          emit(State.Loading)
 //      //  _apiStateFlow.value = NetworkState.Loading
 //      try {
 //        //  if (isInternetAvailable())
 //        //  CoroutineScope(IO).launch {
 //          Log.d("makeText","Loadingds")

 //              kotlin.runCatching {
 //                  Log.d("makeText","runCatching")

 //                  block.invoke("2").await()
 //              }.onFailure {
 //                  emit(State.Error(it.toString()))
 //                  Log.d("makeText","Loadingd")

 //                  //  Log.e(TAG, "runApi: 3")
 //                  //  when (it) {
 //                  //      is java.net.UnknownHostException ->
 //                  //          _apiStateFlow.value =
 //                  //              NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
 //                  //      is java.net.ConnectException ->
 //                  //          _apiStateFlow.value =
 //                  //              NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
 //                  //      else -> _apiStateFlow.value =
 //                  //          NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
 //                  //  }

 //              }.onSuccess {
 //                emit( State.Success(it))
 //                  Log.d("makeText","Loadinga")

 //                  //  Log.e(TAG, "runApi: 4")
 //            //     if (it != null)
 //            //        // emit(it)
 //            //     //   _apiStateFlow.value = NetworkState.Result(it)
 //            //     else {

 //            //         //     Log.e(TAG, "runApi: ${it.errorBody()}")
 //            //         // _apiStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
 //            //     }
 //              }

 //          // }
 //        // else {
 //       //  }
 //          //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
 //      } catch (e: Exception) {
 //          Log.d("makeText",e.message.toString())


 //          // emit(State.Error(e.toString()))
 //          // Log.e(TAG, "runApi: ${e.message}")
 //      }
 //  }
 //          .flowOn(IO)





