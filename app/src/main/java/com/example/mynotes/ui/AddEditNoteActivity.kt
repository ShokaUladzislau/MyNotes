package com.example.mynotes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.R
import com.example.mynotes.database.Note
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitle: EditText
    lateinit var noteText: EditText
    lateinit var saveButton: Button
    lateinit var viewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)


        //actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        noteTitle = findViewById(R.id.idEditNoteTitle)
        noteText = findViewById(R.id.idEditNote)
        saveButton = findViewById(R.id.idSaveButton)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteText = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId", -1)
            saveButton.text = "Update Note"
            this.noteTitle.setText(noteTitle)
            this.noteText.setText(noteText)
        } else {
            saveButton.text = "Save Note"
        }

        saveButton.setOnClickListener {

            val noteTitle = noteTitle.text.toString()
            val noteText = noteText.text.toString()
            // on below line we are checking the type
            // and then saving or updating the data.
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteText.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteText, currentDateAndTime)
                    updatedNote.id = noteId
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteText.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteText, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}