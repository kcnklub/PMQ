package com.music.party.pmq.auth

import com.facebook.login.LoginManager
import com.music.party.pmq.MyApplication
import com.music.party.pmq.modules.personalModule
import io.realm.Realm
import io.realm.SyncConfiguration
import io.realm.SyncUser

/**
 * Created by Kyle on 2/2/2018.
idk this is to get rid of a warning will add actual
header later.
 */
object UserManager {

    private var TAG: String = "User Manager"
    private var mode: AUTH_MODE = AUTH_MODE.PASSWORD // default


    enum class AUTH_MODE {
        PASSWORD,
        FACEBOOK,
        GOOGLE
    }

    fun setAuthMode(m: AUTH_MODE) {
        mode = m
    }

    fun logoutActiveUser() {
        when (mode) {
            AUTH_MODE.PASSWORD, AUTH_MODE.GOOGLE -> {
            }
            AUTH_MODE.FACEBOOK -> LoginManager.getInstance().logOut()
        }

        SyncUser.currentUser().logout()
    }


    fun setActiveUser(user: SyncUser?) {
        val defaultConfig: SyncConfiguration = SyncConfiguration.Builder(user!!, MyApplication.REALM_URL)
                .modules(personalModule())
                .build()
        Realm.setDefaultConfiguration(defaultConfig)
    }


}