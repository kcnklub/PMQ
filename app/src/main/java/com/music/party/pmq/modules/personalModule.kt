package com.music.party.pmq.modules

import com.music.party.pmq.models.PartyQueue
import com.music.party.pmq.models.Song
import io.realm.annotations.RealmModule

/**
 * Created by kylem on 2/2/2018.
 */
@RealmModule(classes = arrayOf(PartyQueue::class, Song::class))
class personalModule {
}