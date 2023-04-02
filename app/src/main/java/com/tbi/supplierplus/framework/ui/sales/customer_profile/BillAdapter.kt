package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.reports.Sales
import com.tbi.supplierplus.databinding.BillRowBinding
import com.tbi.supplierplus.databinding.CustomerRowBinding
import com.tbi.supplierplus.framework.ui.sales.customers.CustomersViewHolder

class BillAdapter():
    ListAdapter<Sales, BillViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val binding = DataBindingUtil.inflate<BillRowBinding>(
            LayoutInflater.from(parent.context), R.layout.bill_row, parent, false
        )
        return BillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Sales>() {
        override fun areItemsTheSame(oldItem: Sales, newItem: Sales): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Sales, newItem: Sales): Boolean {
            return oldItem.Items == newItem.Items
        }
    }


}

class BillViewHolder(private var binding: BillRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Sales) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

