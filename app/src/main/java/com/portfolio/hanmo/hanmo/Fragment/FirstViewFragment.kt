package com.portfolio.hanmo.hanmo.Fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.fragment_firstview.view.*

/**
 * Created by hanmo on 2018. 2. 3..
 */
class FirstViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_firstview, container, false)

        val run_count = RealmHelper.instance.queryFirst(Active_Count_Table::class.java)
        when{
            run_count != null -> {
                var count = run_count.count!!
                rootView.count.text = count.toString()
            }
        }

        return rootView
    }
}