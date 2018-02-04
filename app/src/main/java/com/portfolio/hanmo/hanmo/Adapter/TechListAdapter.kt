package com.portfolio.hanmo.hanmo.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.R
import kotlinx.android.synthetic.main.item_techlist.view.*
import java.util.ArrayList

/**
 * Created by hanmo on 2018. 2. 4..
 */
class TechListAdapter(val items : ArrayList<TechStack>, val type : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val haslist = 6
    val notlist = 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            haslist -> return TechListHolder(parent!!)
            //notlist -> {  }
        }
        throw IllegalArgumentException()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when(holder) {
            is TechListHolder -> holder.bindView(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    inner class TechListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_techlist, parent, false)){

        fun bindView(item: TechStack) {
            with(itemView){
                tech_name_txt.text = item.name
            }

        }
    }

}