package com.tbi.supplierplus.business.repository

import com.tbi.supplierplus.business.models.LocationDetails
import com.tbi.supplierplus.business.utils.Constants
import com.tbi.supplierplus.business.utils.NetworkState
import com.tbi.supplierplus.business.utils.isInternetAvailable
import com.tbi.supplierplus.business.utils.toModel
import com.tbi.supplierplus.di.GoogleService
import com.tbi.supplierplus.framework.datasource.network.GoogleMapsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface GeocodeRepository {
    fun getLocation(lat: Double, lng: Double): Flow<LocationDetails>
}

class GeocodeRepositoryImpl @Inject constructor(@GoogleService private val api: GoogleMapsAPI) :
    GeocodeRepository {
    override fun getLocation(lat: Double, lng: Double): Flow<LocationDetails> = flow {
        try {
            val response = api.getGeocodeLocationAsync(
                "$lat,$lng"
            ).await()
            emit(response.toModel(lat, lng))
        } catch (e: Exception) {
        }
    }

    fun <T> runApi(
        _apiStateFlow: MutableStateFlow<NetworkState>,
        block: Deferred<T>
    ) {

        _apiStateFlow.value = NetworkState.Loading
        try {
            if (isInternetAvailable())
                CoroutineScope(Dispatchers.IO).launch {

                    kotlin.runCatching {
                        block
                    }.onFailure {

                      //  Log.e(TAG, "runApi: 3")
                        when (it) {
                            is java.net.UnknownHostException ->
                                _apiStateFlow.value =
                                    NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                            is java.net.ConnectException ->
                                _apiStateFlow.value =
                                    NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                            else -> _apiStateFlow.value =
                                NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                        }

                    }.onSuccess {

                      //  Log.e(TAG, "runApi: 4")
                        if (it != null)
                            _apiStateFlow.value = NetworkState.Result(it)
                        else {
                       //     Log.e(TAG, "runApi: ${it.errorBody()}")
                            _apiStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                        }
                    }

                }
            else
                _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
        } catch (e: Exception) {
           // Log.e(TAG, "runApi: ${e.message}")
        }


    }

}