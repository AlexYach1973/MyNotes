package com.alexyach.geekbrains.android.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class NoteListFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";
    private int currentPosition;

    List<Note> listNote = Note.listNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Восстановление текущей позиции
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        // инициализация списка
        initList(view);

        // Отображение выбраной ранее записки
        if (isLandscape()) {
            showLandNoteDetails(currentPosition);
        }
    }

    private void initList(View view) {

        LinearLayout layout = (LinearLayout) view;

        for (int i = 0; i < listNote.size(); i++) {

            String title = listNote.get(i).getTitle();
            TextView tv = new TextView(getContext());

            tv.setText(title);
            tv.setTextSize(40);
            tv.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            layout.addView(tv);

            final int position = i;
            tv.setOnClickListener(v -> {
                currentPosition = position;
                showNoteDetails(position);
            });
        }
    }

    private void showNoteDetails(int index) {
        if (isLandscape()) {
            showLandNoteDetails(index);
        } else {
            showPortNoteDetails(index);
        }
    }


    private void showPortNoteDetails(int index) {
        // Создаем новый фрагмент
        NoteDetailsFragment detailFragment = createDetailFragment(index);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                // добавили в BackStack
                .addToBackStack(null)
                // Анимация
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // Показываем детали заметки в ландшафтной ориентации
    private void showLandNoteDetails(int index) {

        // Создаем новый фрагмент
        NoteDetailsFragment detailFragment = createDetailFragment(index);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_details, detailFragment)
                // Анимация
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // Создаем новый фрагмент
    private NoteDetailsFragment createDetailFragment(int index) {

        Log.d("myLogs", "NF index= " + index);

        return NoteDetailsFragment.newInstance(listNote.get(index), index);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);

    }

    // Проверка конфигурации
    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}