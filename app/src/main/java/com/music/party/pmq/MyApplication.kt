package com.music.party.pmq

import android.app.Application
import io.realm.Realm

/**
 * Created by Kyle on 1/30/2018.
 */

class MyApplication : Application() {

    companion object {
        val AUTH_URL = "somsething"
    }

    override fun onCreate(){

        super.onCreate()
        Realm.init(this)
    }
}