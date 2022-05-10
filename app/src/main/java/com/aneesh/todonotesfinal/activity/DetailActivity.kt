package com.aneesh.todonotesfinal.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.aneesh.todonotesfinal.R

class DetailActivity : AppCompatActivity() {

    lateinit var textViewActivityTitle : TextView
    lateinit var textViewActivityDescription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        onBindView()
        setupIntentData()
    }

    private fun setupIntentData() {
        val intent = intent
        textViewActivityTitle.text = intent.getStringExtra("title")
        textViewActivityDescription.text = intent.getStringExtra("description")
    }

    private fun onBindView() {
        textViewActivityTitle = findViewById(R.id.textViewActivityTitle)
        textViewActivityDescription = findViewById(R.id.textViewActivityDescription)
    }
}