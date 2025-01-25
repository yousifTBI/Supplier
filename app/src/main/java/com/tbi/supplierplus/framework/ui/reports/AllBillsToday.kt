package com.tbi.supplierplus.framework.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.reports.Invoices
import com.tbi.supplierplus.databinding.TodayBillRowBinding

class AllBillsToday(val onBillClickListener: OnBillClickListener) :
    ListAdapter<Invoices, AllBillsTodayViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBillsTodayViewHolder {
        val binding = DataBindingUtil.inflate<TodayBillRowBinding>(
            LayoutInflater.from(parent.context), R.layout.today_bill_row, parent, false
        )
        return AllBillsTodayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllBillsTodayViewHolder, position: Int) {
        val sequentialNumber = position + 1
        getItem(position)?.let { item ->
            holder.bind(item, sequentialNumber)
            holder.itemView.setOnClickListener {
                onBillClickListener.onClick(item)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Invoices>() {
        override fun areItemsTheSame(oldItem: Invoices, newItem: Invoices): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Invoices, newItem: Invoices): Boolean {
            return oldItem.BillNo == newItem.BillNo
        }
    }
}

class AllBillsTodayViewHolder(private val binding: TodayBillRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Invoices, sequentialNumber: Int) {
        binding.data = item
        binding.sequentialNumberTextView.text = sequentialNumber.toString()
        binding.executePendingBindings()
    }
}

class OnBillClickListenerr(val clickListener: (statement: Invoices) -> Unit) {
    fun onClick(statement: Invoices) = clickListener(statement)
}
