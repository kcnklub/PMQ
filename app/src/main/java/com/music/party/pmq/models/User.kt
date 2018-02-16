package com.music.party.pmq.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Kyle on 2/1/2018.
 */

open class User(
        @PrimaryKey var id: String,
        var displayName: String,
        var isPartyOwner: Boolean
) : RealmObject() {

    companion object {
        val ID = "id"
        val DISPLAY_NAME = "displayName"
        val IS_PARTY_OWNER = "isPartyOwner"
    }

    //default constructor
    constructor() : this("", "", false)
    
}