package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.opening.AddCollection
import com.tbi.supplierplus.business.pojo.opening.AddOpening
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface DebitsRepository {
    fun getCustomerDebits(distributorID: String): Flow<Tasks<OpeningBalance>>
   // fun getCustomerDebits(distributorID: String): Flow<List<CustomerDebits>>
    fun addBalance( add:AddOpening): Flow<Tasks<OpeningBalance>>

    //  fun getOpeningBalances(distributorID: String): Flow<List<CustomerDebits>>
    fun getOpeningBalances(distributorID: String): Flow<Tasks<OpeningBalance>>
   // fun addBalance(customerID: String, userID: String, amount: String): Flow<String>
   //fun addCollection(
   //    customerID: String,
   //    userID: String,
   //    amount: String,
   //    remaining: String
   //): Flow<String>
    fun addCollection(
       addCollection:AddCollection): Flow<Tasks<AddCollection>>
}

class DebitsRepositoryImpl @Inject constructor(private val api: SupplierAPI) : DebitsRepository {
    override fun getCustomerDebits(distributorID: String): Flow<Tasks<OpeningBalance>> =
        flow {
            try {
                val response =
                    api.Get_AllDebtsAPI(distributorID).await()
                emit(response)
               // Log.i("size_repo", response.fromEnvelopeToModel().size.toString())

            } catch (e: Exception) {

            }
        }.flowOn(IO)

    override fun getOpeningBalances(distributorID: String): Flow<Tasks<OpeningBalance>> =
        flow {
            try {

                val response =api.getAllcustomersOpeningBalanceAPI("2").await()
                emit(response)

            } catch (e: Exception) {
              // emit(emptyList())
              // Log.e("error", e.message.toString())
              // e.printStackTrace()
            }
        }.flowOn( IO )

    override fun addCollection(addCollection: AddCollection): Flow<Tasks<AddCollection>> =
        flow{
            try {

                val response =api.AddCollectionAPI(addCollection).await()
                emit(response)

            } catch (e: Exception) {
                // emit(emptyList())
                // Log.e("error", e.message.toString())
                // e.printStackTrace()
            }

    }.flowOn( IO )

    override fun addBalance( add: AddOpening): Flow<Tasks<OpeningBalance>> =
        flow {

            try {
                Log.e("AddOpening", "e.message.toString()")

                val response =api.addCustomerOpeningBalanceAPI(add).await()
                emit(response)
             //   Log.d("AddOpening",Tasks<OpeningBalance>.Message)



                //   emit(
           //       api.addOpeningBalanceAsync(
           //           getAddBalanceRequestBody(
           //               customerID = customerID,
           //               amount = amount,
           //               userID = userID
           ///           )
           //       ).await().fromEnvelopeToModel()
           //   )
            } catch (e: Exception) {
              Log.e("AddOpenings", e.message.toString()+"Exception")
              e.printStackTrace()
            }
        }.flowOn(IO)

  // override fun addBalance(customerID: String, userID: String, amount: String): Flow<String> =
  //     flow {
  //         try {
  //             emit(
  //                 api.addOpeningBalanceAsync(
  //                     getAddBalanceRequestBody(
  //                         customerID = customerID,
  //                         amount = amount,
  //                         userID = userID
  //                     )
  //                 ).await().fromEnvelopeToModel()
  //             )
  //         } catch (e: Exception) {
  //             Log.e("error", e.message.toString())
  //             e.printStackTrace()
  //         }
  //     }.flowOn(IO)

  //  override fun addCollection(
  //      customerID: String,
  //      userID: String,
  //      amount: String,
  //      remaining: String
  //  ): Flow<String> = flow {
  //      try {
  //          emit(
  //              api.addCollectionAsync(
  //                  getAddCollectionRequestBody(
  //                      customerID = customerID,
  //                      amount = amount,
  //                      userID = userID,
  //                      remainingAmount = remaining
  //                  )
  //              ).await().fromEnvelopeToModel()
  //          )
  //      } catch (e: Exception) {
  //          Log.e("error", e.message.toString())
  //          e.printStackTrace()
  //      }
  //  }.flowOn(IO)
}