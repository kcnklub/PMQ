package com.music.party.pmq

import android.app.Application
import io.realm.Realm

/**
 * Created by Kyle on 1/30/2018.
 */

class MyApplication : Application() {

    companion object {
        val AUTH_URL = "http://" + BuildConfig.OBJECT_SERVER_IP + ":9080/auth"
        val REALM_URL = "http://" + BuildConfig.OBJECT_SERVER_IP + ":9080/~/PMQ"
        val COMMON_URL = "http://" + BuildConfig.OBJECT_SERVER_IP + ":9080/Common_PMQ"
    }

    override fun onCreate(){
        super.onCreate()
        Realm.init(this)
    }
}