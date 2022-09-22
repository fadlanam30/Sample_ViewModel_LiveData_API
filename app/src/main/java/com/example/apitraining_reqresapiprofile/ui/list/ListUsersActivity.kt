package com.example.apitraining_reqresapiprofile.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apitraining_reqresapiprofile.ui.user.UserActivity
import com.example.apitraining_reqresapiprofile.data.remote.response.DataItem
import com.example.apitraining_reqresapiprofile.databinding.ActivityListUsersBinding

class ListUsersActivity : AppCompatActivity() {

    private lateinit var listUsersActivityBinding: ActivityListUsersBinding

    private val listUserViewModel by viewModels<ListUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listUsersActivityBinding = ActivityListUsersBinding.inflate(layoutInflater)
        setContentView(listUsersActivityBinding.root)

//        listUserViewModel = ViewModelProvider(this)[ListUserViewModel::class.java]

        listUsersActivityBinding.listUsersRv.setHasFixedSize(true)

        listUserViewModel.listUser.observe(this) {
            showRecyclerView(it)
        }

        listUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

//    private fun searchList(){
//        listUsersActivityBinding.listUserLoading.visibility = View.VISIBLE
//        val client = ApiConfig.getApiService().getUsersList("3")
//        client.enqueue(object : Callback<ListUserResponse> {
//            override fun onResponse(
//                call: Call<ListUserResponse>,
//                response: Response<ListUserResponse>
//            ) {
//                listUsersActivityBinding.listUserLoading.visibility = View.GONE
//                if (response.isSuccessful){
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        showRecyclerView(responseBody.data)
//                    }
//                } else {
//                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
//                listUsersActivityBinding.listUserLoading.visibility = View.GONE
//                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    private fun showRecyclerView(data : List<DataItem>){
        val rvAdapter = ListUsersAdapter(data)
        listUsersActivityBinding.listUsersRv.layoutManager = GridLayoutManager(this,2)
        listUsersActivityBinding.listUsersRv.adapter = rvAdapter

        rvAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(userData: DataItem) {
                val intentToDetail = Intent(this@ListUsersActivity, UserActivity::class.java)
                intentToDetail.putExtra(RV_ITEM_ID,userData)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        listUsersActivityBinding.listUserLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



    companion object {
        const val RV_ITEM_ID = "RV_ITEM_ID"
    }
}