package com.portfolio.hanmo.hanmo.Fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ifttt.sparklemotion.SparkleMotion
import com.ifttt.sparklemotion.SparkleViewPagerLayout
import com.portfolio.hanmo.hanmo.Adapter.TechListAdapter
import com.portfolio.hanmo.hanmo.Adapter.ViewPagerAdapter
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.DataModel.TechStack_Table
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.fragment_firstview.view.*
import kotlinx.android.synthetic.main.fragment_pager.view.*
import kotlinx.android.synthetic.main.fragment_stackpage.view.*

/**
 * Created by hanmo on 2018. 2. 3..
 */
class Fragment_Pager : Fragment() {

    private var sparkleViewPagerLayout: SparkleViewPagerLayout? = null
    private var sparkleMotion: SparkleMotion? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = inflater!!.inflate(R.layout.fragment_pager, container, false)

        sparkleViewPagerLayout = rootView.pager_layout
        sparkleMotion = SparkleMotion.with(sparkleViewPagerLayout!!)

        sparkleViewPagerLayout!!.viewPager.adapter = SparklePageAdapter()
        sparkleViewPagerLayout!!.viewPager.setCurrentItem(1, true)
        sparkleViewPagerLayout!!.viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> { onBackButtonPressed(rootView) }
                    2 -> { onBackButtonPressed(rootView) }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        return rootView
    }

    fun onBackButtonPressed(rootView: View) {
        rootView.isFocusableInTouchMode = true
        rootView.requestFocus()
        rootView.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                sparkleViewPagerLayout!!.viewPager.setCurrentItem(1, true)
            }
            false
        })
    }

    private class SparklePageAdapter : ViewPagerAdapter() {

        var type : Int = 0

        override fun getView(position: Int, container: ViewGroup): View? {
            when(position) {
                0 -> { return aboutViewPage(container) }
                1 -> { return firstViewPage(container) }
                2 -> { return stackViewPage(container) }

            }

            return null
        }

        override fun getCount(): Int {
            return 3
        }

        private fun stackViewPage(container: ViewGroup): View? {
            val rootView = LayoutInflater.from(container.context).inflate(R.layout.fragment_stackpage, container, false)

            with(rootView.technical_stacklist) {
                val tech_list = ArrayList<TechStack>()
                getData(tech_list)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = TechListAdapter(tech_list, type)

            }

            return rootView
        }

        private fun getData(tech_list: ArrayList<TechStack>) {
            var result = RealmHelper.instance.queryAll(TechStack_Table::class.java)
            when(result){
                null -> {
                    type = 0
                    Log.d("기술스택", "tech stack list is null!")
                }
                else -> {
                    type = 6
                    result.forEach { tech_list.add(TechStack(it.tech_name!!)) }
                }
            }
        }

        private fun aboutViewPage(container: ViewGroup): View? {
            return LayoutInflater.from(container.context).inflate(R.layout.fragment_aboutpage, container, false)
        }

        private fun firstViewPage(container: ViewGroup): View? {
            val rootView = LayoutInflater.from(container.context).inflate(R.layout.fragment_firstview, container, false)

            val run_count = RealmHelper.instance.queryFirst(Active_Count_Table::class.java)
            when{
                run_count != null -> {
                    var count = run_count.count!!
                    rootView.count.text = "count : " + count.toString()
                }
            }

            return rootView
        }



    }

}