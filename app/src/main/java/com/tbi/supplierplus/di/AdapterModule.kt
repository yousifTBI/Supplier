package com.tbi.supplierplus.di

import android.content.Context
import android.widget.Toast
import com.tbi.supplierplus.framework.ui.sales.customers.CustomersAdapter
import com.tbi.supplierplus.framework.ui.sales.customers.OnClickListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun provideCustomersAdapter(@ApplicationContext context: Context): CustomersAdapter =
        CustomersAdapter(onClickListener = OnClickListener {
            Toast.makeText(context, it.customerID, Toast.LENGTH_SHORT).show()
        })

}