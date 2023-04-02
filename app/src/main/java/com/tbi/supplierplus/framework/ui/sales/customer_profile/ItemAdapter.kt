package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.databinding.ItemRowBinding
 // FOR SPINNERS
class ItemsAdapter(val context: Context, var dataSource: List<Item>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_row, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.name.text = dataSource.get(position).name

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val name: TextView

        init {
            name = row?.findViewById(R.id.itemNameTxt) as TextView
        }
    }

}