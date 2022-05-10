package com.aneesh.todonotesfinal.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {

        //this function returns the no. of fragments we have for the given activity
        return 2
    }

    override fun getItem(position: Int): Fragment {
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