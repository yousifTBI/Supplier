package com.tbi.supplierplus.framework.ui.sales.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.pojo.CustomersModel
import com.tbi.supplierplus.databinding.CustomerRowBinding
import com.tbi.supplierplus.databinding.CustomersRowBinding

class CustomersAdapter1(val onClickListener: OnClickListeners) :
    ListAdapter<CustomersModel, CustomersViewHolders>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersViewHolders {
        val binding = DataBindingUtil.inflate<CustomersRowBinding>(
            LayoutInflater.from(parent.context), R.layout.customers_row, parent, false
        )
        return CustomersViewHolders(binding)
    }

    override fun onBindViewHolder(holder: CustomersViewHolders, position: Int) {

        holder.bind( getItem(position))
      //  getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
           // getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CustomersModel>() {
        override fun areItemsTheSame(oldItem: CustomersModel, newItem: CustomersModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CustomersModel, newItem: CustomersModel): Boolean {
            return oldItem.Customer_ID == newItem.Customer_ID
        }
    }
}

class CustomersViewHolders(private var binding: CustomersRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(customer: CustomersModel) {
        binding.data = customer
        binding.executePendingBindings()
    }
}

class OnClickListeners(val clickListener: (customer: Customer) -> Unit) {
    fun onClick(customer: Customer) = clickListener(customer)
}