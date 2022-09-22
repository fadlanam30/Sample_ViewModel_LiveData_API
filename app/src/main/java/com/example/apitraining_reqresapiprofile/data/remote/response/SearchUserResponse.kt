package com.example.apitraining_reqresapiprofile.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchUserResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("support")
	val support: Supports
)

@Parcelize
data class Data(

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("email")
	val email: String
):Parcelable

data class Supports(

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("url")
	val url: String
)
