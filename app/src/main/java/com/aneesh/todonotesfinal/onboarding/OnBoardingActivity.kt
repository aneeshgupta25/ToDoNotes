package com.aneesh.todonotesfinal.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.activity.LoginActivity
import com.aneesh.todonotesfinal.utils.PrefConstant

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick{

    lateinit var viewPager : ViewPager
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        onBindViews()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE)
    }

    private fun onBindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        sharedPreferences.edit().putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY, true).apply()
        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}