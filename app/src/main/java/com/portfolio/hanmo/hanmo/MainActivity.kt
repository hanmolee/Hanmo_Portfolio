package com.portfolio.hanmo.hanmo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.jakewharton.rxbinding2.view.clicks
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.Fragment.Fragment_Pager
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.view_toolbar_main.*
import android.view.animation.LinearInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.widget.textChanges
import com.portfolio.hanmo.hanmo.Adapter.TechListAdapter
import com.portfolio.hanmo.hanmo.Constants.Type
import com.portfolio.hanmo.hanmo.DataModel.SearchResult_Table
import com.portfolio.hanmo.hanmo.DataModel.TechStack
import com.portfolio.hanmo.hanmo.DataModel.TechStack_Table
import com.portfolio.hanmo.hanmo.Util.ResizeWidthAnimation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_techlist.*
import org.jetbrains.anko.toast


/**
 * Created by hanmo on 2018. 2. 3..
 */
class MainActivity : FragmentActivity() {

    companion object {
        var admin = 0
    }
    var count = 0
    private var isFirstBackClicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(list_search_results){
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }


        val test_result = RealmHelper.instance.queryAll(TechStack_Table::class.java)
        test_result.forEach {
            var ddd = it.search_result?.size
            var kkk = 2
        }

        val test_2 = RealmHelper.instance.test(TechStack_Table::class.java)
        test_2.forEach {
            var kk2 = it.tech_name
            var kkk3 = it.id
            var kk3 = it.search_result?.size
        }

        btn_search.clicks()
                .doOnNext {
                    et_search.visibility = View.VISIBLE
                    et_search.setOnEditorActionListener { v, actionId, event ->
                        return@setOnEditorActionListener when (actionId) {
                            EditorInfo.IME_ACTION_SEARCH -> {
                                when(et_search.length()){
                                    0 -> {
                                        toast("검색어를 한글자 이상 입력해주세요")
                                    }
                                    else -> {
                                        hideKeyboard()
                                    }
                                }
                                true
                            }
                            else -> { false }
                        }

                    }
                    btn_search.visibility = View.INVISIBLE
                    btn_close.visibility = View.VISIBLE

                    clickSearchButton()

                }.subscribe{
                    et_search.textChanges()
                            .subscribe {
                                when(et_search.length()){
                                    0 -> {
                                        clickSearchButton()
                                    }
                                    else -> {
                                        var result_list = ArrayList<TechStack>()
                                        val result = it.toString().trim()
                                        val results = RealmHelper.instance.queryResults(TechStack_Table::class.java, result)
                                        results.forEach{
                                            result_list.add(TechStack(it.id, it.tech_name!!))
                                        }
                                        with(list_search_results){
                                            visibility = View.VISIBLE
                                            adapter = TechListAdapter(result_list, Type.result)
                                        }
                                    }
                                }
                            }
                }

        btn_close.clicks()
                .subscribe{
                    invisibleView()
                }

        val run_count = RealmHelper.instance.queryFirst(Active_Count_Table::class.java)
        when{
            run_count != null -> {
                count = run_count.count!!
                RealmHelper.instance.updateActiveCount()
            }
        }

        val fragment_Pager = Fragment_Pager()
        replaceContentFragment(R.id.content_frame, fragment_Pager)

    }

    fun invisibleView() {
        et_search.visibility = View.INVISIBLE
        btn_search.visibility = View.VISIBLE
        btn_close.visibility = View.INVISIBLE
        list_search_results.visibility = View.INVISIBLE
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun clickSearchButton() {

        val search_result = ArrayList<TechStack>()
        val results = RealmHelper.instance.selectSearchResult(SearchResult_Table::class.java)

        when(results.size){
            0 -> {
                search_result.add(TechStack(-2, "검색 히스토리가 없습니다"))
            }
            else -> {

                when(results.size) {
                    0 -> {
                        search_result.add(TechStack(-1, "검색결과 모두 삭제"))
                    }
                    else -> {
                        results.forEach{
                            search_result.add(TechStack(it.id, it.result!!))
                        }
                        search_result.add(TechStack(-1, "검색결과 모두 삭제"))

                    }
                }
            }
        }
        with(list_search_results){
            visibility = View.VISIBLE
            adapter = TechListAdapter(search_result, Type.search_result)
        }
    }

    fun setTitle(title : String){
        txt_toolbar.text = title
    }

    fun replaceFragment(fragment: android.app.Fragment) {
        replaceContentFragment(R.id.content_frame, fragment)
    }

    fun Activity.replaceContentFragment(@IdRes frameId: Int, fragment: android.app.Fragment){
        fragmentManager.beginTransaction().replace(frameId, fragment).commit()
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            if (isFirstBackClicked) {
                Toast.makeText(this, "한번 더 누르면 종료", Toast.LENGTH_SHORT).show()
                isFirstBackClicked = false
                return
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}