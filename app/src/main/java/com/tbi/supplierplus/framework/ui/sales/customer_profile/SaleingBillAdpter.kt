package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.databinding.ReturnsRowBinding
import com.tbi.supplierplus.databinding.SaleingrowBinding
import kotlinx.android.synthetic.main.saleingrow.view.*

class SaleingBillAdpter
// (private val itemClickListener:  ItemClickListenerCounter)
    :
    ListAdapter<SaleingBill, SalingViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalingViewHolder {
        val binding = DataBindingUtil.inflate<SaleingrowBinding>(
            LayoutInflater.from(parent.context), R.layout.saleingrow, parent, false
        )
        return SalingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SalingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        // holder.itemView.textView52.
        // holder.itemView.buttonup.setOnClickListener {
        //    // x++
        //    // Log.d("buttonup",x.toString())
        //     itemClickListener.add(, position)
        // }
        //      holder.itemView.buttonD.setOnClickListener {

        //        val x =getItem(position).NumberOfUnits
        //         var s=x.toInt()
        //          s++


        //         holder.itemView.textView52.setText(s.toString())
        //          Log.d("buttonup", s.toString() )
        //      // val a=  x.toString()
        //      //    var xx=a.toInt()+1
        //      //   // x++
        //      //    it.textView52.setText(xx)
        //     // val x= holder.itemView.textView52.text


        //         // x--
        //        //  Log.d("buttonup",it.toString()+""+position.toString())
        //        //  itemClickListener.add(it, position)

        //      }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SaleingBill>() {
        override fun areItemsTheSame(oldItem: SaleingBill, newItem: SaleingBill): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SaleingBill, newItem: SaleingBill): Boolean {
            return oldItem.ItemID == newItem.ItemID
        }
    }


}

class SalingViewHolder(private var binding: SaleingrowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: SaleingBill) {
        binding.data = Item
        binding.executePendingBindings()
    }

}

