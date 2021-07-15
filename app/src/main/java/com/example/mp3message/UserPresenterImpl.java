package com.example.mp3message;

import android.os.Environment;

import java.util.List;

public class UserPresenterImpl implements UserPresenter {

    private final String path = Environment.getExternalStorageDirectory().toString() + "/Download/";
    //Считываем директорию, потом убираем лишние файлы(не audio)
    @Override
    public List<String> showListMusic() {
        UserModel userModel = new UserModel();
        DeleteNotAudio deleteNotAudio = new DeleteNotAudio();
        return deleteNotAudio.accept(userModel.readDir(path));
    }

    public String getPath() {
        return path;
    }

}
