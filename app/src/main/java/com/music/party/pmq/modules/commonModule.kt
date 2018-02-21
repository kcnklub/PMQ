package com.music.party.pmq.modules

import com.music.party.pmq.MyApplication
import com.music.party.pmq.models.Party
import com.music.party.pmq.models.User
import io.realm.RealmConfiguration
import io.realm.SyncConfiguration
import io.realm.SyncUser
import io.realm.annotations.RealmModule

/**
 * Created by kylem on 2/2/2018.
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@RealmModule(classes = arrayOf(Party::class, User::class))
class commonModule {
    companion object {
        fun getCommonRealm (user: SyncUser?): RealmConfiguration {
            return SyncConfiguration.Builder(user, MyApplication.COMMON_URL)
                    .modules(commonModule())
                    .build()
        }
    }

}