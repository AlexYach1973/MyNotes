package com.alexyach.geekbrains.android.mynotes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note implements Serializable {

    public static ArrayList<Note> listNote = createList();

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
    private static ArrayList<Note> createList() {
        ArrayList<Note> list = new ArrayList<>();

        list.add(new Note("Зарядка", "Гимнастические упражнения: \n" +
                "Приседания: 20 раз,\nотжимания 20 раз,\nподтягивания: сколько смогу",
                "01.01.2022"));

        list.add(new Note("Футбол", "Просто постоять у ворот\n" +
                "или подавать мячики", "02.01.2022"));

        list.add(new Note("Пиво", "Оттянуться по полной, и за гимнастику и за футбол", "03.01.2022"));

        list.add(new Note("Обучение", "Не забыть сдать вовремя домашку", "04.01.2022"));

        list.add(new Note("Работа", "директор:\nобязательно покритиковать,\n" +
                "коллеги:\n напомнить, что они все дебилы,\n Я- молодец", "05.01.2022"));

        return  list;
    }


}
