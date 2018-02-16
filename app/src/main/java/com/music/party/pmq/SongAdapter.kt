package com.music.party.pmq

import android.content.Context
import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup
import kaaes.spotify.webapi.android.models.Track
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView





class SongAdapter(c: Context, theSongs: ArrayList<Track>): BaseAdapter(){

    private var songArrayList: ArrayList<Track> = theSongs
    private var songInf: LayoutInflater? = LayoutInflater.from(c)

    override fun getCount(): Int {
        return this.songArrayList.size
    }

    override fun getItem(arg0: Int): Any? {

        return null
    }

    override fun getItemId(arg0: Int): Long {

        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //map to song layout
        val songLay = songInf?.inflate(R.layout.track, parent, false) as LinearLayout
        //get title and artist views
        val songView = songLay.findViewById(R.id.track_title) as TextView
        val artistView = songLay.findViewById(R.id.track_artist) as TextView
        //get song using position
        val currSong = songArrayList[position]
        //get title and artist strings
        songView.text = currSong.name
        artistView.text = currSong.artists[0].name
        //set position as tag
        songLay.tag = position
        return songLay
    }

}