package com.music.party.pmq.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Kyle on 1/31/2018.
 */

class Song(n_id : Long, n_name : String, n_artist : String, n_album : String, n_url : String) : RealmObject(){

    @PrimaryKey var id : Long = n_id

    var name : String = n_name
    var artist: String = n_artist
    var album : String = n_artist
    var songUrl : String = n_url

}