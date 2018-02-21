package com.music.party.pmq.auth.google

import android.support.v4.app.FragmentActivity
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.music.party.pmq.R

/**
 * Created by krm8mc on 2/21/2018.
 */
abstract class GoogleAuth(btnSignIn : SignInbutton, fragmentActivity: FragmentActivity) : GoogleApiClient.OnConnectionFailedListener {

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


    }

}