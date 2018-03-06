package com.music.party.pmq.models


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Kyle on 1/31/2018.
 */
class Party (n_id : Long, n_name : String,  n_url : String) : RealmObject() {
open class Party (
        @PrimaryKey var id : Long,
        var name : String,
        var ownerID: String,
        var queueURL: String
) : RealmObject() {


    companion object {
        val ID = "id"
        val NAME = "name"
        val OWNER_ID = "ownerID"
        val QUEUE_URL = "queueURL"
    }

    //default constructor
    constructor() : this (0, "", "", "")

}*/