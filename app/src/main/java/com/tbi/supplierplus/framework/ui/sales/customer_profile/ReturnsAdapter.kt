package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.pojo.reports.Returns
import com.tbi.supplierplus.databinding.BillRowBinding
import com.tbi.supplierplus.databinding.ReturnsRowBinding
import com.tbi.supplierplus.framework.ui.sales.customers.OnClickListener
// for recyclers
class ReturnsAdapter:
    ListAdapter<Returns, ReturnsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnsViewHolder {
        val binding = DataBindingUtil.inflate<ReturnsRowBinding>(
            LayoutInflater.from(parent.context), R.layout.returns_row, parent, false
        )
        return ReturnsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReturnsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Returns>() {
        override fun areItemsTheSame(oldItem: Returns, newItem: Returns): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Returns, newItem: Returns): Boolean {
            return oldItem.ItemName == newItem.ItemName
        }
    }


}

class ReturnsViewHolder(private var binding: ReturnsRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Returns) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

