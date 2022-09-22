package com.example.apitraining_reqresapiprofile.ui.list

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apitraining_reqresapiprofile.data.remote.retrofit.ApiConfig
import com.example.apitraining_reqresapiprofile.data.remote.response.DataItem
import com.example.apitraining_reqresapiprofile.data.remote.response.ListUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserViewModel : ViewModel() {

    //LiveData API
    private val _listUser = MutableLiveData<List<DataItem>>()
    val listUser: LiveData<List<DataItem>> = _listUser

    //LiveData Single Event
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchList()
    }

    private fun searchList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersList(PAGE)
        client.enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(
                call: Call<ListUserResponse>,
                response: Response<ListUserResponse>,
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    if (response.body() != null) {
                        _listUser.value = response.body()?.data
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val PAGE = "1"
    }

}