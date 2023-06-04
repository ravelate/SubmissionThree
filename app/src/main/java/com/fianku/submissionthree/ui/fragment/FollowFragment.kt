package com.fianku.submissionthree.ui.fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fianku.submissionthree.Adapter.UsersAdapter
import com.fianku.submissionthree.Response.UsersResponseItem
import com.fianku.submissionthree.api.ApiConfig
import com.fianku.submissionthree.databinding.FragmentFollowBinding
import com.fianku.submissionthree.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment(private val data: String?,private val follow: String?) : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater,container,  false)
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollowersUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollowersUser.addItemDecoration(itemDecoration)

        getFollowersData(data)
        return binding.root
    }


    private fun getFollowersData(login: String?) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowersUser(login.toString(),follow.toString())
        client.enqueue(object : Callback<List<UsersResponseItem>> {
            override fun onResponse(
                call: Call<List<UsersResponseItem>>,
                response: Response<List<UsersResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val adapter = response.body()?.let { UsersAdapter(it) }
                    binding.rvFollowersUser.adapter = adapter
                    adapter?.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: UsersResponseItem) {
                            data.login?.let { showSelectedHero(it) }
                        }
                    })
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UsersResponseItem>>, t: Throwable) {
                showLoading(true)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
    private fun showSelectedHero(hero: String) {
        val moveIntent = Intent(activity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.USER_DATA, hero)
        startActivity(moveIntent)
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}