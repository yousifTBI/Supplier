package com.tbi.supplierplus.business.repository

import android.util.Log
import com.tbi.supplierplus.business.models.Expenses
import com.tbi.supplierplus.business.models.ExpensesType
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.expenses.AddExpenses
import com.tbi.supplierplus.business.pojo.expenses.ExpensesSearch
import com.tbi.supplierplus.business.pojo.expenses.Expensese
import com.tbi.supplierplus.business.utils.fromEnvelopeToModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.addExpensesRequestBody
import com.tbi.supplierplus.framework.datasource.requests.getExpensesRequestBody
import com.tbi.supplierplus.framework.datasource.requests.getExpensesTypesRequestBody
import com.tbi.supplierplus.framework.datasource.requests.getItemsBySearchRequestBody
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject


interface ExpensesRepository {

    fun addExpenses(addExpenses: AddExpenses): Flow<Tasks<Expensese>>

    fun getExpensesSearchType():Flow<Tasks<ExpensesSearch>>
    fun GetExpenses( User_ID:String):Flow<Tasks<Expensese>>

//
    fun getExpenses(userID: String): Flow<List<Expenses>>

    fun getExpensesTypes(userID: String): Flow<List<ExpensesType>>

    fun addExpenses(
        userID: String,
        amount: String,
        comment: String,
        expensesId: String
    ): Flow<List<Expenses>>
}

class ExpensesRepositoryImpl @Inject constructor(private val api: SupplierAPI) :
    ExpensesRepository {

    override fun getExpensesSearchType(): Flow<Tasks<ExpensesSearch>> = flow {
            try {
                val response =
                    api.getExpensesSearchTypeAPI().await()
                emit(response)
                Log.d("ExpensesSearch",response.data.get(0).ExpenseType)
            }catch (ex:Exception){
                Log.d("ExpensesSearch",ex.message.toString())


            }
        }.flowOn(IO)

    override fun GetExpenses(User_ID: String): Flow<Tasks<Expensese>> = flow{
        try {
            val response =
                api.GetExpensesAPI(User_ID).await()
            emit(response)
        }catch (ex:Exception){

        }
    }.flowOn(IO)


    override fun addExpenses(addExpenses: AddExpenses): Flow<Tasks<Expensese>>  = flow{
        try {
            val response = api.addExpensesAPI(addExpenses).await()
            emit(response)

        }catch (ex:Exception){

        }
    }



    override fun getExpenses(userID: String): Flow<List<Expenses>> =
        flow {
            try {
                val response = api.getExpensesAsync(getExpensesRequestBody(userID)).await()
                emit(response.fromEnvelopeToModel())

            }catch (ex:Exception){

            }

        }.flowOn(IO)


    override fun getExpensesTypes(userID: String): Flow<List<ExpensesType>> =
        flow {
            try {  val response = api.getExpensesTypesAsync(getExpensesTypesRequestBody(userID)).await()
                emit(response.fromEnvelopeToModel())

            }catch (ex:Exception){

            }

        }.flowOn(IO)


    override fun addExpenses(
        userID: String, amount: String, comment: String, expensesId: String
    ): Flow<List<Expenses>> =
        flow<List<Expenses>> {
            try {
                val response =
                    api.setExpensesAsync(addExpensesRequestBody(userID, amount, comment, expensesId))
                        .await()
                emit(response.fromEnvelopeToModel())
            }catch (ex:Exception){

            }

        }.flowOn(IO)



}


