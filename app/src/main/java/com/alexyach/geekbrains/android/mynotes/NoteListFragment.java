package com.alexyach.geekbrains.android.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class NoteListFragment extends Fragment implements OnDialogListener {

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

        // Установка картинки из ChildFragmenta
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.image_child_container, new ImageChildFragment())
                .commit();

        // Восстановление текущей позиции
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        // инициализация списка
        initList(view);

    }

    private void initList(View view) {

        LinearLayout layout = (LinearLayout) view;

        for (int i = 0; i < listNote.size(); i++) {

            String title = listNote.get(i).getTitle();
            TextView tv = new TextView(getContext());

            tv.setText(title);
            tv.setTextSize(40);
            tv.setTextColor(getResources().getColor(R.color.purple_500));
            tv.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            layout.addView(tv);

            final int position = i;
            tv.setOnClickListener(v -> {
                currentPosition = position;
                showPortNoteDetails(position);
            });
        }
    }

    private void showPortNoteDetails(int index) {
        // Создаем новый фрагмент
        NoteDetailsFragment detailFragment = createDetailFragment(index);
        // Передали прослушиватель
        detailFragment.setListener(this);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                // добавили в BackStack
                .addToBackStack(null)
                // Анимация
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // Создаем новый фрагмент
    private NoteDetailsFragment createDetailFragment(int index) {

        Log.d("myLogs", "NF index= " + index);

        return NoteDetailsFragment.newInstance(listNote.get(index));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDialogYes(String str) {

        Toast.makeText(requireActivity(), "Новая дата: " + str, Toast.LENGTH_LONG).show();
    }
}