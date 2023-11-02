package com.tbi.supplierplus.framework.ui.daily_closing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.ItemsBillsModel
import com.tbi.supplierplus.business.pojo.reports.Invoices
import com.tbi.supplierplus.databinding.ItemBiillsRowBinding
import com.tbi.supplierplus.databinding.RowStatementBinding

class ItemsBillAdapet (val onBillClickListener: OnBillClickListener) :
    ListAdapter<ItemsBillsModel, CustomerStatementViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerStatementViewHolder {
        val binding = DataBindingUtil.inflate<ItemBiillsRowBinding>(
            LayoutInflater.from(parent.context), R.layout.item_biills_row, parent, false
        )
        return CustomerStatementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerStatementViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onBillClickListener.onClick(it1) }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ItemsBillsModel>() {
        override fun areItemsTheSame(
            oldItem: ItemsBillsModel,
            newItem: ItemsBillsModel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ItemsBillsModel,
            newItem: ItemsBillsModel
        ): Boolean {
            return oldItem.billNo== newItem.billNo
        }
    }
}

class CustomerStatementViewHolder(private var binding:ItemBiillsRowBinding  ) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemsBillsModel) {
        binding.data = item
        binding.executePendingBindings()
    }
}


class OnBillClickListener(val clickListener: (statement: ItemsBillsModel) -> Unit) {
    fun onClick(statement: ItemsBillsModel) = clickListener(statement)
}
