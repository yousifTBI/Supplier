package com.tbi.supplierplus.framework.ui.login

import android.content.Context
import android.util.Log
import com.tbi.supplierplus.business.pojo.RegistrationModel
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: SupplierAPI
) {



    fun loginInfoComb(androidID: String,context: Context) = wrapWithFlowApi(

        fetch = {
            api.LoginAPI(androidID)
        }
    , context
    ).flowOn(IO)




    fun RegistrationInfo(registrationModel: RegistrationModel,context: Context) = wrapWithFlowApi(

        fetch = {
            api.RegistrationAPI(registrationModel)
        }
    ,context
    ).flowOn(IO)



    fun LoginAoiRepo(androidID: String): Flow<State<Task3<DistrputerLogin>>> {
        return flow {

            try {
                if (isInternetAvailable()) {
                    emit(State.Loading)
                    kotlin.runCatching {
                        api.LoginAPI(androidID)!!.await()
                    }.onFailure {
                        emit(State.Error(it.toString()))

                    }.onSuccess {
                        try {
                            emit(State.Success(it))
                            //cached
                        } catch (e: Exception) {
                            Log.d("getItemByBarcodeV1API", e.message.toString())
                        }
                        //   emit( State.Success(it))
                    }

                } else {
                    emit(State.Error("لايوجد اتصال بالانترنت "))
                }
                //  _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
            } catch (e: Exception) {
                Log.d("makeText", e.message.toString())


            }

        }.flowOn(IO)
    }




}

