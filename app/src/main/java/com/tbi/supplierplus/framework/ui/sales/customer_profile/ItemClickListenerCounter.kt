package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.view.View

interface ItemClickListenerCounter {

    fun minus(textData: View, position:Int)
    fun add(textData: View, position:Int)
    abstract fun SaleingBillAdpter(): SaleingBillAdpter
}