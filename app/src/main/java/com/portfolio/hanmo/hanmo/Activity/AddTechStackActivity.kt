package com.portfolio.hanmo.hanmo.Activity

import android.app.Activity
import android.os.Bundle
import com.portfolio.hanmo.hanmo.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_add_techstack.*
import org.jetbrains.anko.toast

/**
 * Created by hanmo on 2018. 2. 19..
 */
class AddTechStackActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_techstack)

        val btn_confirm = createButtonClickObservable()

        btn_add_tech_confirm.setOnClickListener {
            btn_confirm.subscribe {
                toast(it)
            }
        }

    }

    private fun createButtonClickObservable() : Observable<String> {
        return Observable.create {
            it.onNext(et_tech_name.text.toString())
        }
    }



}