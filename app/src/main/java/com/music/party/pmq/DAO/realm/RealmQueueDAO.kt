package com.music.party.pmq.DAO.realm

import com.music.party.pmq.DAO.QueueDAO
import com.music.party.pmq.models.PartyQueue
import com.music.party.pmq.models.Song
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 * Created by kylem on 7/9/2018.
implementation DAO for queue
 */
class RealmQueueDAO : QueueDAO {

    private lateinit var realm: Realm

    override fun addToQueue(song: Song) {
        realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction {
                val s = realm.createObject<Song>(song)
            }
        } finally {
            realm.close()
        }
    }

    override fun removeFromQueue(song: Song) {
        realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction {
                val queue: RealmList<Song>? = realm.where<PartyQueue>()
                        .findAll()
                        .first()
                        ?.queue
                if (queue != null) {
                    if (queue.contains(song)) {
                        queue.remove(song)
                    }
                }
            }
        } finally {
            realm.close()
        }
    }
}