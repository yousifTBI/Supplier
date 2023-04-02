package com.tbi.supplierplus.framework.datasource.network

import com.tbi.supplierplus.framework.datasource.responses.RegisterEnvelope
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SuppliersAPI {

    // fun closed(): Observable<Task1Closed?>?
    // GET api/Product/SetClosingDay


    @Headers("Content-Type: application/json")
    @GET("api/Sale/GetCusBySearch")
    fun GetCusBySearch(@Query("UserID") UserID:String): Deferred<RegisterEnvelope>

}