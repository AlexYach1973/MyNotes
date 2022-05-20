package com.alexyach.geekbrains.android.mynotes;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.List;

public class NoteDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String INDEX = "index";

    private static int currentIndex;

    List<Note> listNote = Note.listNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
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

        DatePicker datePicker = view.findViewById(R.id.date_picker);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String title = getArguments().getString(ARG_PARAM1);
            String description = getArguments().getString(ARG_PARAM2);
            String date = getArguments().getString(ARG_PARAM3);
//            int index = getArguments().getInt(INDEX);

            tvTitle.setText(title);
            tvDescription.setText(description);
            tvDate.setText(date);
        }

        // Установка даты
        datePicker.setOnDateChangedListener((datePicker1, year, month, day) -> {
            String dateString = day + "." + (month + 1) + "." + year;

            // Отобразили
            tvDate.setText(dateString);
            // Сохранили
            listNote.get(currentIndex).setDate(dateString);

            /*Log.d("myLogs",  "NDF currentIndex= " + currentIndex +
                    "\n1: " + listNote.get(0).getDate() +
                    "\n2: " + listNote.get(1).getDate() +
                    "\n3: " + listNote.get(2).getDate() +
                    "\n4: " + listNote.get(3).getDate() +
                    "\n5: " + listNote.get(4).getDate());*/
        });

    }

    /**
     * Use this factory method to create a new instance
     */
    public static NoteDetailsFragment newInstance(String title, String description,
                                                  String date, int index) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, description);
        args.putString(ARG_PARAM3, date);
        args.putInt(INDEX, index);

        currentIndex = index;

        fragment.setArguments(args);
        return fragment;
    }



}