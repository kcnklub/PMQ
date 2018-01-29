package com.music.party.pmq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignInActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var username:AutoCompleteTextView = findViewById(R.id.username_signin)
        var password:EditText = findViewById(R.id.password_signin)
        val signInButton:Button = findViewById(R.id.signin_button)
        val registerButton:Button = findViewById(R.id.register_button)

        signInButton.setOnClickListener { view ->
            Toast.makeText(this, "something", Toast.LENGTH_LONG).show()
        }

        registerButton.setOnClickListener { view ->

        }
    }
}
