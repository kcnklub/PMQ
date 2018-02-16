package com.music.party.pmq

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.ErrorCode
import io.realm.ObjectServerError
import io.realm.SyncCredentials
import io.realm.SyncUser

import com.music.party.pmq.MyApplication
import com.music.party.pmq.UserManager

class SignInActivity : AppCompatActivity(), SyncUser.Callback<SyncUser> {

    val TAG: String = "Sign In Activty"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        val signInButton:Button = findViewById(R.id.signin_button)
        val registerButton:Button = findViewById(R.id.register_button)

        signInButton.setOnClickListener { view ->
            Toast.makeText(this, "something", Toast.LENGTH_LONG).show()

        }

        registerButton.setOnClickListener { view ->
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun attemptLogin(){
        val usernameView:AutoCompleteTextView = findViewById(R.id.username_signin)
        val passwordView:EditText = findViewById(R.id.password_signin)

        usernameView.error = null
        passwordView.error = null

        val email: String = usernameView.text.toString()
        val password: String = passwordView.text.toString()

        var cancel = false

        var focusView : View = usernameView

        if(TextUtils.isEmpty(password)){
            passwordView.setError("@Strings/error_invalid_password")
            focusView = passwordView
            cancel = true
        }

        if(TextUtils.isEmpty(email)){
            usernameView.setError("@Strings/error_required_field")
            focusView = usernameView
            cancel = true
        }

        if(cancel){
            focusView.requestFocus()
        } else {
            Log.d(TAG, "Logining IN")
            SyncUser.loginAsync(SyncCredentials.usernamePassword(email, password, false), MyApplication.AUTH_URL, this);
        }
    }

    private fun loginComplete(user: SyncUser?){
        Log.d(TAG, "LOGIN IN COMPLETE")

        UserManager.setActiveUser(user)
        // move to main activity and end this activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onSuccess(result: SyncUser?) {
        Log.d(TAG, "Success")
        loginComplete(result)
    }

    override fun onError(error: ObjectServerError?) {
        Log.d(TAG, "Failed but not running this")
        val errorMsg: String?
        when(error?.errorCode){
            ErrorCode.UNKNOWN -> errorMsg = "Account does not exist"
            ErrorCode.INVALID_CREDENTIALS -> errorMsg = "The Provided credentials are invalid!"
            else -> {
                errorMsg = error?.toString()
            }
        }

        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
    }
}
