package com.example.apitraining_reqresapiprofile.ui.search

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apitraining_reqresapiprofile.data.remote.response.SearchUserResponse
import com.example.apitraining_reqresapiprofile.data.remote.retrofit.ApiConfig
import com.example.apitraining_reqresapiprofile.databinding.ActivitySearchUserBinding
import com.example.apitraining_reqresapiprofile.ui.list.ListUserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserActivity : AppCompatActivity() {
    private lateinit var searchUserBinding: ActivitySearchUserBinding

    private lateinit var searchUserViewModel: SearchUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchUserBinding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(searchUserBinding.root)

        searchUserViewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]

        searchUserBinding.searchUserInput.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUserViewModel.searchUserById(query!!)
//                searchUserById(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchUserViewModel.dataUser.observe(this) { user ->
            searchUserBinding.tvUserName.text =
                StringBuilder(user.firstName).append(" ").append(user.lastName)
            searchUserBinding.tvUserEmail.text = user.email
            Glide.with(searchUserBinding.root)
                .load(user.avatar)
                .into(searchUserBinding.imgUserPhoto)
        }

        searchUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

//    private fun searchUserById(query: String) {
//        searchUserBinding.cardView.visibility = View.GONE
//        searchUserBinding.searchUserLoading.visibility = View.VISIBLE
//        val client = ApiConfig.getApiService().getUserById(query)
//        client.enqueue(object : Callback<SearchUserResponse> {
//            override fun onResponse(
//                call: Call<SearchUserResponse>,
//                response: Response<SearchUserResponse>,
//            ) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        searchUserBinding.tvUserName.text =
//                            StringBuilder(responseBody.data.firstName).append(" ")
//                                .append(responseBody.data.lastName)
//                        searchUserBinding.tvUserEmail.text = responseBody.data.email
//                        Glide.with(searchUserBinding.root)
//                            .load(responseBody.data.avatar)
//                            .into(searchUserBinding.imgUserPhoto)
//                    }
//                    searchUserBinding.searchUserLoading.visibility = View.GONE
//                    searchUserBinding.cardView.visibility = View.VISIBLE
//                } else {
//                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
//                    searchUserBinding.searchUserLoading.visibility = View.GONE
//                    searchUserBinding.cardView.visibility = View.VISIBLE
//                }
//            }
//
//            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
//                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
//                searchUserBinding.searchUserLoading.visibility = View.GONE
//                searchUserBinding.cardView.visibility = View.VISIBLE
//            }
//        })
//    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            searchUserBinding.cardView.visibility = View.GONE
            searchUserBinding.searchUserLoading.visibility = View.VISIBLE
        } else {
            searchUserBinding.cardView.visibility = View.VISIBLE
            searchUserBinding.searchUserLoading.visibility = View.GONE
        }

    }

}