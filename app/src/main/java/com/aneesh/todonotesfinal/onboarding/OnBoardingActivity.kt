package com.aneesh.todonotesfinal.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.login.LoginActivity
import com.aneesh.todonotesfinal.data.local.pref.PrefConstant
import com.aneesh.todonotesfinal.data.local.pref.StoreSession

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick{

    lateinit var viewPager : ViewPager2

    companion object{
        private const val FIRST_ITEM = 0
        private const val LAST_ITEM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        onBindViews()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun onBindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = LAST_ITEM
    }

    override fun onOptionBack() {
        viewPager.currentItem = FIRST_ITEM
    }

    override fun onOptionDone() {
        StoreSession.write(PrefConstant.ON_BOARDED_SUCCESSFULLY, true)
        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}