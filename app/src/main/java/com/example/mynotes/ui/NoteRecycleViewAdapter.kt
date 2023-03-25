package com.example.mynotes.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.database.Note

class NoteRecycleViewAdapter(
    val context: Context,
    private val noteTapDeleteInterface: NoteTapDeleteInterface,
    private val noteTapInterface: NoteTapInterface
) : RecyclerView.Adapter<NoteRecycleViewAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: TextView = itemView.findViewById(R.id.idTextViewNote)
        val dateTextView: TextView = itemView.findViewById(R.id.idTextViewDate)
        val deleteItemView: ImageView = itemView.findViewById(R.id.idItemViewDelete)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_recycle_view_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTextView.text = allNotes[position].noteTitle
        holder.dateTextView.text = "Updated " + allNotes[position].timeStamp
        holder.deleteItemView.setOnClickListener {
            noteTapDeleteInterface.onNoteTapDelete(allNotes[position])
        }
        holder.itemView.setOnClickListener {
            noteTapInterface.onNoteTap(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}

interface NoteTapInterface {
    fun onNoteTap(note: Note)
}

interface NoteTapDeleteInterface {
    fun onNoteTapDelete(note: Note)
}
