package com.zoo.dongyeah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }

    fun onClickLogin(view: View) {
        val intent = Intent(this, ActivityUserInfo::class.java)
        startActivity(intent)
    }
}