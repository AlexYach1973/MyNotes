package com.alexyach.geekbrains.android.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаем записки
//        listNote = createList();

        if (savedInstanceState == null) {
            NoteListFragment noteListFragment = new NoteListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, noteListFragment)
                    .commit();
        }

    }

}