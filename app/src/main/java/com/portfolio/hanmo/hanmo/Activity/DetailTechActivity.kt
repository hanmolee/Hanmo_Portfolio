package com.portfolio.hanmo.hanmo.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.util.Log
import com.portfolio.hanmo.hanmo.DataModel.TechStackTable
import com.portfolio.hanmo.hanmo.Fragment.Fragment_Pager
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_techstack.*

/**
 * Created by hanmo on 2018. 3. 6..
 */
class DetailTechActivity : Activity() {

    companion object {
        const val EXTRA_PARAM_ID = "tech_id"

        fun newIntent(context: Context, position : Int) : Intent {
            val intent = Intent(context, DetailTechActivity::class.java)
            intent.putExtra(EXTRA_PARAM_ID, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_techstack)

        val tech_id = intent.getIntExtra(EXTRA_PARAM_ID, 0)

        val tech = RealmHelper.instance.techStack_queryFirst(TechStackTable::class.java, tech_id)
        when (tech) {
            null -> {
                Log.e("Tech Detail", "Tech List is null")
            }
            else -> {
                TechName.text = tech.tech_name
                val image_id = com.portfolio.hanmo.hanmo.DataModel.TechImage.techList()[tech_id]
                Picasso.with(applicationContext).load(image_id).into(TechImage)

                val photo = BitmapFactory.decodeResource(resources, image_id)
                Palette.from(photo).generate {
                    val bgColor = it.getMutedColor(ContextCompat.getColor(applicationContext, android.R.color.black))
                    TechNameHolder.setBackgroundColor(bgColor)
                }
            }

        }
    }

}