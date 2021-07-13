package com.example.mp3message;

import java.util.List;

public class UserPresenterImpl implements UserPresenter {

    @Override
    public List<String> showListMusic() {
        UserModel userModel = new UserModel();
        return userModel.readDir();
    }
}
