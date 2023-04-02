
package com.tbi.supplierplus.framework.ui.return_items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.returns.ReturnItems
import com.tbi.supplierplus.databinding.ReturnsItemsSettelmentBinding

// for recyclers
class GetReturnItemsSettelmentAdapter: ListAdapter<ReturnItems, GetReturnItemsSettelmentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetReturnItemsSettelmentViewHolder {
        val binding = DataBindingUtil.inflate<ReturnsItemsSettelmentBinding>(
            LayoutInflater.from(parent.context), R.layout.returns_items_settelment, parent, false
        )
        return GetReturnItemsSettelmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GetReturnItemsSettelmentViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ReturnItems>() {
        override fun areItemsTheSame(oldItem: ReturnItems, newItem: ReturnItems): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ReturnItems, newItem: ReturnItems): Boolean {
            return oldItem.Item_ID== newItem.Item_ID
        }
    }


}

class GetReturnItemsSettelmentViewHolder(private var binding: ReturnsItemsSettelmentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: ReturnItems) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

