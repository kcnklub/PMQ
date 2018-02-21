package com.music.party.pmq.auth.facebook

import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

/**
 * Created by krm8mc on 2/21/2018.
 */
abstract class FacebookAuth(loginBtn: LoginButton) {

    private var loginButton : LoginButton? = loginBtn
    private var callbackManager: CallbackManager? = null

    fun onAuthCancelled() {}

    fun onAuthError() {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent){
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    abstract fun onRegistrationComplete(loginResult : LoginResult?)

    init {
        this.callbackManager = CallbackManager.Factory.create()
        loginButton!!.setReadPermissions("email")
        loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                onAuthCancelled()
            }

            override fun onError(error: FacebookException?) {
                onAuthError()
            }

            override fun onSuccess(result: LoginResult?) {
                onRegistrationComplete(result)
            }
        })
    }
}