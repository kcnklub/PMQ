package com.music.party.pmq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.music.party.pmq.modules.commonModule
import io.realm.*

class RegisterActivity : AppCompatActivity(), SyncUser.Callback<SyncUser> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button = findViewById(R.id.register_button)

        registerButton.setOnClickListener {view ->
            //TODO create the user and sign in.
            attemptRegister()
        }
    }

    private fun attemptRegister(){
        val usernameView: EditText = findViewById(R.id.username_register)
        val emailView: EditText = findViewById(R.id.email_register)
        val passwordView: EditText = findViewById(R.id.password_register)
        val passwordConfirmView: EditText = findViewById(R.id.password_confirm)

        usernameView.setError(null)
        emailView.setError(null)
        passwordView.setError(null)
        passwordConfirmView.setError(null)

        val username: String = usernameView.text.toString()
        val email: String = emailView.text.toString()
        val password: String = passwordView.text.toString()
        val passwordConfirm: String = passwordConfirmView.text.toString()

        var cancel : Boolean = false
        var focusView: View = usernameView

        if(isEmpty(username)){
            usernameView.setError("@string/error_field_required")
            focusView = usernameView
            cancel = true
        }

        if(isEmpty(email)){
            usernameView.setError("@string/error_field_required")
            focusView = emailView
            cancel = true
        }

        if(isEmpty(password)){
            usernameView.setError("@string/error_field_required")
            focusView = passwordView
            cancel = true
        }

        if(isEmpty(passwordConfirm)){
            usernameView.setError("@string/error_field_required")
            focusView = passwordConfirmView
            cancel = true
        }

        if(password != passwordConfirm){
            passwordConfirmView.setError("@string/passwords_dont_match")
            focusView = passwordConfirmView
            cancel = true
        }

        if(cancel){
            focusView.requestFocus()
        } else {
            SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, true), MyApplication.AUTH_URL, SyncUser.Callback<SyncUser>(){
                fun onSuccess(user: SyncUser){

                }
            })
        }

    }

    override fun onSuccess(result: SyncUser?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(error: ObjectServerError?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
