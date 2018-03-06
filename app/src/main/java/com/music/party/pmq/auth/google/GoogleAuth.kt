package com.music.party.pmq.auth.google

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.SignInButton
import com.music.party.pmq.R

/**
* Created by krm8mc on 2/21/2018.
idk this is to get rid of a warning will add actual
header later.
*/
abstract class GoogleAuth(btnSignIn : SignInButton, fragmentActivity: FragmentActivity) : GoogleApiClient.OnConnectionFailedListener {

    private val TAG = "GoogleAuth"
    private var mGoogleApiClient : GoogleApiClient? = null
    private val RC_SIGN_IN = 10

    init {
        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(fragmentActivity.getString(R.string.server_client_id))
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(fragmentActivity)
                .enableAutoManage(fragmentActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        btnSignIn.setOnClickListener {
            val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            fragmentActivity.startActivityForResult(signInIntent, RC_SIGN_IN)
            Log.d(TAG, "Button clicked and fragment launched")
            Log.d(TAG, "GOOGLE")
        }

    }

    fun onActivityResult(requestCode : Int, resultCode: Int, data: Intent?){
        Log.d(TAG, "We got a result and now we are going to start handling it")
        if(requestCode == RC_SIGN_IN){
            val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        if(!p0.hasResolution()) {
            onError("connection failed and has no resolution. code: " + p0.errorCode)
        }
    }

    abstract fun onRegistrationComplete(result: GoogleSignInResult)

    fun onError(s: String){
        Log.d(TAG, s)
    }

    private fun handleSignInResult(result: GoogleSignInResult){
        Log.d(TAG, "handling the result now")
        if(result.isSuccess){
            Log.d(TAG, "SUCCESS")
            onRegistrationComplete(result)
        } else {
            Log.d(TAG, "Auth went wrong")
        }
    }
}