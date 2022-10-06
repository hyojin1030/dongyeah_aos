package com.zoo.dongyeah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityUserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
    }

    fun onClickSave(view: View) {
        val intent = Intent(this, ActivityMain::class.java)
        startActivity(intent)
    }
}