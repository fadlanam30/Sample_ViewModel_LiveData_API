package com.example.apitraining_reqresapiprofile.ui.upload

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apitraining_reqresapiprofile.data.remote.response.UploadUserResponse
import com.example.apitraining_reqresapiprofile.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel : ViewModel() {

    //LiveData API
    private val _responseUser = MutableLiveData<UploadUserResponse>()
    val responseUser: LiveData<UploadUserResponse> = _responseUser

    //LiveData Single Event
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadNewUser(name: String, email: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().uploadUser(name,email)
        client.enqueue(object : Callback<UploadUserResponse> {
            override fun onResponse(
                call: Call<UploadUserResponse>,
                response: Response<UploadUserResponse>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _responseUser.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UploadUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

}