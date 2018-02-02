package com.music.party.pmq.models


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Kyle on 1/31/2018.
 */
open class Party (
        @PrimaryKey var id : Long,
        var name : String,
        var ownerID: String,
        var queueURL: String
) : RealmObject() {

    //default constructor
    constructor() : this (0, "", "", "")

}