package com.portfolio.hanmo.hanmo.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.portfolio.hanmo.hanmo.DataModel.SearchHistory
import com.portfolio.hanmo.hanmo.R

/**
 * Created by hanmo on 2018. 3. 7..
 */
class SearchHistoryAdapter(val item : ArrayList<SearchHistory>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchHistoryHolder(parent)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when(holder) {
            is SearchHistoryHolder -> holder.bindView(item[position])
        }
    }

    inner class SearchHistoryHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_techlist, parent, false)) {

        fun bindView(param: SearchHistory) {
            with(itemView){

            }

        }

    }

}