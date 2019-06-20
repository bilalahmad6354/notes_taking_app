package com.example.notes.com.example.notes.controllers.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes.com.example.notes.models.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteDBController extends DatabaseController {

    // Class variables
    Note note;
    Context context;

    public NoteDBController(Context context){
        super(context);
        this.note = new Note();
        this.context = context;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public boolean insertNote() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INPUT_COLUMN_Title, this.note.getTitle());
        contentValues.put(INPUT_COLUMN_Text, this.note.getText());
        this.note.setId(String.valueOf(db.insert(INPUT_TABLE_NAME_NOTES, null, contentValues)));
        return true;
    }


    public boolean updateNote(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INPUT_COLUMN_Text, this.note.getText());
        db.update(INPUT_TABLE_NAME_NOTES, contentValues, INPUT_COLUMN_ID + "=" + this.note.getId(), null);
        return true;
    }

    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + INPUT_TABLE_NAME_NOTES, null);
        return res;
    }


    public Cursor getNoteFromDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + INPUT_TABLE_NAME_NOTES + " WHERE " +
                INPUT_COLUMN_ID + "=?", new String[]{this.note.getId()});
        return res;
    }


    public void deleteSingleNote() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INPUT_TABLE_NAME_NOTES, INPUT_COLUMN_ID + "=?", new String[]{this.note.getId()});
    }


    public List<Note> getNotesListFromCursor(Cursor cursor){
        List<Note> dataSourceForNoteList = new ArrayList<>();
        if   (cursor.moveToFirst()) {
            do {
                Note temp = new Note();
                temp.setId(cursor.getString(cursor.getColumnIndex(DatabaseController.INPUT_COLUMN_ID)));
                temp.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseController.INPUT_COLUMN_Title)));
                temp.setText(cursor.getString(cursor.getColumnIndex(DatabaseController.INPUT_COLUMN_Text)));
                dataSourceForNoteList.add(temp);
                // do what you need with the cursor here
            } while (cursor.moveToNext());
        }
        return dataSourceForNoteList;
    }

    public NoteDBController getSingleNoteFromCursor(Cursor cursor){
        NoteDBController temp = new NoteDBController(context);

        if(cursor.moveToFirst()){
            temp.note.setId(cursor.getString(cursor.getColumnIndex(DatabaseController.INPUT_COLUMN_ID)));
            temp.note.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseController.INPUT_COLUMN_Title)));
            temp.note.setText(cursor.getString(cursor.getColumnIndex(DatabaseController.INPUT_COLUMN_Text)));
        }
        return temp;

    }
}
