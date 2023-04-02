package com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.closing.SupplierReport
import com.tbi.supplierplus.databinding.DailyClosingItemRowBinding

class AdapterItemsReceived : ListAdapter<SupplierReport, DailyClosingViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyClosingViewHolder {
        val binding = DataBindingUtil.inflate<DailyClosingItemRowBinding>(
            LayoutInflater.from(parent.context), R.layout.daily_closing_item_row, parent, false
        )
        return DailyClosingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyClosingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        //  getItem(position).


    }

    companion object DiffCallback : DiffUtil.ItemCallback<SupplierReport>() {
        override fun areItemsTheSame(oldItem: SupplierReport, newItem: SupplierReport): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SupplierReport, newItem: SupplierReport): Boolean {
            return oldItem.Amount == newItem.Amount
        }
    }




}
class DailyClosingViewHolder(private var binding: DailyClosingItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: SupplierReport) {
        binding.data = Item
        binding.executePendingBindings()
    }
}