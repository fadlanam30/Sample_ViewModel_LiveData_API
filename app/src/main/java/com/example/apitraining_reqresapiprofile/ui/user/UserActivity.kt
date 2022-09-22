package com.example.apitraining_reqresapiprofile.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.apitraining_reqresapiprofile.data.remote.response.DataItem
import com.example.apitraining_reqresapiprofile.databinding.ActivityUserBinding
import com.example.apitraining_reqresapiprofile.ui.list.ListUsersActivity

class UserActivity : AppCompatActivity() {
    private lateinit var activityUserBinding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUserBinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(activityUserBinding.root)

        val dataUser = intent.getParcelableExtra<DataItem>(ListUsersActivity.RV_ITEM_ID)
        if (dataUser!!.firstName.isNotEmpty())
            activityUserBinding.apply {
                userDetailFirstnameTv.text = dataUser.firstName
                userDetailLastnameTv.text = dataUser.lastName
                userDetailEmailTv.text = dataUser.email
                Glide.with(activityUserBinding.root)
                    .load(dataUser.avatar)
                    .into(userPhotoDetailIv)
            }
    }
}