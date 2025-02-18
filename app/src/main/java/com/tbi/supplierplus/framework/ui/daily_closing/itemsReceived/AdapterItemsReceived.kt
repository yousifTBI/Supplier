package com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.PenddingModel
import com.tbi.supplierplus.databinding.DailyClosingItemRowBinding

class AdapterItemsReceived : ListAdapter<PenddingModel, DailyClosingViewHolder>(DiffCallback) {

//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyClosingViewHolder {
        val binding = DataBindingUtil.inflate<DailyClosingItemRowBinding>(
            LayoutInflater.from(parent.context), R.layout.daily_closing_item_row, parent, false
        )


//        binding.textView72.setOnClickListener {
//
//                var position =
//        }
        return DailyClosingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyClosingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        //  getItem(position).


    }

    companion object DiffCallback : DiffUtil.ItemCallback<PenddingModel>() {
        override fun areItemsTheSame(oldItem: PenddingModel, newItem: PenddingModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PenddingModel, newItem: PenddingModel): Boolean {
            return oldItem.total_amount == newItem.total_amount
        }
    }




}
class DailyClosingViewHolder(private var binding: DailyClosingItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: PenddingModel) {
        binding.data = Item
        binding.executePendingBindings()
    }
}