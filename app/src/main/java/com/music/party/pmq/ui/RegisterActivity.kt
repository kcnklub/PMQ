package com.music.party.pmq.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.music.party.pmq.MyApplication
import com.music.party.pmq.MyApplication.Companion.AUTH_URL
import com.music.party.pmq.R
import com.music.party.pmq.auth.UserManager
import com.music.party.pmq.auth.facebook.FacebookAuth
import com.music.party.pmq.auth.google.GoogleAuth
import com.music.party.pmq.models.User
import com.music.party.pmq.modules.commonModule
import io.realm.*
import io.realm.SyncCredentials.google

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterActivity : AppCompatActivity(), SyncUser.Callback<SyncUser> {

    private var userProviderId: String = ""
    private var googleAuth: GoogleAuth? = null
    private var facebookAuth: FacebookAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button = findViewById(R.id.register_button)

        registerButton.setOnClickListener { _ ->
            attemptRegister()
        }

        //Set up facebook Auth
        facebookAuth = object : FacebookAuth(findViewById(R.id.login_button)) {
            override fun onRegistrationComplete(loginResult: LoginResult?) {
                UserManager.setAuthMode(UserManager.AUTH_MODE.GOOGLE)
                val credentials = SyncCredentials.facebook(loginResult?.accessToken!!.token)
                userProviderId = credentials.identityProvider
                SyncUser.logInAsync(credentials, AUTH_URL, this@RegisterActivity)
            }
        }

        googleAuth = object : GoogleAuth(findViewById(R.id.sign_in_button), this) {
            override fun onRegistrationComplete(result: GoogleSignInResult) {
                UserManager.setAuthMode(UserManager.AUTH_MODE.GOOGLE)
                val acct: GoogleSignInAccount? = result.signInAccount
                val credentials = google(acct?.idToken)
                userProviderId = credentials.identityProvider
                SyncUser.logInAsync(credentials, AUTH_URL, this@RegisterActivity)
            }
        }

    }

    /* TODO: google and facebook login in.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        googleAuth!!.onActivityResult(requestCode, resultCode, data)
        facebookAuth!!.onActivityResult(requestCode, resultCode, data)
    }*/

    private fun attemptRegister() {
        val usernameView: EditText = findViewById(R.id.username_register)
        val emailView: EditText = findViewById(R.id.email_register)
        val passwordView: EditText = findViewById(R.id.password_register)
        val passwordConfirmView: EditText = findViewById(R.id.password_confirm)

        usernameView.error = null
        emailView.error = null
        passwordView.error = null
        passwordConfirmView.error = null

        val username: String = usernameView.text.toString()
        val email: String = emailView.text.toString()
        val password: String = passwordView.text.toString()
        val passwordConfirm: String = passwordConfirmView.text.toString()

        var cancel = false
        var focusView: View = usernameView

        if (isEmpty(username)) {
            usernameView.setError("@string/error_field_required")
            focusView = usernameView
            cancel = true
        }

        if (isEmpty(email)) {
            usernameView.setError("@string/error_field_required")
            focusView = emailView
            cancel = true
        }

        if (isEmpty(password)) {
            usernameView.setError("@string/error_field_required")
            focusView = passwordView
            cancel = true
        }

        if (isEmpty(passwordConfirm)) {
            usernameView.setError("@string/error_field_required")
            focusView = passwordConfirmView
            cancel = true
        }

        if (password != passwordConfirm) {
            passwordConfirmView.setError("@string/passwords_dont_match")
            focusView = passwordConfirmView
            cancel = true
        }

        if (cancel) {
            focusView.requestFocus()
        } else {
            SyncUser.logInAsync(
                    SyncCredentials.usernamePassword(username, password, true),
                    MyApplication.AUTH_URL,
                    object : SyncUser.Callback<SyncUser> {
                        override fun onSuccess(user: SyncUser) {
                            userProviderId = username
                            registrationComplete(user)
                        }

                        override fun onError(error: ObjectServerError?) {
                            Log.d("TAG", error.toString())
                        }
                    })
        }

    }

    private fun registrationComplete(user: SyncUser?) {
        UserManager.setActiveUser(user)
        Log.d("TAG", "setting this user as the active user.")
        /*
        * check if the user's email has been used before in this app if not add the user if so warn the user and throw error
        * */
        Realm.getInstanceAsync(commonModule.getCommonRealm(user), object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                Log.d("TAG", "The common realm has been loaded and we are ready to check if the email entered has been used")
                val checkUser: RealmResults<User> = realm.where(User::class.java).equalTo(User.EMAIL, userProviderId).findAll()
                if (checkUser.isEmpty()) {
                    realm.executeTransaction { realm ->
                        val savedUser: User? = realm.createObject(User::class.java, user?.identity)
                        savedUser?.email = userProviderId
                        Log.d("TAG", "The User has been added to the User DB in the common Realm")
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "This email has been used before", Toast.LENGTH_LONG).show()
                    //warns the user and logouts out the realm user object and doesn't add the custom user to the realm.
                    SyncUser.current().logOut()
                }
            }
        })

        Log.d("Tag", "We did it REGISTRATION COMPLETE")
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onSuccess(result: SyncUser?) {
        registrationComplete(result)
    }

    override fun onError(error: ObjectServerError?) {
        val errorMsg: String? = when (error?.errorCode) {
            ErrorCode.EXISTING_ACCOUNT -> "Account already Exists"
            else -> error.toString()
        }
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
    }
}
