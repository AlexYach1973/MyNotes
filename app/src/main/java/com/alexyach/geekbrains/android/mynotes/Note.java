package com.alexyach.geekbrains.android.mynotes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note implements Serializable {

    public static List<Note> listNote = createList();

    private String title;
    private String describe;
    private String date;

    public Note(String title, String describe, String date) {
        this.title = title;
        this.describe = describe;
        this.date = date;

    }

    public String getTitle() {
        return title;
    }

    public String getDescribe() {
        return describe;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    // Создание заметок
    private static List<Note> createList() {
        List<Note> list = new ArrayList<>();

        list.add(new Note("Title 1", "description 1", "01.01.2022"));
        list.add(new Note("Title 2", "description 2", "02.01.2022"));
        list.add(new Note("Title 3", "description 3", "03.01.2022"));
        list.add(new Note("Title 4", "description 4", "04.01.2022"));
        list.add(new Note("Title 5", "description 5\n description 5\n " +
                "description 5\n description 5\n", "05.01.2022"));

        return  list;
    }


}
