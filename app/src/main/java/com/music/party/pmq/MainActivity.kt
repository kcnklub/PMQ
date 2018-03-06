package com.music.party.pmq

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.music.party.pmq.models.PartyQueue
import io.realm.SyncUser
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, SyncUser.currentUser().identity, Toast.LENGTH_SHORT).show()
    }
}
