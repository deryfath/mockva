package com.project.daksatest.module.history

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.daksatest.R
import com.project.daksatest.extension.inflate
import com.project.daksatest.model.DataTransaction

class HistoryAdapter (private val items:List<DataTransaction>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val inflatedView= parent.inflate(R.layout.item_history,false)
        return HistoryAdapter.ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val view=holder.itemView
        val data=items[position]

        view.let {

            it.visibility= View.VISIBLE
            (it.findViewById(R.id.tv_date_history) as TextView).text=data.transactionTimestamp
            (it.findViewById(R.id.tv_amount_history) as TextView).text=data.amount.toString()
            (it.findViewById(R.id.tv_ref_history) as TextView).text=data.clientRef
            (it.findViewById(R.id.tv_dest_history) as TextView).text=data.accountDstId

        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

    }
}