package com.example.apitraining_reqresapiprofile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apitraining_reqresapiprofile.databinding.ActivityMainBinding
import com.example.apitraining_reqresapiprofile.ui.list.ListUsersActivity
import com.example.apitraining_reqresapiprofile.ui.search.SearchUserActivity
import com.example.apitraining_reqresapiprofile.ui.upload.UploadUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.btnGetListusers.setOnClickListener {
            val intentToListUsersActivity = Intent(this@MainActivity, ListUsersActivity::class.java)
            startActivity(intentToListUsersActivity)
        }

        activityMainBinding.btnGetUser.setOnClickListener {
            val intentToUserActivity = Intent(this@MainActivity, SearchUserActivity::class.java)
            startActivity(intentToUserActivity)
        }

        activityMainBinding.btnUploadUser.setOnClickListener {
            val intentToUploadUserActivity = Intent(this@MainActivity, UploadUserActivity::class.java)
            startActivity(intentToUploadUserActivity)
        }
    }
}