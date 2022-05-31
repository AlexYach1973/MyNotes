package com.alexyach.geekbrains.android.mynotes;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

public class NoteDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "note";

    private static int currentIndex;

    List<Note> listNote = Note.listNote;

    // Дата установки календаря
    String dateString = "";

    // Экземпляр интерфейса, который реализован в NoteListFragment
    OnDialogListener listener;
    // Установим слушатель диалога
    public void setListener(OnDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.text_view_title_details);
        TextView tvDescription = view.findViewById(R.id.text_view_description);
        TextView tvDate = view.findViewById(R.id.text_view_date);

        // Кнопка назад
       view.findViewById(R.id.button_note_detail_back)
               .setOnClickListener(view1 -> showAlertDialog());



        DatePicker datePicker = view.findViewById(R.id.date_picker);

        Bundle bundle = getArguments();

        if (bundle != null) {
            Note note = (Note) getArguments().getSerializable(ARG_PARAM);

            tvTitle.setText(note.getTitle());
            tvDescription.setText(note.getDescribe());
            tvDate.setText(note.getDate());
        }

        // Установка даты
        datePicker.setOnDateChangedListener((datePicker1, year, month, day) -> {
            dateString = day + "." + (month + 1) + "." + year;

            // Отобразили
            tvDate.setText(dateString);
            // Сохранили
            listNote.get(currentIndex).setDate(dateString);

        });

    }

    private void showAlertDialog() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Внимание!")
                .setMessage("Новую дату установили?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Передаем дату
                        if (dateString.isEmpty()) {
                            listener.onDialogYes("Новая дата не установлена");
                        } else {
                            listener.onDialogYes(dateString);
                        }

                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogYes("Новая дата не установлена");
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                })
                .show();
    }

    /**
     * Use this factory method to create a new instance
     */
    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_PARAM, note);

        currentIndex = Note.listNote.indexOf(note);

        Log.d("myLogs", "index= " + Note.listNote.indexOf(note));

        fragment.setArguments(args);
        return fragment;
    }



}