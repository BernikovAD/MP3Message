package com.example.mp3message;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            init();
        }
    }

    private void init() {
        //инициализируем recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_item_music);
        TextView emptyView = findViewById(R.id.no_active_jobs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //создаем презентер
        UserPresenterImpl userPresenter = new UserPresenterImpl();
        if(userPresenter.showListMusic().size() == 0){
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            //отправояем команду презентору чтобы тот вернул список файлов и отправляем все в адаптер, чтоб отобразить на экране
            ItemMusicFileAdapter itemMusicFileAdapter = new ItemMusicFileAdapter(userPresenter.showListMusic());
            recyclerView.setAdapter(itemMusicFileAdapter);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_MEDIA) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                init();
            }
        }
    }
}