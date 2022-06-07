package com.alexyach.geekbrains.android.mynotes.source;

import com.alexyach.geekbrains.android.mynotes.Note;

import java.util.ArrayList;

public interface IDataSource {

    ArrayList<Note> getNotes();

    void addNote(Note note);
    void updateNote(Note note, int position);
    void deleteNote(int position);

    void removeAll();

}
