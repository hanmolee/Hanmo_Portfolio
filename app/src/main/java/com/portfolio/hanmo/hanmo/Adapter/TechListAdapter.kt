package com.portfolio.hanmo.hanmo.Adapter

import android.graphics.BitmapFactory
import android.media.Image
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.portfolio.hanmo.hanmo.Constants.Type
import com.portfolio.hanmo.hanmo.DataModel.TechImage
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_techlist.view.*
import kotlinx.android.synthetic.main.item_techlist.view.*
import org.jetbrains.anko.find
import java.util.ArrayList

/**
 * Created by hanmo on 2018. 2. 4..
 */
class TechListAdapter(val items : ArrayList<TechStack>, val type : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            Type.ADMIN -> return TechListHolder_admin(parent)
            Type.SEARCHTECHLIST -> return SearchTechListHolder(parent)
            //notlist -> {  }
        }
        throw IllegalArgumentException()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when(holder) {
            is TechListHolder_admin -> holder.bindView(items[position])
            is SearchTechListHolder -> holder.bindView(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    fun setOnItemClickListener(itemClickListener : OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view : View, position: Int)
    }

    inner class TechListHolder_admin(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_techlist, parent, false)), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(item: TechStack) {
            with(itemView){
                itemView.TechName.text = item.name
                val image_id = com.portfolio.hanmo.hanmo.DataModel.TechImage.techList()[adapterPosition]
                Picasso.with(context).load(image_id).into(itemView.TechImage)

                val photo = BitmapFactory.decodeResource(context.resources, image_id)
                Palette.from(photo).generate{
                    val bgColor = it.getMutedColor(ContextCompat.getColor(context, android.R.color.black))
                    itemView.TechNameHolder.setBackgroundColor(bgColor)
                }

            }
        }

            override fun onClick(v: View?) {
            itemClickListener.onItemClick(itemView, adapterPosition)
        }
    }

    inner class SearchTechListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_techlist, parent, false)), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(item: TechStack) {
            with(itemView){
                itemView.txt_search_techlist.text = item.name
                val image_id = com.portfolio.hanmo.hanmo.DataModel.TechImage.techList()[adapterPosition]
                Picasso.with(context).load(image_id).into(itemView.searchTechImage)
            }
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(itemView, adapterPosition)
        }
    }
}

