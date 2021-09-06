package com.example.mp3message;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserActivity extends AppCompatActivity {
    final String titleMessageAlert = "Разрешение на доступ к памяти телефона";
    final String bodyMessageAlert = "Для вашего доступа к музыкальным файлам требуется разрешение на чтение памяти телефона.";
    final String btnAlert = "ПРИНЯТЬ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Считываем в каком состоянии находиться разрешение на чтение памяти
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //Проверяем,если не подходит, то делаем запрос на разрешение
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
        //Считываем лист(название муз.файлов),если равен 0, то делаем невидимым RecyclerView и видимым textView("Файлов нет!")
        if (userPresenter.showListMusic().size() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            //отправояем команду презентору чтобы тот вернул список файлов и отправляем все в адаптер, чтоб отобразить на экране
            ItemMusicFileAdapter itemMusicFileAdapter = new ItemMusicFileAdapter(userPresenter.showListMusic());
            recyclerView.setAdapter(itemMusicFileAdapter);
        }
    }

    /*Переопределяем метод проверки разрешений.
     * Так как SDK_VERSION < 23 проверка происходит при установке приложения, то будем проверять в версия от 23 и выше.
     * Если разрешение на чтение памяти не получено, то вызываем shouldShowRequestPermissionRationale()
     * shouldShowRequestPermissionRationale()  true, если пользователь ранее уже отклонял запрос на предоставление разрешения
     * false если запрос разрешения происходит впервые или если пользователь в ответ на прежний запрос выставил опцию Don't ask again в диалоговом окне запроса
     * Если true то вызовем диалог где уточним пользователю зачем нужен доступ к памяти
     * Если false то откроем настройки приложения
     * Иначе если доступ к памяти получен, идем дальше*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showAlertDialog();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    intent.setData(uri);
                    this.startActivity(intent);
                }
            } else {
                init();
            }
        }
    }

    //Метод вызова диалога, где показано, зачем нужен доступ к памяти
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleMessageAlert)
                .setMessage(bodyMessageAlert)
                .setNegativeButton(btnAlert,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}