package com.tbi.supplierplus.framework.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.models.ItemsSummary
import com.tbi.supplierplus.business.pojo.reports.Invoices
import com.tbi.supplierplus.databinding.RowStatementBinding

class StatementAdapter(val onBillClickListener: OnBillClickListener) :
    ListAdapter<Invoices, CustomerStatementViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerStatementViewHolder {
        val binding = DataBindingUtil.inflate<RowStatementBinding>(
            LayoutInflater.from(parent.context), R.layout.row_statement, parent, false
        )
        return CustomerStatementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerStatementViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onBillClickListener.onClick(it1) }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Invoices>() {
        override fun areItemsTheSame(
            oldItem: Invoices,
            newItem: Invoices
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Invoices,
            newItem: Invoices
        ): Boolean {
            return oldItem.BillNo== newItem.BillNo
        }
    }
}

class CustomerStatementViewHolder(private var binding: RowStatementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Invoices) {
        binding.data = item
        binding.executePendingBindings()
    }
}


class OnBillClickListener(val clickListener: (statement: Invoices) -> Unit) {
    fun onClick(statement: Invoices) = clickListener(statement)
}

