package com.aneesh.todonotesfinal.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.adapter.BlogsAdapter
import com.aneesh.todonotesfinal.model.JsonResponse

class BlogActivity : AppCompatActivity() {

    lateinit var recyclerViewBlogs : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        onBindView()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse>{
                override fun onResponse(response: JsonResponse?) {
                    setupRecyclerView(response)
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@BlogActivity, "${anError}", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun setupRecyclerView(response: JsonResponse?) {
        val blogsAdapter = BlogsAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this@BlogActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogsAdapter
    }

    private fun onBindView() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }
}