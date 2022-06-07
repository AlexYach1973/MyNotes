package com.alexyach.geekbrains.android.mynotes.adapter;

import android.view.View;

public interface NotesAdapterClickListener {
    void onNoteClick(int position);
    void onLongItemClick(View view, int position);
}
