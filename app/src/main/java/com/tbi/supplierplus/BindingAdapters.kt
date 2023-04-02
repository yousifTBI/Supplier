package com.tbi.supplierplus

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.pojo.reports.Returns
import com.tbi.supplierplus.business.pojo.reports.Sales
import com.tbi.supplierplus.framework.ui.sales.customer_profile.BillAdapter
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ReturnsAdapter

@BindingAdapter("listDataBill")
fun bindBill(recyclerView: RecyclerView, data: List<Sales>?) {
    val adapter = recyclerView.adapter as BillAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataReturns")
fun bindReturns(recyclerView: RecyclerView, data: List<Returns>?) {
    val adapter = recyclerView.adapter as ReturnsAdapter
    adapter.submitList(data)
}