package com.alexyach.geekbrains.android.mynotes.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alexyach.geekbrains.android.mynotes.AboutFragment;
import com.alexyach.geekbrains.android.mynotes.NoteListFragment;
import com.alexyach.geekbrains.android.mynotes.R;
import com.alexyach.geekbrains.android.mynotes.source.DataSource;
import com.alexyach.geekbrains.android.mynotes.source.IDataSource;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements IDataSourceHandler {

    private IDataSource dataSource = new DataSource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NoteListFragment noteListFragment = new NoteListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, noteListFragment)
                    .commit();
        }

        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {

        // Находим DrawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigator_drawer_open,
                R.string.navigator_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                item -> {

                    if (item.getItemId() == R.id.action_drawer_about) {
                        openAboutFragment();
                        drawer.close();
                        return true;
                    }
    
                    return false;
                });
    }

    private void openAboutFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, new AboutFragment())
                .commit();
    }


    @Override
    public IDataSource getDataSource() {
        return dataSource;
    }
}