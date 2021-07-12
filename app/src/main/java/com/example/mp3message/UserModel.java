package com.example.mp3message;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//Считываю данные из директории Download, отбираю только MP3 файлы
public class UserModel {

    //Строка название директории
    private String path = Environment.getExternalStorageDirectory().toString() + "/Download/";
    File directory = new File(path);

    //Читаем директорию и заполняем List<Name>
    public List<String> readDir() {
        File[] files = directory.listFiles();
        List<String> listMusicName = new ArrayList<>();
        for (File file : files) {
            if (accept(directory, file.getName())) { ;
                listMusicName.add(file.getName());
            }
        }
        return listMusicName;
    }

    public String getPath() {
        return path;
    }

    //Проверяем на mp3
    public boolean accept(File dir, String name) {
        if (name.endsWith(".mp3") || name.endsWith(".MP3")) {
            return true;
        }
        directory = new File(dir.getAbsolutePath() + "/" + name);
        return directory.isDirectory();
    }
}


