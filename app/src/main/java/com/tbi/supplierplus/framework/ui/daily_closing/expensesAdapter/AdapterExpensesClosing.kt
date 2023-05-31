package com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.CurrentMortg3Model
import com.tbi.supplierplus.business.pojo.closing.SupplierReport
import com.tbi.supplierplus.databinding.DailyClosingExpensesRowBinding
import com.tbi.supplierplus.databinding.DailyClosingItemRowBinding

class AdapterExpensesClosing : ListAdapter<CurrentMortg3Model, DailyClosingViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyClosingViewHolder {
        val binding = DataBindingUtil.inflate<DailyClosingExpensesRowBinding>(
            LayoutInflater.from(parent.context), R.layout.daily_closing_expenses_row, parent, false
        )
        return DailyClosingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyClosingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        //  getItem(position).


    }

    companion object DiffCallback : DiffUtil.ItemCallback<CurrentMortg3Model>() {
        override fun areItemsTheSame(oldItem: CurrentMortg3Model, newItem: CurrentMortg3Model): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CurrentMortg3Model, newItem: CurrentMortg3Model): Boolean {
            return oldItem.Amount == newItem.Amount
        }
    }




}
class DailyClosingViewHolder(private var binding: DailyClosingExpensesRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: CurrentMortg3Model) {
        binding.data = Item
        binding.executePendingBindings()
    }
}