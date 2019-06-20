package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.com.example.notes.constants.Constants;
import com.example.notes.com.example.notes.controllers.database.NoteDBController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WriteNotesActivity extends AppCompatActivity {

    // Interface variables
    @BindView(R.id.etNoteText) public EditText etNoteText;
    NoteDBController notesORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);
        ButterKnife.bind(this);

        notesORM = new NoteDBController(this);
        notesORM.getNote().setId(getIntent().getStringExtra("noteId"));
        notesORM = notesORM.getSingleNoteFromCursor(notesORM.getNoteFromDB());
        setTitle(notesORM.getNote().getTitle());
        etNoteText.setText(notesORM.getNote().getText());
        etNoteText.setSelection(etNoteText.length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_write_notes_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean returnValue = this.updateNote();
        Intent returnIntent = new Intent();
        setResult(Constants.RESULT_OK, returnIntent);
        finish();
        return returnValue;
    }

    public boolean updateNote(){
        notesORM.getNote().setText(etNoteText.getText().toString());
        if(notesORM.updateNote()){
            return true;
        }else{
            Toast.makeText(this, getString(R.string.error_creating_note), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
