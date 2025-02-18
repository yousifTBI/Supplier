package com.tbi.supplierplus.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tbi.supplierplus.business.utils.Constants.GOOGLE_BASE_URL
import com.tbi.supplierplus.business.utils.Constants.TBI_BASE_URL
import com.tbi.supplierplus.framework.datasource.network.GoogleMapsAPI
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {


    @Provides
    @Singleton
    fun provideSimpleXML(): SimpleXmlConverterFactory {
        var strategy = AnnotationStrategy()
        var serializer = Persister(strategy)
        return SimpleXmlConverterFactory.createNonStrict(serializer)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        var client = OkHttpClient.Builder()

       // try {
            //client.cache(cache)
      //  client  .addInterceptor(object  LoggingInterceptor())
        client.networkInterceptors().add(StethoInterceptor())
           // client.connectTimeout(10, TimeUnit.SECONDS)

     // client.addNetworkInterceptor(interceptor) // same for .addInterceptor(...)
        client.connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
        client.writeTimeout(30, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)



      //  }catch ( ex:Exception){
      //       return client.callTimeout(23,TimeUnit.DAYS)
      //  }


        client.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("UserId", SharedPreferencesCom.getInstance().gerSharedUser_ID()).
                addHeader("DistributorGroupId", SharedPreferencesCom.getInstance().gerSharedDistributor_ID()).
               // addHeader("DistributorGroupId", SharedPreferencesCom.getInstance().getSerial_ID()).
                build()
            chain.proceed(request)
        }

        return client.build()
    }


    @Singleton
    @Provides
    fun provideTBIAPI(xmlFactory: SimpleXmlConverterFactory, client: OkHttpClient): SupplierAPI {
       // try {
            return Retrofit.Builder()
             // .addConverterFactory(xmlFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
              //.baseUrl(TBI_BASE_URL)
                .baseUrl("https://dawar-api.unoerp.app/")
                .client(client)
                .build()
                .create(SupplierAPI::class.java)

  }




    @Singleton
    @Provides
    fun provideGeoLocationAPI(): GoogleMapsAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(GOOGLE_BASE_URL)
        .build().create(GoogleMapsAPI::class.java)
}