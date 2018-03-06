package com.music.party.pmq

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.spotify.sdk.android.player.*
import kaaes.spotify.webapi.android.SpotifyApi
import kaaes.spotify.webapi.android.SpotifyCallback
import kaaes.spotify.webapi.android.SpotifyError
import kaaes.spotify.webapi.android.models.Track
import kaaes.spotify.webapi.android.models.TracksPager


class SpotifyAuth : AppCompatActivity(), Player.NotificationCallback, ConnectionStateCallback {

    private val CLIENT_ID = "790890a4a7ab4ec39238e088d4ef2202"
    private val REDIRECT_URI = "https://chazlakinger.com" //change this to our site. doesn't really matter won't ever get used
    private val REQUEST_CODE = 1337
    private var spotifyAuthToken = ""
    private var mPlayer: Player? = null
    val api = SpotifyApi()
    var songArrayList: ArrayList<Track> = ArrayList()
    var songProgress: ProgressBar? = null
    var songProgressStatus = 0
    var progressHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_auth)


        val songlistview: ListView = findViewById(R.id.songlist)
        songProgress = findViewById(R.id.songProgressBar)

        val builder = AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI)
        builder.setScopes(arrayOf("user-read-private", "streaming"))
        val request = builder.build()
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)

        val song2Button: Button = findViewById(R.id.button2)
        song2Button.setOnClickListener { view ->
            val searchtext: EditText = findViewById(R.id.searchtext)

            var spotify = api.service
            spotify.searchTracks(searchtext.text.toString(), object : SpotifyCallback<TracksPager>() {
                override fun failure(p0: SpotifyError?) {
                    Log.d("Album failure", p0.toString())
                }
                override fun success(t: TracksPager?, response: retrofit.client.Response?) {
                    if (t != null) {
                        if(t.tracks.total > 0)
                        {
                            songArrayList.clear()
                            for (i in t.tracks.items) {
                                songArrayList.add(i)
                                System.out.println(i.album.images[0].url)
                            }
                            val songAdt = SongAdapter(this@SpotifyAuth, songArrayList)
                            songlistview.setAdapter(songAdt)
                        }
                    }
                }
            })
        }

        val pauseButton: Button = findViewById(R.id.button3)
        pauseButton.setOnClickListener { view ->
            mPlayer?.pause(null)
        }

        val resumeButton: Button = findViewById(R.id.button4)
        resumeButton.setOnClickListener { view ->
            mPlayer?.resume(null)
        }

    }

    fun trackPicked(view: View) {
        System.out.println("selected ID: " + Integer.parseInt(view.tag.toString()))
        System.out.println(songArrayList[Integer.parseInt(view.tag.toString())].artists[0].name + ": " + songArrayList[Integer.parseInt(view.tag.toString())].name)
        mPlayer?.playUri(null, songArrayList[Integer.parseInt(view.tag.toString())].uri, 0, 0)

        Thread(object: Runnable {
            override fun run() {
                while(songProgressStatus < 100) {
                    var position: Float = mPlayer!!.playbackState.positionMs.toFloat()
                    var duration: Float = songArrayList[Integer.parseInt(view.tag.toString())].duration_ms.toFloat()
                    songProgressStatus = (100 * (position / duration)).toInt()

                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    progressHandler.post(object : Runnable {
                        override fun run() {
                            songProgress?.progress = songProgressStatus
                        }

                    })
                }
            }

        }).start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type == AuthenticationResponse.Type.TOKEN) {
                val playerConfig = Config(this, response.accessToken, CLIENT_ID)
                spotifyAuthToken = response.accessToken
                System.out.println("!!!!!!!!!!AUTH TOKEN: " + spotifyAuthToken)
                Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
                    override fun onInitialized(spotifyPlayer: SpotifyPlayer) {
                        mPlayer = spotifyPlayer
                        mPlayer?.addConnectionStateCallback(this@SpotifyAuth)
                        mPlayer?.addNotificationCallback(this@SpotifyAuth)
                    }

                    override fun onError(throwable: Throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.message)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        Spotify.destroyPlayer(this)
        super.onDestroy()
    }

    override fun onPlaybackEvent(playerEvent: PlayerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name)

    }

    override fun onPlaybackError(error: Error) {
        Log.d("MainActivity", "Playback error received: " + error.name)

    }

    override fun onLoggedIn() {
        Log.d("MainActivity", "User logged in")
        api.setAccessToken(spotifyAuthToken)
    }

    override fun onLoggedOut() {
        Log.d("MainActivity", "User logged out")
    }

    override fun onLoginFailed(var1: Error) {
        Log.d("MainActivity", "Login failed")
    }

    override fun onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred")
    }

    override fun onConnectionMessage(message: String) {
        Log.d("MainActivity", "Received connection message: " + message)
    }
}

