package com.tbi.supplierplus.framework.ui.purchase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R

import com.tbi.supplierplus.business.pojo.puarchase.Puarchase
import com.tbi.supplierplus.databinding.PurchRowBinding
import com.tbi.supplierplus.databinding.PurchasedItemRowBinding

class PurchasedItemsAdapter:
    ListAdapter<Puarchase, PurchasedViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedViewHolder {
        val binding = DataBindingUtil.inflate<PurchRowBinding>(
            LayoutInflater.from(parent.context), R.layout.purch_row, parent, false
        )
        return PurchasedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchasedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Puarchase>() {
        override fun areItemsTheSame(oldItem: Puarchase, newItem: Puarchase): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Puarchase, newItem: Puarchase): Boolean {
            return oldItem.Item_ID == newItem.Item_ID
        }
    }


}
//purch_row
class PurchasedViewHolder(private var binding: PurchRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Puarchase) {
        binding.data = item
        binding.executePendingBindings()
    }
}