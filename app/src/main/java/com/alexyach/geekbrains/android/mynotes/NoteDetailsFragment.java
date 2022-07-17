package com.alexyach.geekbrains.android.mynotes;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.alexyach.geekbrains.android.mynotes.activity.IDataSourceHandler;
import com.alexyach.geekbrains.android.mynotes.activity.MainActivity;
import com.alexyach.geekbrains.android.mynotes.source.DataSource;
import com.alexyach.geekbrains.android.mynotes.source.IDataSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_NOTE = "note";
    private static final String ARG_PARAM_INDEX = "index";

    private int currentIndex;

    // Переданный экземпляр
    private Note currentNote;

    private IDataSource dataSource;
    private List<Note> listNote;

    EditText tvTitle, tvDescription;

    DatePicker datePicker;

    // Дата установки календаря
    String dateString = "";

    // Экземпляр интерфейса, который реализован в NoteListFragment
    OnDialogListener listener;
    // Установим слушатель диалога
    public void setListener(OnDialogListener listener) {
//        Log.d("myLogs", "NoteDetailsFragment: " + listener.getClass().getSimpleName());
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

        //Передали DataSource через MainActivity
        dataSource = ((IDataSourceHandler) getActivity()).getDataSource();
        listNote = dataSource.getNotes();

        tvTitle = view.findViewById(R.id.text_view_title_details);
        tvDescription = view.findViewById(R.id.text_view_description);
        TextView tvDate = view.findViewById(R.id.text_view_date);

        // Кнопка назад с Диалогом
       view.findViewById(R.id.button_note_detail_back)
               .setOnClickListener(view1 -> showAlertDialog());

       // Кнопка скрытия/открытия DatePicker
        view.findViewById(R.id.button_note_detail_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (datePicker.getVisibility() == View.VISIBLE) {
                    datePicker.setVisibility(View.GONE);
                } else {
                    datePicker.setVisibility(View.VISIBLE);
                }
            }
        });


        datePicker = view.findViewById(R.id.date_picker);

        Bundle bundle = getArguments();

        if (bundle != null) {
            currentNote = (Note) getArguments().getSerializable(ARG_PARAM_NOTE);
            currentIndex = getArguments().getInt(ARG_PARAM_INDEX);

            tvTitle.setText(currentNote.getTitle());
            tvDescription.setText(currentNote.getDescribe());
            tvDate.setText(currentNote.getDate());
        }

        // Установка даты
        datePicker.setOnDateChangedListener((datePicker1, year, month, day) -> {

            // Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            dateString = getDateFromCalendar(calendar);

            // Отобразили
            tvDate.setText(dateString);
            // Сохранили
//            listNote.get(currentIndex).setDate(dateString);

        });

    }

    private String getDateFromCalendar(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Внимание!")
                .setMessage("Сохранить изменения?")
                // ДА
                .setPositiveButton("Да", (dialogInterface, i) -> {

                    // Сохраняем изменения
                    saveChange();

                    // Передаем дату
                    if (dateString.isEmpty()) {
                        listener.onDialogYes("Новая дата не установлена");
                    } else {
                        listener.onDialogYes(dateString);
                    }

                    // Возвращаемся
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                // НЕТ
                .setNegativeButton("Нет", (dialogInterface, i) -> {
                    listener.onDialogYes("Новая дата не установлена");
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .show();
    }

    private void saveChange() {
        dataSource.updateNote(new Note(
                tvTitle.getText().toString(),
                tvDescription.getText().toString(),
                dateString
        ), currentIndex);

    }

    /**
     * Use this factory method to create a new instance
     */
    public static NoteDetailsFragment newInstance(Note note, int index) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_PARAM_NOTE, note);
        args.putInt(ARG_PARAM_INDEX, index);

//        Log.d("myLogs", "index= " + Note.listNote.indexOf(note));

        fragment.setArguments(args);
        return fragment;
    }



}