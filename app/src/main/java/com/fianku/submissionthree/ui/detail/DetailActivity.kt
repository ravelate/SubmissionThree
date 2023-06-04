package com.fianku.submissionthree.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fianku.submissionthree.Adapter.FollowPagerAdapter
import com.fianku.submissionthree.ui.FavViewModelFactory
import com.fianku.submissionthree.R
import com.fianku.submissionthree.Response.UserResponse
import com.fianku.submissionthree.database.Favorite
import com.fianku.submissionthree.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _activityDetailBinding: ActivityDetailBinding?= null
    private lateinit var detailViewModel: DetailViewModel
    private val binding get() = _activityDetailBinding
    private var check: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        detailViewModel = obtainViewModel(this@DetailActivity)
        val data = intent.getStringExtra(USER_DATA)

        data?.let { detailViewModel.getUsersData(it) }
        detailViewModel.users.observe(this, {
            setDetailUser(it)
        })
        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        supportActionBar?.elevation = 0f
        val followPagerAdapter = FollowPagerAdapter(this, data)
        binding?.vpViewPager?.adapter = followPagerAdapter
        binding?.let {
            TabLayoutMediator(it.tTabs, it.vpViewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        binding?.fabAdd?.setOnClickListener {
            val favorite = Favorite()
            favorite.let {
                it.login = detailViewModel.users.value?.login
                it.url = detailViewModel.users.value?.htmlUrl
                it.avatarUrl = detailViewModel.users.value?.avatarUrl
            }
            check = detailViewModel.insert(favorite)
            if (check as Boolean){
                showToast(getString(R.string.added))
            }else{
                showToast(getString(R.string.failedadded))
            }
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }
    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }
    private fun setDetailUser(user: UserResponse) {
        binding?.let {
            Glide.with(it.ivProfile.context)
                .load("${user.avatarUrl}")
                .circleCrop()
                .into(it.ivProfile)
        }
        binding?.tvUsername?.text = user.login
        binding?.tvName?.text = user.name
        binding?.tvCompany?.text = user.company?.toString() ?: "-"
        val fllwers = this.resources.getString(R.string.tab_text_1, user.followers)
        val fllwing = this.resources.getString(R.string.tab_text_2, user.following)
        binding?.tTabs?.getTabAt(0)?.text = fllwers
        binding?.tTabs?.getTabAt(1)?.text = fllwing

    }
    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val USER_DATA = "UsersResponseItem"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}