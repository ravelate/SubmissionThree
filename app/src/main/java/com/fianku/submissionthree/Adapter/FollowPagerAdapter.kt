package com.fianku.submissionthree.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fianku.submissionthree.ui.fragment.FollowFragment

class FollowPagerAdapter(activity: AppCompatActivity, private val data: String?)
    : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowFragment(data,"followers")
            1 -> fragment = FollowFragment(data,"following")
        }
        return fragment as Fragment
    }

}