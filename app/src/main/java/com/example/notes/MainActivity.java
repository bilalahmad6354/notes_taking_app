package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.com.example.notes.adapters.NotesListAdapter;
import com.example.notes.com.example.notes.constants.Constants;
import com.example.notes.com.example.notes.controllers.database.NoteDBController;
import com.example.notes.com.example.notes.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    // Interface variables
    @BindView(R.id.fabAddNote) public FloatingActionButton fabAddNote;
    @BindView(R.id.rvNotesList) public RecyclerView rvNotesList;

    // Class related variables
    NoteDBController notesORM;
    List<Note> dataSourceForNoteList = new ArrayList<>();
    NotesListAdapter notesListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        notesORM = new NoteDBController(this);
        dataSourceForNoteList.clear();
        dataSourceForNoteList = notesORM.getNotesListFromCursor(notesORM.getAllNotes());
        notesListAdapter= new NotesListAdapter(this, dataSourceForNoteList);
        rvNotesList.setAdapter(notesListAdapter);
        notesListAdapter.notifyDataSetChanged();
        rvNotesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        registerAllOnClickListeners();

    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        syncDataSourceWithDatabase();
    }

    @Override
    public  void onResume() {
        syncDataSourceWithDatabase();
        notesListAdapter.notifyDataSetChanged();
        super.onResume();
    }


    private void registerAllOnClickListeners() {

        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                showDialogueForFileName();
            }
        });
    }

    private void showDialogueForFileName() {
        final EditText etNoteName = new EditText(this);
        etNoteName.setHint(getString(R.string.text_enter_filename));
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_dialogue_title_for_filename))
                .setMessage(getString(R.string.text_enter_filename_description))
                .setView(etNoteName)
                .setPositiveButton(getString(R.string.text_dialogue_positive_button_for_a_new_note), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String noteName = etNoteName.getText().toString();
                        dialog.dismiss();
                        handleCreateNote(noteName);
                    }
                })
                .setNegativeButton(getString(R.string.text_dialogue_negative_button_for_a_new_note), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public void handleCreateNote(String noteName){

        if(noteName.isEmpty()){
            Toast.makeText(MainActivity.this, getString(R.string.error_note_name_empty), Toast.LENGTH_SHORT).show();
        }else {
            notesORM.getNote().setTitle(noteName);
            notesORM.insertNote();

            Intent intent = new Intent(MainActivity.this, WriteNotesActivity.class);
            intent.putExtra("noteId" , notesORM.getNote().getId());
            syncDataSourceWithDatabase();
            notesListAdapter.notifyDataSetChanged();
            startActivityForResult(intent, Constants.RESULT_OK);
        }
    }
    public void syncDataSourceWithDatabase(){
        dataSourceForNoteList.clear();
        dataSourceForNoteList = notesORM.getNotesListFromCursor(notesORM.getAllNotes());
        notesListAdapter.setNotesList(dataSourceForNoteList);
    }

}
