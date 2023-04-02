package com.tbi.supplierplus.newTest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill

class testAdepterR (val context: Context, var dataSource: List<SaleingBill>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.test_bill_row, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        vh.name.text = dataSource[position].Items+""
        vh.Discont.text = dataSource[position].Discount
        vh. salary.text = dataSource[position].UnitPrice
        vh.Total.text = dataSource[position].TotalPrice
        vh.size.text = dataSource[position].size


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
        val salary: TextView
        val Total  : TextView
        val size  : TextView
        val Discont : TextView

        init {
            salary=row?.findViewById(R.id.textView53)as TextView
            name = row?.findViewById(R.id.textView37) as TextView
            // name
            amount= row?.findViewById(R.id.textView52) as TextView
           Total= row?.findViewById(R.id.textView54) as TextView
           size= row?.findViewById(R.id.textView56) as TextView
            Discont= row?.findViewById(R.id.textView55) as TextView
        }
    }

}