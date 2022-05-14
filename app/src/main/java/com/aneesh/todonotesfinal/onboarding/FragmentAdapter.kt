package com.aneesh.todonotesfinal.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        //this function returns the no. of fragments we have for the given activity
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                OnBoardingOneFragment()
            }
            1 -> {
                OnBoardingTwoFragment()
            }
            else -> {
                null!!
            }
        }
    }
}