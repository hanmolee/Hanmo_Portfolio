package com.portfolio.hanmo.hanmo.Activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.portfolio.hanmo.hanmo.DataModel.Admin_Table
import com.portfolio.hanmo.hanmo.MainActivity.Companion.admin
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import kotlinx.android.synthetic.main.activity_admin.*
import org.jetbrains.anko.toast

/**
 * Created by hanmo on 2018. 2. 17..
 */
class AdminActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btn_login_admin.setOnClickListener(this)
        btn_create_admin.setOnClickListener(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        admin = 0
        setResult(200)
        finish()
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_create_admin -> {
                val id : String = et_admin_id.text.toString()
                val pwd : String = et_admin_pwd.text.toString()

                RealmHelper.instance.adminCreate(id, pwd)

                toast("관리자 아이디가 생성되었습니다 \n로그인 버튼을 클릭해 주세요!")

            }
            btn_login_admin -> {
                val id : String = et_admin_id.text.toString()
                val pwd : String = et_admin_pwd.text.toString()

                var results = RealmHelper.instance.queryAll(Admin_Table::class.java)
                when(results.size) {
                    0 -> {
                        toast("관리자 아이디가 존재하지 않습니다. \n관리자 아이디를 생성해 주세요!")
                    }
                    else -> {
                        RealmHelper.instance.adminLogin(this, id, pwd)
                    }
                }
            }
        }
    }
}