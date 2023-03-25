package com.example.mynotes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.database.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteTapInterface, NoteTapDeleteInterface {


    lateinit var viewModel: NoteViewModel
    lateinit var notesRecycleView: RecyclerView
    lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRecycleView = findViewById(R.id.notesRecycleView)
        addButton = findViewById(R.id.addButton)

        notesRecycleView.layoutManager = LinearLayoutManager(this)

        val noteRecycleViewAdapter = NoteRecycleViewAdapter(this, this, this)

        notesRecycleView.adapter = noteRecycleViewAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteRecycleViewAdapter.updateList(it)
            }
        })
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNoteTap(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onNoteTapDelete(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}