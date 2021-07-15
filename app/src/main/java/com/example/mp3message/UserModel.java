package com.example.mp3message;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Считываю данные из директории
public class UserModel {
    //Читаем директорию и заполняем List<String>
    public List<File> readDir(String path) {
        File directory = new File(path);
        List<File> listMusicName = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) listMusicName.addAll(Arrays.asList(files));
        return listMusicName;
    }
}


