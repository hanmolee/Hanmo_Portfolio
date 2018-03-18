package com.portfolio.hanmo.hanmo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import com.portfolio.hanmo.hanmo.R
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.toast

/**
 * Created by hanmo on 2018. 3. 18..
 */
class SignInActivity : AppCompatActivity() {

    private lateinit var signinDisposable : CompositeDisposable

    companion object {
        fun newIntent(context: Context) : Intent {
            val intent = Intent(context, SignInActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signinDisposable = CompositeDisposable()

        setSignInButton()
    }

    private fun setSignInButton() {
        btn_signin.clicks()
                .subscribe {
                    val login = RealmHelper.instance.signIn(et_signin_id.text.toString(), et_signin_pwd.text.toString())
                    when(login) {
                        true -> {
                            finish()
                        }
                        false -> {
                            toast("아이디 혹은 비밀번호가 일치하지 않습니다.")
                        }
                    }
                }
                .apply { signinDisposable.add(this) }

        btn_signup.setOnClickListener {
            val intentSignUp = SignUpActivity.newIntent(this@SignInActivity)
            //intentSignUp.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

            val btnPair = android.support.v4.util.Pair.create(btn_signup as View, "SignUpButton")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SignInActivity, btnPair)

            ActivityCompat.startActivity(this@SignInActivity, intentSignUp, options.toBundle())
        }

    }

    override fun onDestroy() {
        signinDisposable.clear()
        super.onDestroy()
    }
}