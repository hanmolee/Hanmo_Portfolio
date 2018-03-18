package com.portfolio.hanmo.hanmo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.portfolio.hanmo.hanmo.DataModel.UsersTable
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created by hanmo on 2018. 3. 14..
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var signupDisposable : CompositeDisposable
    private var enName : Boolean = false
    private var enId : Boolean = false
    private var enPwd : Boolean = false
    private var enConfirmPwd : Boolean = false

    companion object {
        fun newIntent(context: Context) : Intent {
            val intent = Intent(context, SignUpActivity::class.java)
            return intent
        }
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signupDisposable = CompositeDisposable()

        setInputText()
        setClickSignup()
    }


    private fun setClickSignup() {
        //전부  clear시 버튼 활성화
        btn_signup.clicks()
                .subscribe {
                    when(btn_signup.isEnabled) {
                        true -> {
                            RealmHelper.instance.insertUsers(UsersTable::class.java,
                                    et_name.text.toString(),
                                    et_id.text.toString(),
                                    et_pwd.text.toString()
                            )

                            finish()
                        }
                        false -> {
                            toast("모든 텍스트를 채워주세요")
                        }
                    }
                }
                .apply { signupDisposable.add(this) }
    }

    private fun setInputText() {
        et_name.textChanges()
                .doOnNext {
                    enName = when(it.toString().trim().length) {
                        0 -> {
                            check_name.setImageResource(R.drawable.ic_signup_warning)
                            false
                        }
                        else -> {
                            check_name.setImageResource(R.drawable.ic_signup_clear)
                            true
                        }
                    }
                }
                .subscribe { ButtonEnabled() }
                .apply { signupDisposable.add(this) }

        et_id.textChanges()
                .doOnNext {
                    enId = when(isEmailValid(it)) {
                        true -> {
                            check_id.setImageResource(R.drawable.ic_signup_clear)
                            true
                        }
                        false -> {
                            check_id.setImageResource(R.drawable.ic_signup_warning)
                            false
                        }
                    }
                }
                .subscribe { ButtonEnabled() }
                .apply { signupDisposable.add(this) }

        et_pwd.textChanges()
                .doOnNext {
                    enPwd = when {
                        it.length > 6 -> {
                            check_pwd.setImageResource(R.drawable.ic_signup_clear)
                            true
                        }
                        else -> {
                            check_pwd.setImageResource(R.drawable.ic_signup_warning)
                            false
                        }
                    }
                }
                .subscribe { ButtonEnabled() }
                .apply { signupDisposable.add(this) }

        et_confirm_pwd.textChanges()
                .doOnNext {
                    enConfirmPwd = when {
                        it.length > 6 -> {
                            when(et_pwd.text.toString().equals(it.toString(), true)) {
                                true -> {
                                    check_confirm_pwd.setImageResource(R.drawable.ic_signup_clear)
                                    true
                                }
                                else -> {
                                    false
                                }
                            }
                        }
                        else -> {
                            check_confirm_pwd.setImageResource(R.drawable.ic_signup_warning)
                            false
                        }
                    }
                }
                .subscribe { ButtonEnabled() }
                .apply { signupDisposable.add(this) }
    }

    private fun ButtonEnabled() {
        when {
            enName && enId && enPwd && enConfirmPwd -> {
                btn_signup.isEnabled = true
            }
            else -> {
                btn_signup.isEnabled = false
            }
        }
    }

    override fun onDestroy() {
        signupDisposable.clear()
        super.onDestroy()
    }
}