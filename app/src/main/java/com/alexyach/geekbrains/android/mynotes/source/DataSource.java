package com.alexyach.geekbrains.android.mynotes.source;

import com.alexyach.geekbrains.android.mynotes.Note;

import java.util.ArrayList;
import java.util.List;

public class DataSource implements IDataSource {

    private ArrayList<Note> list = new ArrayList<>();

    // Constructor
    public DataSource() {
        initList();
    }

    private void initList() {
        // Создание заметок
        list = Note.listNote;
    }

    @Override
    public ArrayList<Note> getNotes() {
            return  list;
    }

    @Override
    public void addNote(Note note) {
        list.add(note);
    }

    @Override
    public void updateNote(Note note, int position) {
        list.remove(position);
        list.add(position, note);
    }

    @Override
    public void deleteNote(int position) {
        list.remove(position);
    }

    @Override
    public void removeAll() {
        list.clear();
    }
}
