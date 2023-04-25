package com.example.sketch_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class NoteBooks extends AppCompatActivity {

    FloatingActionButton addNoteBtn;  // Floating action button used to add new notes
    RecyclerView recyclerView;  // RecyclerView used to display notes
    NoteAdapter noteAdapter;  // Adapter for binding the notes to the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_books);

        addNoteBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recycler_view);
        setupRecyclerView();  // Configure the RecyclerView and its adapter

        // Set up an OnClickListener for the addNoteBtn to open the NoteDetailsActivity when clicked
        addNoteBtn.setOnClickListener((v)-> startActivity(new Intent(NoteBooks.this, NoteDetailsActivity.class)));
    }

    // Method to configure the RecyclerView and its adapter
    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();  // Start listening for changes to the notes data
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();  // Stop listening for changes to the notes data
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();  // Notify the adapter that data has changed and should be updated
    }
}
