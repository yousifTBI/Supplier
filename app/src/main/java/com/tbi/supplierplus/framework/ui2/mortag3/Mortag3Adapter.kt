package com.tbi.supplierplus.framework.ui2.mortag3

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.Datum
import com.tbi.supplierplus.business.pojo.reports.Sales
import com.tbi.supplierplus.databinding.BillRowBinding
import com.tbi.supplierplus.framework.ui.daily_closing.OnDebitClickListeners

class Mortag3Adapter (val onClickListener: OnDebitBillClickListeners):
    ListAdapter<Sales, BillViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val binding = DataBindingUtil.inflate<BillRowBinding>(
            LayoutInflater.from(parent.context), R.layout.bill_row, parent, false
        )
        return BillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        getItem(position)?.let {
            Log.d("getCurriygentMoI1",it.Items+" ")
            holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Sales>() {
        override fun areItemsTheSame(oldItem: Sales, newItem: Sales): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Sales, newItem: Sales): Boolean {
            return oldItem.Items == newItem.Items
        }
    }


}

class BillViewHolder(private var binding: BillRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Sales) {
        binding.data = Item
        binding.executePendingBindings()
    }
}
class OnDebitBillClickListeners(val clickListener: (CustomerDebit: Sales) -> Unit) {
    fun onClick(CustomerDebit: Sales) = clickListener(CustomerDebit)
}
