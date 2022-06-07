package com.alexyach.geekbrains.android.mynotes.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexyach.geekbrains.android.mynotes.Note;
import com.alexyach.geekbrains.android.mynotes.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> noteList;

    private NotesAdapterClickListener listener;

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public void setListener(NotesAdapterClickListener listener) {
//        Log.d("myLogs", "Adapter " + listener.getClass());
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        // Заполняем item в ViewHolder-e
        holder.bindHolder(position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // ViewHolder
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvDescription, tvDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.view_text_item_title);
            tvDescription = itemView.findViewById(R.id.view_text_item_description);
            tvDate = itemView.findViewById(R.id.view_text_item_date);

            // Listener. Передали текущую заметку
            itemView.setOnClickListener(view ->
                    listener.onNoteClick(getAdapterPosition()));

            // Долгое касание
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongItemClick(view, getAdapterPosition());
                    return true;
                }
            });
        }

        // Заполняем item
        void bindHolder(int position) {
            tvTitle.setText(noteList.get(position).getTitle());
            tvDescription.setText(noteList.get(position).getDescribe());
            tvDate.setText(noteList.get(position).getDate());
        }

    }

}
