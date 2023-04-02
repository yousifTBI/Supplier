package com.tbi.supplierplus.framework.ui.collect_debit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.databinding.RowCustomerDebitBinding

class CustomerDebitsAdapter(val onClickListener: OnDebitClickListener) :
    ListAdapter<OpeningBalance, CustomerDebitsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerDebitsViewHolder {
        val binding = DataBindingUtil.inflate<RowCustomerDebitBinding>(
            LayoutInflater.from(parent.context), R.layout.row_customer_debit, parent, false
        )
        return CustomerDebitsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerDebitsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OpeningBalance>() {
        override fun areItemsTheSame(oldItem: OpeningBalance, newItem: OpeningBalance): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OpeningBalance, newItem: OpeningBalance): Boolean {
            return oldItem.cus_id == newItem.cus_id
        }
    }
}

class CustomerDebitsViewHolder(private var binding: RowCustomerDebitBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(CustomerDebit: OpeningBalance) {
        binding.data = CustomerDebit
        binding.executePendingBindings()
    }
}

class OnDebitClickListener(val clickListener: (CustomerDebit: OpeningBalance) -> Unit) {
    fun onClick(CustomerDebit: OpeningBalance) = clickListener(CustomerDebit)
}