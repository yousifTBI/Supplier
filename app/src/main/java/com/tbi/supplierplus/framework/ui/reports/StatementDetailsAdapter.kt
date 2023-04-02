package com.tbi.supplierplus.framework.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.CustomerStatement
import com.tbi.supplierplus.business.models.ItemsSummary
import com.tbi.supplierplus.databinding.RowStatementBinding
import com.tbi.supplierplus.databinding.RowStatementDetailsBinding

class StatementDetailsAdapter :
    ListAdapter<CustomerStatement, CustomerStatementDetailsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerStatementDetailsViewHolder {
        val binding = DataBindingUtil.inflate<RowStatementDetailsBinding>(
            LayoutInflater.from(parent.context), R.layout.row_statement_details, parent, false
        )
        return CustomerStatementDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerStatementDetailsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<CustomerStatement>() {
        override fun areItemsTheSame(oldItem: CustomerStatement, newItem: CustomerStatement): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CustomerStatement, newItem: CustomerStatement): Boolean {
            return oldItem.billID == newItem.billID
        }
    }


}

class CustomerStatementDetailsViewHolder(private var binding: RowStatementDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CustomerStatement) {
        binding.data = item
        binding.executePendingBindings()
    }
}

