package com.portfolio.hanmo.hanmo.Fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ifttt.sparklemotion.SparkleMotion
import com.ifttt.sparklemotion.SparkleViewPagerLayout
import com.portfolio.hanmo.hanmo.Adapter.ViewPagerAdapter
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.fragment_firstview.view.*
import kotlinx.android.synthetic.main.fragment_pager.view.*

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

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        return rootView
    }


    private class SparklePageAdapter : ViewPagerAdapter() {
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
            return LayoutInflater.from(container.context).inflate(R.layout.fragment_stackpage, container, false)
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