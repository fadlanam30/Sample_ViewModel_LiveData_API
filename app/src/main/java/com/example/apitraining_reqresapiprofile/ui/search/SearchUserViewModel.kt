package com.example.apitraining_reqresapiprofile.ui.search

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apitraining_reqresapiprofile.data.remote.response.Data
import com.example.apitraining_reqresapiprofile.data.remote.response.SearchUserResponse
import com.example.apitraining_reqresapiprofile.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel : ViewModel() {
    //LiveData API
    private val _dataUser = MutableLiveData<Data>()
    val dataUser: LiveData<Data> = _dataUser

    //LiveData Single Event
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUserById(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserById(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>,
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _dataUser.value = responseBody.data
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

}