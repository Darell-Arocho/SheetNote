package com.example.sketch_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    ImageButton saveNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);

        // Set a click listener for the save note button
        saveNoteBtn.setOnClickListener((v)-> saveNote());
    }

    /**
     * Method called when the user clicks the save note button.
     * Validates the input and saves the note to Firebase if valid.
     */
    void saveNote(){
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();

        // Check that the note has a title
        if(noteTitle==null || noteTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }

        // Create a new Note object with the input data
        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        // Save the note to Firebase
        saveNoteToFireBase(note);
    }

    /**
     * Saves a Note object to Firebase.
     * @param note the note to save
     */
    void saveNoteToFireBase(Note note){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();

        // Save the note to Firebase
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Note is added successfully
                    Utility.showToast(NoteDetailsActivity.this, "Note added successfully");
                    finish();
                }else{
                    // There was an error adding the note
                    Utility.showToast(NoteDetailsActivity.this, "Failed while adding note");
                }
            }
        });
    }
}
