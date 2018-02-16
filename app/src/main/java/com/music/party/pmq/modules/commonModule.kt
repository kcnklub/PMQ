package com.music.party.pmq.modules

import com.music.party.pmq.models.Party
import com.music.party.pmq.models.User
import io.realm.annotations.RealmModule

/**
 * Created by kylem on 2/2/2018.
 */
@RealmModule(classes = arrayOf(Party::class, User::class))
class commonModule {
}