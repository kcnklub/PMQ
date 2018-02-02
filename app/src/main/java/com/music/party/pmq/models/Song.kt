package com.music.party.pmq.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Kyle on 1/31/2018.
 */

open class Song(
        @PrimaryKey var id : Long,
        var name : String,
        var artist: String,
        var album : String,
        var songUrl : String
) : RealmObject(){

    //Default constructor.
    constructor() : this(0, "", "", "", "")

}