package com.music.party.pmq.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.music.party.pmq.DAO.QueueDAO
import com.music.party.pmq.R
import io.realm.SyncUser

class MainActivity : AppCompatActivity() {

    private lateinit var queueDAO: QueueDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, SyncUser.currentUser().identity, Toast.LENGTH_SHORT).show()
    }
}
