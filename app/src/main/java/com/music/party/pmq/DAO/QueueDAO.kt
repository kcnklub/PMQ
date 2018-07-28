package com.music.party.pmq.DAO

import com.music.party.pmq.models.Song

/**
 * Created by kylem on 7/9/2018.
 * all of the data base operations will be in this file that we don't have it spread throughout
 * the rest of the project.
 */
interface QueueDAO {

    /**
     * add a song to the party queue.
     */
    fun addToQueue(song: Song)

    /**
     * remove a song from the party queue.
     */
    fun removeFromQueue(song: Song)

}