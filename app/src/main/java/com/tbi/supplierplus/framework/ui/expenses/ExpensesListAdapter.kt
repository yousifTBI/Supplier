package com.tbi.supplierplus.framework.ui.expenses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.expenses.Expensese

class ExpensesListAdapter(val context: Context, var dataSource: List<Expensese>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.expenses_item_row, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.name.text = dataSource[position].Expensetype
        vh.amount.text = dataSource[position].Amount.toString()

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
        val amount: TextView

        init {
            name = row?.findViewById(R.id.itemName) as TextView
           // name
            amount= row?.findViewById(R.id.itemAmount) as TextView
        }
    }

}