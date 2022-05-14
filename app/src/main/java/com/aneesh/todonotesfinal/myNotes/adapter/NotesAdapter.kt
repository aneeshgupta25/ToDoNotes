package com.aneesh.todonotesfinal.myNotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.myNotes.clicklisteners.ClickListener
import com.aneesh.todonotesfinal.data.local.db.NotesEntity
import com.bumptech.glide.Glide

class NotesAdapter(private val list : ArrayList<NotesEntity>, private val clickListener : ClickListener) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    class NotesViewHolder(view : View): RecyclerView.ViewHolder(view){
        val textViewTitle : TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = view.findViewById(R.id.textViewDescription)
        val checkBoxMarkStatus : CheckBox = view.findViewById(R.id.checkBoxMarkStatus)
        val imageView : ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item_view, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = list[position]
        holder.textViewTitle.text = note.title
        holder.textViewDescription.text = note.description
        holder.checkBoxMarkStatus.isChecked = note.isTaskCompleted
        Glide.with(holder.itemView).load(note.imagePath).into(holder.imageView )

        holder.itemView.setOnClickListener{
            clickListener.onClick(note)
        }
        holder.checkBoxMarkStatus.setOnCheckedChangeListener { _, isChecked ->
            note.isTaskCompleted = isChecked
            clickListener.onUpdate(note)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}