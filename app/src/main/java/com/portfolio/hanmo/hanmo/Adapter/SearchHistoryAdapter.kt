package com.portfolio.hanmo.hanmo.Adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.portfolio.hanmo.hanmo.Activity.DetailTechActivity
import com.portfolio.hanmo.hanmo.Constants.Type
import com.portfolio.hanmo.hanmo.DataModel.SearchHistory
import com.portfolio.hanmo.hanmo.DataModel.SearchHistoryTable
import com.portfolio.hanmo.hanmo.MainActivity
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_history.view.*

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
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_history, parent, false)), View.OnClickListener {

        fun bindView(item: SearchHistory) {
            itemView.setOnClickListener(this)
            with(itemView){
                itemView.txt_search_history.text = item.search_history
                when(item.id) {
                    Type.DELETEALL -> {
                        itemView.txt_search_history.setTextColor(Color.DKGRAY)
                    }
                }
                val image_id = com.portfolio.hanmo.hanmo.DataModel.TechImage.techList()[adapterPosition]
                Picasso.with(context).load(image_id).into(itemView.searchHistoryImage)
            }

        }

        override fun onClick(v: View?) {
            when(v) {
                itemView -> {
                    //히스토리 아이템 클릭 시 디테일 뷰 연동
                }
            }
        }

    }

}