package com.portfolio.hanmo.hanmo.Fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.ifttt.sparklemotion.SparkleMotion
import com.ifttt.sparklemotion.SparkleViewPagerLayout
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.portfolio.hanmo.hanmo.Activity.DetailTechActivity
import com.portfolio.hanmo.hanmo.Adapter.TechListAdapter
import com.portfolio.hanmo.hanmo.Adapter.ViewPagerAdapter
import com.portfolio.hanmo.hanmo.Constants.RequestCodes
import com.portfolio.hanmo.hanmo.Constants.ResultCodes
import com.portfolio.hanmo.hanmo.Constants.Type
import com.portfolio.hanmo.hanmo.DataModel.SearchHistory
import com.portfolio.hanmo.hanmo.DataModel.SearchHistoryTable
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.DataModel.TechStackTable
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.fragment_pager.view.*
import kotlinx.android.synthetic.main.fragment_stackpage.view.*

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
            adapter = SparklePageAdapter(activity)
            setCurrentItem(1, true)
            setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    when(position) {
                        0 -> {  }
                        1 -> {  }
                        2 -> {  }
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

    private fun refreshFragment() {
        val ft = fragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }

    private class SparklePageAdapter(val activity: Activity) : ViewPagerAdapter() {

        private lateinit var inputManager: InputMethodManager
        var isSearchViewVisible: Boolean = false
        var type : Int = Type.LISTISNULL

        val onItemClickListener = object : TechListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val intent = DetailTechActivity.newIntent(activity, position)

                val techImage = view.findViewById<ImageView>(R.id.TechImage)
                val techNameHolder = view.findViewById<LinearLayout>(R.id.TechHolder)

                val imagePair = android.support.v4.util.Pair.create(techImage as View, "tImage")
                val holderPair = android.support.v4.util.Pair.create(techNameHolder as View, "tNameHolder")

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imagePair, holderPair)

                ActivityCompat.startActivity(activity, intent, options.toBundle())
            }

        }

        val onItemClickListener2 = object : TechListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val intent = DetailTechActivity.newIntent(activity, position)

                val techImage = view.findViewById<ImageView>(R.id.searchTechImage)
                val techNameHolder = view.findViewById<LinearLayout>(R.id.searchTechNameHolder)

                val imagePair = android.support.v4.util.Pair.create(techImage as View, "tImage")
                val holderPair = android.support.v4.util.Pair.create(techNameHolder as View, "tNameHolder")

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imagePair, holderPair)

                ActivityCompat.startActivity(activity, intent, options.toBundle())
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

            rootView.ly_search.visibility = View.INVISIBLE

            inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            initTechList(rootView)
            setSearchView(rootView)
            selectTechList(rootView)

            //rootView.et_search.textChanges().doOnNext {  }.subscribe {  }

            return rootView
        }

        private fun initTechList(rootView: View) {

            with(rootView.technical_stacklist) {
                val tech_list = ArrayList<TechStack>()
                getData(tech_list)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                val TechAdapter = TechListAdapter(tech_list, type)
                TechAdapter.setOnItemClickListener(onItemClickListener)
                adapter = TechAdapter
            }

        }

        private fun selectTechList(rootView: View) {

            val searchHistory = ArrayList<SearchHistory>()

            rootView.et_search.textChanges()
                    .doOnNext {
                        //검색 화면 보이기

                    }
                    .subscribe{
                        when(it.length) {
                            0 -> {
                                val searchResults = RealmHelper.instance.queryAll(SearchHistoryTable::class.java)
                                when(searchResults) {
                                    null -> {
                                        searchHistory.add(SearchHistory(-1, "검색결과가 없습니다.", 0, null))
                                    }
                                }
                                searchResults?.forEach {
                                    searchHistory.add(SearchHistory(it.id, it.history_name, it.history_search_time, it.techstack_table))
                                }
                                searchHistory.add(SearchHistory(-2, "검색결과 모두 지우기", 0, null))
                            }
                            else -> {
                                rootView.search_techlist_form.visibility = View.VISIBLE
                                with(rootView.search_techlist){
                                    val search_tech_list = ArrayList<TechStack>()
                                    val searchTechResults = RealmHelper.instance.searchTechlist(TechStackTable::class.java, it.toString())
                                    when(searchTechResults) {
                                        null -> {

                                        }
                                    }
                                    searchTechResults?.let {
                                        it.forEach {
                                            search_tech_list.add(TechStack(it.id, it.tech_name, it.tech_image))
                                        }
                                    }
                                    setHasFixedSize(true)
                                    layoutManager = LinearLayoutManager(context)
                                    val TechAdapter = TechListAdapter(search_tech_list, Type.SEARCHTECHLIST)
                                    TechAdapter.setOnItemClickListener(onItemClickListener2)
                                    adapter = TechAdapter
                                }

                            }
                        }

                    }
        }

        private fun setSearchView(rootView: View) {
            rootView.btn_search.clicks()
                    .doOnNext {
                        when(isSearchViewVisible) {
                            false -> {
                                revealSearchView(rootView.ly_search)
                                rootView.et_search.requestFocus()
                                inputManager.showSoftInput(rootView.et_search, InputMethodManager.SHOW_IMPLICIT)

                            }
                            true -> {
                                inputManager.hideSoftInputFromWindow(rootView.et_search.windowToken, 0)
                                hideEditText(rootView.ly_search)

                            }
                        }
                    }
                    .subscribe {
                        when(isSearchViewVisible) {
                            false -> {
                                isSearchViewVisible = true

                                rootView.btn_search.setImageResource(R.drawable.ic_confirm)
                                val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
                                alphaAnimation.duration = 200
                                rootView.btn_search.startAnimation(alphaAnimation)

                            }
                            true -> {
                                isSearchViewVisible = false

                                rootView.btn_search.setImageResource(R.drawable.ic_search)
                                val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
                                alphaAnimation.duration = 200
                                rootView.btn_search.startAnimation(alphaAnimation)

                            }
                        }

                    }
        }

        private fun hideEditText(ly_search: LinearLayout) {
            val cx = ly_search.right - 30
            val cy = ly_search.bottom - 60
            val initialRadius = ly_search.width
            val anim = ViewAnimationUtils.createCircularReveal(ly_search, cx, cy, initialRadius.toFloat(), 0f)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    ly_search.visibility = View.INVISIBLE
                }
            })
            anim.start()
        }

        private fun revealSearchView(ly_search: LinearLayout) {
            val cx = ly_search.right - 30
            val cy = ly_search.bottom - 60
            val finalRadius = Math.max(ly_search.width, ly_search.height)
            val anim = ViewAnimationUtils.createCircularReveal(ly_search, cx, cy, 0F, finalRadius.toFloat())
            anim.duration = 5000
            ly_search.visibility = View.VISIBLE
            anim.start()
        }

        private fun getData(tech_list: ArrayList<TechStack>) {
            var result = RealmHelper.instance.queryAll(TechStackTable::class.java)
            when(result){
                null -> {
                    type = Type.LISTISNULL
                    Log.d("기술스택", "tech stack list is null!")
                }
                else -> {
                    type = Type.ADMIN
                    result.forEach { tech_list.add(TechStack(it.id, it.tech_name, it.tech_image)) }

                }
            }
        }

        private fun aboutViewPage(container: ViewGroup): View? {
            return LayoutInflater.from(container.context).inflate(R.layout.fragment_aboutpage, container, false)
        }

        private fun firstViewPage(container: ViewGroup): View? {
            val rootView = LayoutInflater.from(container.context).inflate(R.layout.fragment_firstview, container, false)

            return rootView
        }

    }

}