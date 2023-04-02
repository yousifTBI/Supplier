package com.tbi.supplierplus.framework.datasource.network

import com.tbi.supplierplus.BuildConfig
import com.tbi.supplierplus.business.repository.GeocodeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsAPI {

    @GET("maps/api/geocode/json")
    fun getGeocodeLocationAsync(
        @Query("latlng") location: String,
        @Query("key") key: String = BuildConfig.MAPS_API_KEY,
    ):
            Deferred<GeocodeResponse>
}