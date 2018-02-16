package com.music.party.pmq

import com.music.party.pmq.modules.personalModule
import io.realm.Realm
import io.realm.SyncConfiguration
import io.realm.SyncUser

/**
 * Created by Kyle on 2/2/2018.
 */
object UserManager {

    private var TAG: String = "User Manager"

    fun setActiveUser(user: SyncUser?){
        val defaultConfig: SyncConfiguration = SyncConfiguration.Builder(user, MyApplication.REALM_URL)
                .modules(personalModule())
                .build()
        Realm.setDefaultConfiguration(defaultConfig)
    }
}