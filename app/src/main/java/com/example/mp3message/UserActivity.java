package com.example.mp3message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemMusicFileAdapter itemMusicFileAdapter;
    private UserPresenterImpl userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        //инициализируем recyclerView
        recyclerView = findViewById(R.id.recycler_view_item_music);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //создаем презентер
        userPresenter = new UserPresenterImpl();
        //отправояем команду презентору чтобы тот вернул список файлов и отправляем все в адаптер, чтоб отобразить на экране
        itemMusicFileAdapter = new ItemMusicFileAdapter(userPresenter.showListMusic());
        recyclerView.setAdapter(itemMusicFileAdapter);
    }

}