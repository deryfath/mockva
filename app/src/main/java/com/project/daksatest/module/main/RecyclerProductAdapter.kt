package com.project.daksatest.module

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.project.daksatest.R
import com.project.daksatest.activity.main.MainPresenter
import com.project.daksatest.extension.inflate
import com.project.daksatest.model.ProductResponse

class RecyclerProductAdapter (private val activity: MainActivity, private val presenter: MainPresenter, private val items:List<ProductResponse>) : RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerProductAdapter.ViewHolder {
        val inflatedView= parent!!.inflate(R.layout.item_product,false)
        return RecyclerProductAdapter.ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerProductAdapter.ViewHolder, position: Int) {
        val view=holder?.itemView
        val data=items[position]

        view?.let {

            it.visibility= View.VISIBLE
            Glide.with(it.context).load(data.image).into(it.findViewById(R.id.iv_image_product) as ImageView)
            (it.findViewById(R.id.tv_name_product) as TextView).text=data.name
            (it.findViewById(R.id.tv_price_product) as TextView).text=data.price.toString()

        }


    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

    }
}