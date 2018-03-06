package com.portfolio.hanmo.hanmo.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.TextView
import com.ifttt.sparklemotion.SparkleMotion
import com.ifttt.sparklemotion.SparkleViewPagerLayout
import com.portfolio.hanmo.hanmo.Activity.AddTechStackActivity
import com.portfolio.hanmo.hanmo.Activity.AdminActivity
import com.portfolio.hanmo.hanmo.Activity.DetailTechStackActivity
import com.portfolio.hanmo.hanmo.Adapter.TechListAdapter
import com.portfolio.hanmo.hanmo.Adapter.ViewPagerAdapter
import com.portfolio.hanmo.hanmo.Constants.RequestCodes
import com.portfolio.hanmo.hanmo.Constants.ResultCodes
import com.portfolio.hanmo.hanmo.Constants.Type
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.DataModel.Admin_Table
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.DataModel.TechStack_Table
import com.portfolio.hanmo.hanmo.MainActivity
import com.portfolio.hanmo.hanmo.MainActivity.Companion.admin
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.fragment_firstview.view.*
import kotlinx.android.synthetic.main.fragment_pager.view.*
import kotlinx.android.synthetic.main.fragment_stackpage.view.*
import kotlinx.android.synthetic.main.item_techlist.view.*
import kotlinx.android.synthetic.main.view_toolbar_main.*
import kotlinx.android.synthetic.main.view_toolbar_main.view.*
import org.jetbrains.anko.toast

/**
 * Created by hanmo on 2018. 2. 3..
 */
class Fragment_Pager : BaseFragment() {

    private var sparkleViewPagerLayout: SparkleViewPagerLayout? = null
    private var sparkleMotion: SparkleMotion? = null
    val TAG = "메인 페이지"


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = inflater!!.inflate(R.layout.fragment_pager, container, false)
        sparkleViewPagerLayout = rootView.pager_layout
        sparkleMotion = SparkleMotion.with(sparkleViewPagerLayout!!)

        setTitle("Welcome Page")

        with(sparkleViewPagerLayout!!.viewPager){
            adapter = SparklePageAdapter(this@Fragment_Pager)
            setCurrentItem(1, true)
            setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    when(position) {
                        0 -> {
                            setTitle("About Page")
                            onBackButtonPressed(rootView)
                        }
                        1 -> { setTitle("Welcome Page") }
                        2 -> {
                            setTitle("Tech Stack Page")
                            onBackButtonPressed(rootView)
                        }
                    }
                }
                override fun onPageScrollStateChanged(state: Int) {}
            })
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            RequestCodes.ADMIN -> {
                when(resultCode){
                    ResultCodes.RESET -> {
                        //toast("성공")
                        refreshFragment()
                    }
                    else -> {
                        //toast("실패")
                        refreshFragment()
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun refreshFragment() {
        val ft = fragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }

    private class SparklePageAdapter(thisContext: Fragment_Pager) : ViewPagerAdapter() {

        var _thisContext = thisContext

        var type : Int = Type.list_is_null

        private val onItemClickListener = object : TechListAdapter.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onItemClick(view: View, position: Int) {

                val intent = DetailTechStackActivity.newIntent(_thisContext.context, position)

                val techNameHolder = view.findViewById<TextView>(R.id.tech_name_txt)

                val holderPair = android.support.v4.util.Pair.create(techNameHolder as View, "techName")

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(_thisContext.activity, holderPair)

                ActivityCompat.startActivity(_thisContext.context, intent, options.toBundle())
            }

        }

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
                val TechAdapter = TechListAdapter(tech_list, type)
                TechAdapter.setOnItemClickListener(onItemClickListener)
                adapter = TechAdapter

            }

            return rootView
        }

        private fun getData(tech_list: ArrayList<TechStack>) {
            var result = RealmHelper.instance.queryAll(TechStack_Table::class.java)
            when(result){
                null -> {
                    type = Type.list_is_null
                    Log.d("기술스택", "tech stack list is null!")
                }
                else -> {
                    result.forEach { tech_list.add(TechStack(it.id, it.tech_name!!)) }

                    when(admin){
                        1 -> {
                            type = Type.admin
                            tech_list.add(TechStack(-1,
                                _thisContext.resources.getString(R.string.item_add)))
                        }
                        else -> {
                            type = Type.not_admin
                            Log.d(_thisContext.TAG, "관리자 아님")
                        }
                    }
                }
            }
        }

        private fun aboutViewPage(container: ViewGroup): View? {
            return LayoutInflater.from(container.context).inflate(R.layout.fragment_aboutpage, container, false)
        }

        private fun firstViewPage(container: ViewGroup): View? {
            val rootView = LayoutInflater.from(container.context).inflate(R.layout.fragment_firstview, container, false)
            when(admin) {
                0 -> {
                    rootView.count.text = "count : nullllllll "
                }
                1 -> {
                    val run_count = RealmHelper.instance.testQuery(Admin_Table::class.java)
                    when(run_count) {
                        null -> {

                        }
                        else -> {
                            run_count.count?.forEach {
                                rootView.count.text = "count : " + it.count.toString()
                            }
                        }

                    }
                }
            }


            with(rootView.btn_admin_login){
                setOnClickListener {
                    val admin_intent = Intent(context, AdminActivity::class.java)
                    _thisContext.startActivityForResult(admin_intent, RequestCodes.ADMIN)
                }
            }

            return rootView
        }

    }

}