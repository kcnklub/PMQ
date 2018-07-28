package com.music.party.pmq.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by Kyle on 1/31/2018.
 */
@RealmClass
open class PartyQueue(@PrimaryKey var id: Long,
                 var name: String,
                 var queue: RealmList<Song> = RealmList(),
                 var currentSong: Song? = null
) : RealmObject() {

    companion object {
        val ID = "id"
        val NAME = "name"
        val QUEUE = "queue"
        val CURRENT_SONG = "currentSong"
    }

    //Default constructor.
    constructor() : this(0, "")
}