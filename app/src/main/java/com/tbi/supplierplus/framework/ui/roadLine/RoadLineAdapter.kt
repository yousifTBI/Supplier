package com.tbi.supplierplus.framework.ui.roadLine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.RoadMapModel
import com.tbi.supplierplus.databinding.RoadRowBinding

class RoadLineAdapter () : ListAdapter<RoadMapModel, RoadLineAdapterViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadLineAdapterViewHolder {
        val binding = DataBindingUtil.inflate<RoadRowBinding>(
            LayoutInflater.from(parent.context), R.layout.road_row, parent, false
        )

        return RoadLineAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoadLineAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

//        holder.itemView.setOnClickListener {
//            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
//        }


    }


    companion object DiffCallback : DiffUtil.ItemCallback<RoadMapModel>() {
        override fun areItemsTheSame(oldItem: RoadMapModel, newItem: RoadMapModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RoadMapModel, newItem: RoadMapModel): Boolean {
            return oldItem.BranchName == newItem.BranchName
        }

    }


}

class RoadLineAdapterViewHolder(private var binding: RoadRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: RoadMapModel) {
        binding.data = Item
        val textColor = if (Item.SalesStatus == 0) {
            ContextCompat.getColor(binding.root.context, R.color.red)
        } else {
            ContextCompat.getColor(binding.root.context, R.color.green)
        }
        // Apply the text color to the TextView in your road_row layout
        binding.textView49.setTextColor(textColor)
        binding.executePendingBindings()
    }

}

