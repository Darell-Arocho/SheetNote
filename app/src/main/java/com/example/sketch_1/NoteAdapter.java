package com.example.sketch_1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

// This class extends FirestoreRecyclerAdapter, which is a RecyclerView adapter that binds data from a Firestore query to a RecyclerView.
public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {

    Context context;

    // Constructor takes FirestoreRecyclerOptions object and a context as parameters.
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    // This method is called by RecyclerView to display the data at the specified position.
    // It sets the title, content, and timestamp of the note at the specified position to the corresponding views in the ViewHolder.
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titleTextView.setText(note.title);
        holder.contentTextView.setText(note.content);
        holder.timestampTextView.setText(Utility.timestampToString(note.timestamp));

        // When a user clicks on a note, this code starts a new MainActivity with the title and content of the note, as well as the document ID for the note.
        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });

        // When the user clicks on the delete note button, this code deletes the note from Firestore and displays a toast indicating whether the deletion was successful or not.
        holder.itemView.findViewById(R.id.delete_note_text_view_btn).setOnClickListener((v)->{
            String docId = this.getSnapshots().getSnapshot(position).getId();
            DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
            documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //note is deleted
                        Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        //note not deleted
                        Toast.makeText(context, "Note could not be deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    // This method is called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    // It inflates the recycler_note_item.xml layout and returns a new NoteViewHolder object that contains references to the TextViews in the layout.
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        // Declare the TextViews for the title, content and timestamp of the note
        TextView titleTextView, contentTextView, timestampTextView;

        // Constructor for the NoteViewHolder class
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find the title, content and timestamp TextViews from the itemView layout
            titleTextView = itemView.findViewById(R.id.notebook_title_text_view);
            contentTextView = itemView.findViewById(R.id.notebook_content_text_view);
            timestampTextView = itemView.findViewById(R.id.notebook_timestamp_text_view);
        }
    }
}
