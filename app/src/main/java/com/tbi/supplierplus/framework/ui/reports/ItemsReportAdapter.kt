package com.tbi.supplierplus.framework.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.reports.ItemReport
import com.tbi.supplierplus.databinding.RowItemsReportBinding

class ItemsReportAdapter() :
    ListAdapter<ItemReport, ItemsReportViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsReportViewHolder {
        val binding = DataBindingUtil.inflate<RowItemsReportBinding>(
            LayoutInflater.from(parent.context), R.layout.row_items_report, parent, false
        )
        return ItemsReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsReportViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ItemReport>() {
        override fun areItemsTheSame(oldItem: ItemReport, newItem: ItemReport): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ItemReport, newItem: ItemReport): Boolean {
            return oldItem.Item_ID == newItem.Item_ID
        }
    }


}

class ItemsReportViewHolder(private var binding: RowItemsReportBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemReport) {
        binding.data = item
        binding.executePendingBindings()
    }
}

