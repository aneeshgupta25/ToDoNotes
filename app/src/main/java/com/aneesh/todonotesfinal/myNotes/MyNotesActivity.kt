package com.aneesh.todonotesfinal.myNotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.aneesh.todonotesfinal.NotesApp
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.addNotes.AddNotesActivity
import com.aneesh.todonotesfinal.blog.BlogActivity
import com.aneesh.todonotesfinal.detail.DetailActivity
import com.aneesh.todonotesfinal.myNotes.adapter.NotesAdapter
import com.aneesh.todonotesfinal.myNotes.clicklisteners.ClickListener
import com.aneesh.todonotesfinal.data.local.db.NotesEntity
import com.aneesh.todonotesfinal.data.local.pref.PrefConstant
import com.aneesh.todonotesfinal.data.local.pref.StoreSession
import com.aneesh.todonotesfinal.utils.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {
    private lateinit var fullName : String
    private lateinit var fabAddNotes : FloatingActionButton
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var listNotes : ArrayList<NotesEntity>
    private lateinit var notesAdapter : NotesAdapter
    private val ADD_NOTES_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        onBindView()
        setUpSharedPreferences()
        getDataFromDatabase()
        setUpRecyclerView()
        setupToolBar()
        clickListners()
        setUpWorkManager()
    }

    private fun setUpWorkManager() {
        val constraint = Constraints.Builder().build()
        val request = PeriodicWorkRequest
            .Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun clickListners() {
        fabAddNotes.setOnClickListener{

            val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
            startActivityForResult(intent, ADD_NOTES_CODE)
        }
    }

    private fun setUpRecyclerView() {
        val clickListener = object : ClickListener {
            override fun onClick(notes: NotesEntity) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra("title", notes.title)
                intent.putExtra("description", notes.description)
                startActivity(intent)
            }

            override fun onUpdate(note: NotesEntity) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(note)
            }
        }

        notesAdapter = NotesAdapter(listNotes, clickListener)
        recyclerViewNotes.adapter = notesAdapter
        recyclerViewNotes.layoutManager = LinearLayoutManager(this@MyNotesActivity)
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        listNotes.addAll(notesDao.getAll())
    }

    private fun addNotesToDb(note: NotesEntity) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(note)
    }

    private fun onBindView() {
        listNotes = arrayListOf()
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setupToolBar() {
        val intent = intent
        fullName = intent.getStringExtra(PrefConstant.FULL_NAME).toString()
        Log.d("MyNotesActivity", StoreSession.readString(PrefConstant.FULL_NAME).toString())
        Log.d("MyNotesActivity", fullName.isNullOrEmpty().toString())
        if(fullName == "null"){
            Log.d("MyNotesActivity", fullName)
            fullName = StoreSession.readString(PrefConstant.FULL_NAME)
            Log.d("MyNotesActivity", fullName)
        }
        supportActionBar?.title = fullName
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            if(requestCode == ADD_NOTES_CODE){
                val title = data?.getStringExtra("title")
                val description = data?.getStringExtra("description")
                val imagePath = data?.getStringExtra("image_path")
                val note = NotesEntity(title = title!!, description = description!!, imagePath = imagePath!!)
                addNotesToDb(note)
                listNotes.add(note)
                notesAdapter.notifyItemInserted(listNotes.size - 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.blog){
            Log.d("Aneesh", "menu is clicked")
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}