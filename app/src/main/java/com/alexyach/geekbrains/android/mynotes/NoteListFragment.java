package com.alexyach.geekbrains.android.mynotes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexyach.geekbrains.android.mynotes.activity.IDataSourceHandler;
import com.alexyach.geekbrains.android.mynotes.adapter.NotesAdapter;
import com.alexyach.geekbrains.android.mynotes.adapter.NotesAdapterClickListener;
import com.alexyach.geekbrains.android.mynotes.source.DataSource;
import com.alexyach.geekbrains.android.mynotes.source.IDataSource;

import java.util.List;

public class NoteListFragment extends Fragment implements OnDialogListener {

    private static final String CURRENT_NOTE = "CurrentNote";
    private int currentPosition;

    private NotesAdapter adapter;
    RecyclerView recyclerView;

//    private IDataSource dataSource = new DataSource();
    private IDataSource dataSource;
    List<Note> listNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Передали DataSource через MainActivity
        dataSource = ((IDataSourceHandler) getActivity()).getDataSource();

        // Установка картинки из ChildFragmenta
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.image_child_container, new ImageChildFragment())
                .commit();

        // Восстановление текущей позиции
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        // Свое меню
        setHasOptionsMenu(true);

        // инициализация Adaptera
        initList(view);

        // Кнеопка Добавить
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
    }

    private void addNewNote() {
        dataSource.addNote(new Note("без названия",
                "без описания",
                "без даты"));

        // Оповестили адаптера об изменениях в конце списка
        adapter.notifyItemInserted(dataSource.getNotes().size() - 1);
        // Прокрутили до конца списка
        recyclerView.scrollToPosition(dataSource.getNotes().size() - 1);

    }

    private void initList(View view) {

        listNote = dataSource.getNotes();

        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new  LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        // Adapter
        adapter = new NotesAdapter();
        adapter.setNoteList(listNote);
        adapter.setListener(new NotesAdapterClickListener() {
            // Обычное нажатие
            @Override
            public void onNoteClick(int position) {
                showPortNoteDetails(position);
            }

            // Долгое нажатие
            @Override
            public void onLongItemClick(View view, int position) {
                // Инициаизируем Popup Menu
                initPopup(view, position);
            }
        });

        recyclerView.setAdapter(adapter);

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

        return NoteDetailsFragment.newInstance(listNote.get(index), index);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDialogYes(String str) {
        Toast.makeText(requireActivity(), "Новая дата: " + str, Toast.LENGTH_SHORT).show();
    }

    /** Menu */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_notes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_delete_all) {
            dataSource.removeAll();
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    /** Popup Menu  */
    private void initPopup(View view, int position) {
        Activity activity = requireActivity();
        PopupMenu popupMenu = new PopupMenu(activity, view);
        activity.getMenuInflater().inflate(R.menu.menu_notes_context, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.action_context_delete) {
                    // Удаление элемента
                    dataSource.deleteNote(position);
                    adapter.notifyItemRemoved(position);
                }
                return true;
            }
        });
        popupMenu.show();

    }
}