package com.portfolio.hanmo.hanmo.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.portfolio.hanmo.hanmo.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * Created by hanmo on 2018. 3. 14..
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var signupDisposable : CompositeDisposable

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
                .doOnNext {  }
                .subscribe {  }
                .apply { signupDisposable.add(this) }
    }

    private fun setInputText() {
        et_name.textChanges()
                .subscribe {
                    when(it.toString().trim().length) {
                        0 -> {
                            check_name.setImageResource(R.drawable.ic_signup_warning)
                        }
                        else -> {
                            check_name.setImageResource(R.drawable.ic_signup_clear)
                        }
                    }
                }
                .apply { signupDisposable.add(this) }

        et_id.textChanges()
                .subscribe {
                    when(isEmailValid(it)) {
                        true -> {
                            check_id.setImageResource(R.drawable.ic_signup_clear)
                        }
                        false -> {
                            check_id.setImageResource(R.drawable.ic_signup_warning)
                        }
                    }
                }.apply { signupDisposable.add(this) }

        et_pwd.textChanges()
                .subscribe {
                    when {
                        it.length > 6 -> {
                            check_pwd.setImageResource(R.drawable.ic_signup_clear)
                        }
                        else -> {
                            check_pwd.setImageResource(R.drawable.ic_signup_warning)
                        }
                    }
                }.apply { signupDisposable.add(this) }

        et_confirm_pwd.textChanges()
                .subscribe {
                    when {
                        it.length > 6 -> {
                            when(et_pwd.text.toString().equals(it.toString(), true)) {
                                true -> {
                                    check_confirm_pwd.setImageResource(R.drawable.ic_signup_clear)
                                }
                            }
                        }
                        else -> {
                            check_confirm_pwd.setImageResource(R.drawable.ic_signup_warning)
                        }
                    }
                }.apply { signupDisposable.add(this) }
    }

    override fun onDestroy() {
        signupDisposable.clear()
        super.onDestroy()
    }
}