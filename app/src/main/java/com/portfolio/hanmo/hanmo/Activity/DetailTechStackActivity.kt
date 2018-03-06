package com.portfolio.hanmo.hanmo.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.activity_detail_techstack.*

/**
 * Created by hanmo on 2018. 3. 6..
 */
class DetailTechStackActivity : Activity() {

    companion object {
        val EXTRA_PARAM_ID = "tech_id"

        fun newIntent(context: Context, position : Int) : Intent {
            val intent = Intent(context, DetailTechStackActivity::class.java)
            intent.putExtra(EXTRA_PARAM_ID, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_techstack)

        tech_name_txt.text = "dddd"

    }

}