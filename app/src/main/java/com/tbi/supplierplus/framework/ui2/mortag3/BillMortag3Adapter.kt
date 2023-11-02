package com.tbi.supplierplus.framework.ui2.mortag3

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.reports.Invoices
import com.tbi.supplierplus.databinding.RowStatementBinding


class BillMortag3Adapter (val onClickListener: OnDebitBillClickListeners1):
    ListAdapter<Invoices, BillMortag3ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillMortag3ViewHolder {
        val binding = DataBindingUtil.inflate<RowStatementBinding>(
            LayoutInflater.from(parent.context), R.layout.row_statement, parent, false
        )
        return BillMortag3ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillMortag3ViewHolder, position: Int) {
        getItem(position)?.let {
            Log.d("getCurriygentMoI1",it.BillNo.toString()+" ")
            holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
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

class BillMortag3ViewHolder(private var binding: RowStatementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Invoices) {
        binding.data = Item
        binding.executePendingBindings()
    }
}
class OnDebitBillClickListeners1(val clickListener: (CustomerDebit: Invoices) -> Unit) {
    fun onClick(CustomerDebit: Invoices) = clickListener(CustomerDebit)
}
