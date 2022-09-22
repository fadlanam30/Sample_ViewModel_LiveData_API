package com.example.apitraining_reqresapiprofile.data.remote.retrofit

import com.example.apitraining_reqresapiprofile.data.remote.response.ListUserResponse
import com.example.apitraining_reqresapiprofile.data.remote.response.SearchUserResponse
import com.example.apitraining_reqresapiprofile.data.remote.response.UploadUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsersList(
        @Query("page") page: String
    ): Call<ListUserResponse>

    @GET("users/{userid}")
    fun getUserById(
        @Path("userid") userid:String
    ): Call<SearchUserResponse>


    @FormUrlEncoded
    @POST("users")
    fun uploadUser(
        @Field("name") name: String,
        @Field("job") job: String
     ): Call<UploadUserResponse>
}