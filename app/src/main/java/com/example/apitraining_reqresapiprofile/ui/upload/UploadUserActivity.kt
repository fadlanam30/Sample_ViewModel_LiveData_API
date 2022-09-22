package com.example.apitraining_reqresapiprofile.ui.upload

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.apitraining_reqresapiprofile.data.remote.response.UploadUserResponse
import com.example.apitraining_reqresapiprofile.data.remote.retrofit.ApiConfig
import com.example.apitraining_reqresapiprofile.databinding.ActivityUploadUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadUserActivity : AppCompatActivity() {
    private lateinit var uploadUserBinding: ActivityUploadUserBinding

    private lateinit var uploadViewModel: UploadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadUserBinding = ActivityUploadUserBinding.inflate(layoutInflater)
        setContentView(uploadUserBinding.root)

        uploadViewModel = ViewModelProvider(this)[UploadViewModel::class.java]

        uploadUserBinding.uploadDataUserBtn.setOnClickListener {
            uploadViewModel.uploadNewUser(uploadUserBinding.uploadNameUserInput.text.toString(),
                uploadUserBinding.uploadEmailUserInput.toString())
//            uploadNewUser(
//                uploadUserBinding.uploadNameUserInput.text.toString(),
//                uploadUserBinding.uploadEmailUserInput.toString())
        }

        uploadViewModel.responseUser.observe(this) { response ->
            val text = StringBuilder("Your ID is ").append(response.id)
                .append("\nCreated at : ")
                .append(response.createdAt)
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

        uploadViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun uploadNewUser(name: String, email: String) {
        uploadUserBinding.uploadInProgressTv.visibility = View.VISIBLE
        uploadUserBinding.uploadLoading.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().uploadUser(name, email)
        client.enqueue(object : Callback<UploadUserResponse> {
            override fun onResponse(
                call: Call<UploadUserResponse>,
                response: Response<UploadUserResponse>,
            ) {
                if (response.isSuccessful) {
                    uploadUserBinding.uploadInProgressTv.visibility = View.GONE
                    uploadUserBinding.uploadLoading.visibility = View.GONE
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val text = StringBuilder("Your ID is ")
                            .append(responseBody.id)
                            .append("\nCreated at : ")
                            .append(responseBody.createdAt)
                        Toast.makeText(this@UploadUserActivity, text, Toast.LENGTH_SHORT).show()
                    } else {
                        uploadUserBinding.uploadInProgressTv.visibility = View.GONE
                        uploadUserBinding.uploadLoading.visibility = View.GONE
                        Toast.makeText(this@UploadUserActivity,
                            "Failed to connect to server",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uploadUserBinding.uploadInProgressTv.visibility = View.GONE
                    uploadUserBinding.uploadLoading.visibility = View.GONE
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                    Toast.makeText(this@UploadUserActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<UploadUserResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                Toast.makeText(this@UploadUserActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            uploadUserBinding.uploadInProgressTv.visibility = View.VISIBLE
            uploadUserBinding.uploadLoading.visibility = View.VISIBLE
        } else {
            uploadUserBinding.uploadInProgressTv.visibility = View.GONE
            uploadUserBinding.uploadLoading.visibility = View.GONE
        }

    }

}