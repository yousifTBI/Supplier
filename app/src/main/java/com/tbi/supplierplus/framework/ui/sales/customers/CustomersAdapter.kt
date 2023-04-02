package com.tbi.supplierplus.framework.ui.sales.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.databinding.CustomerRowBinding

class CustomersAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Customer, CustomersViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersViewHolder {
        val binding = DataBindingUtil.inflate<CustomerRowBinding>(
            LayoutInflater.from(parent.context), R.layout.customer_row, parent, false
        )
        return CustomersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.customerID == newItem.customerID
        }
    }
}

class CustomersViewHolder(private var binding: CustomerRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(customer: Customer) {
        binding.data = customer
        binding.executePendingBindings()
    }
}

class OnClickListener(val clickListener: (customer: Customer) -> Unit) {
    fun onClick(customer: Customer) = clickListener(customer)
}