package com.example.notes.com.example.notes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notes.R;
import com.example.notes.WriteNotesActivity;
import com.example.notes.com.example.notes.controllers.database.NoteDBController;
import com.example.notes.com.example.notes.models.Note;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>{

    // Activity Related Variables
    private Context context;
    private List<Note> notesList = new ArrayList<>();

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public NotesListAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_list, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNoteText.setText(note.getText());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {


        // View holder interface variables
        @BindView(R.id.tvNoteTitle) TextView tvNoteTitle;
        @BindView(R.id.tvNoteText) TextView tvNoteText;
        @BindView(R.id.btnEditNote) Button btnEditNote;
        @BindView(R.id.btnDeleteNote) Button btnDeleteNote;

        public NoteViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setAllClickListners();
        }

        public void setAllClickListners(){
            btnEditNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WriteNotesActivity.class);
                    intent.putExtra("noteId" , notesList.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

            btnDeleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note noteToDelete = notesList.get(getAdapterPosition());
                    NoteDBController noteDbControoler = new NoteDBController(context);
                    noteDbControoler.setNote(noteToDelete);
                    noteDbControoler.deleteSingleNote();
                    notesList.remove(noteToDelete);
                    notifyDataSetChanged();
                    Toast.makeText(context, context.getString(R.string.text_note_deleted), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
