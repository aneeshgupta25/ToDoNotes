package com.aneesh.todonotesfinal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.model.Data
import com.bumptech.glide.Glide

class BlogsAdapter(private val list : List<Data>) : RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder>(){

    inner class BlogsViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        val imageViewBlog : ImageView = view.findViewById(R.id.imageViewBlog)
        val textViewTitle : TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = view.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_layout, parent, false)
        return BlogsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogsViewHolder, position: Int) {
        val blog = list[position]
        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageViewBlog)
        Log.d("Aneesh Aneesh", "${blog.title}")
        Log.d("Aneesh Aneesh", "${blog.description}")
        Log.d("Aneesh Aneesh", "${position}")
        Log.d("Aneesh Aneesh", "${itemCount}")
        Log.d("Aneesh Aneesh", "${holder.textViewTitle.visibility}")
        holder.textViewTitle.text = blog.title
        holder.textViewDescription.text = blog.description
    }

    override fun getItemCount(): Int {
        return list.size
    }

}