package com.portfolio.hanmo.hanmo.Adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.portfolio.hanmo.hanmo.Constants.Type
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.Fragment.PushFragment
import com.portfolio.hanmo.hanmo.MainActivity
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_search_result.view.*
import kotlinx.android.synthetic.main.item_techlist.view.*
import java.util.ArrayList

/**
 * Created by hanmo on 2018. 2. 4..
 */
class TechListAdapter(val items : ArrayList<TechStack>, val type : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            Type.not_admin -> return TechListHolder(parent!!)
            Type.admin -> return TechListHolder_admin(parent!!)
            Type.result -> return TechListHolder_results(parent!!)
            Type.search_result -> return TechListHolder_search_result(parent!!)
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
            is TechListHolder_admin -> holder.bindView(items[position])
            is TechListHolder_results -> holder.bindView(items[position])
            is TechListHolder_search_result -> holder.bindView(items[position])
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

    inner class TechListHolder_search_result(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)){

        fun bindView(item: TechStack) {
            with(itemView){
                when(item.id){
                    -1, -2 -> {
                        with(txt_search_result){
                            text = item.name
                            gravity = Gravity.CENTER
                            setTextColor(Color.parseColor("#B9675B"))
                        }
                    }
                    else -> {
                        txt_search_result.text = item.name
                    }
                }

                itemView.setOnClickListener {

                    //RealmHelper.instance.testDelete(item.name)
                    //RealmHelper.instance.updateSearchResult(item.name)

                    when(position){
                        0 -> {
                            /*var stack01 = PushFragment()
                            (context as MainActivity).replaceFragment(stack01)
                            (context as MainActivity).list_search_results.visibility = View.INVISIBLE*/
                            RealmHelper.instance.testupdate(item.name)
                        }
                        1 -> {
                            RealmHelper.instance.testDelete(item.name)
                        }
                    }
                }
            }

        }
    }

    inner class TechListHolder_results(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_techlist, parent, false)){

        fun bindView(item: TechStack) {
            with(itemView){
                tech_name_txt.visibility = View.INVISIBLE
                btn_list_minus.visibility = View.INVISIBLE
                tech_result.visibility = View.VISIBLE
                tech_result.text = item.name
                itemView.setOnClickListener {

                    (context as MainActivity).hideKeyboard()

                    RealmHelper.instance.insertSearchResult(item.name)

                    when(item.id){
                        0 -> {
                            var stack01 = PushFragment()
                            (context as MainActivity).replaceFragment(stack01)
                            (context as MainActivity).list_search_results.visibility = View.INVISIBLE
                        }
                    }
                }
            }

        }
    }

    inner class TechListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_techlist, parent, false)), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(item: TechStack) {
            with(itemView){
                tech_name_txt.text = item.name
            }

        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(itemView, adapterPosition)
        }
    }

    inner class TechListHolder_admin(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_techlist, parent, false)){

        fun bindView(item: TechStack) {
            with(itemView){

                with(tech_name_txt){
                    text = item.name
                    setOnClickListener{
                        when(item.id){
                            -1 -> {
                                //(context as Fragment_Pager).startActivityForResult()
                            }
                            0 -> {
                                var stack01 = PushFragment()
                                (context as MainActivity).replaceFragment(stack01)
                            }
                        }
                    }
                }

                with(btn_list_minus) {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val stack_id = item.id
                        RealmHelper.instance.deleteStackList(stack_id)
                        items.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }

                when(item.id){
                    -1 -> {
                        btn_list_minus.visibility = View.INVISIBLE
                        tech_name_txt.setTextColor(Color.parseColor("#8E75A7"))
                    }
                }

            }

        }
    }
}