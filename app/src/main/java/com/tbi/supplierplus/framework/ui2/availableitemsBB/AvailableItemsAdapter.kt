package com.tbi.supplierplus.framework.ui2.availableitemsBB

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.AvailableItems

import com.tbi.supplierplus.databinding.AvailableTemsBinding
import com.tbi.supplierplus.databinding.ItemsSettelmentItemBinding
import kotlinx.android.synthetic.main.items_settelment_item.view.*

class AvailableItemsAdapter
     (val onClickListener: OnAvailableItemsClickListener)
    : ListAdapter<AvailableItems, GetItemsSettelmentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetItemsSettelmentViewHolder {
        val binding = DataBindingUtil.inflate<AvailableTemsBinding>(
            LayoutInflater.from(parent.context), R.layout.available_tems, parent, false
        )

        return GetItemsSettelmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GetItemsSettelmentViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<AvailableItems>() {
        override fun areItemsTheSame(oldItem: AvailableItems, newItem: AvailableItems): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AvailableItems, newItem: AvailableItems): Boolean {
            return oldItem.ItemID== newItem.ItemID
        }
    }


}

class GetItemsSettelmentViewHolder(private var binding: AvailableTemsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: AvailableItems) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

class OnAvailableItemsClickListener(val clickListener: (itemsSettlement: AvailableItems) -> Unit) {
    fun onClick(itemsSettlement: AvailableItems) = clickListener(itemsSettlement)
}