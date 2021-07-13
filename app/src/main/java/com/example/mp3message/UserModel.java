package com.example.mp3message;

import android.os.Environment;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

//Считываю данные из директории Download, отбираю только MP3 файлы
public class UserModel {

    //Строка название директории
    private final String path = Environment.getExternalStorageDirectory().toString() + "/Download/";
    File directory = new File(path);

    //Читаем директорию и заполняем List<Name>
    public List<String> readDir() {
        List<String> listMusicName = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (accept(file.getName())) {
                    listMusicName.add(file.getName());
                }
            }
        }
        return listMusicName;
    }

    public String getPath() {
        return path;
    }


    //Проверяем на mp3
    public boolean accept(String name) {
        String mimeType = URLConnection.guessContentTypeFromName(name);
        System.out.println(mimeType);
        return mimeType != null && mimeType.indexOf("audio") == 0;
    }
}


