package com.example.mp3message;

import java.util.List;

public class UserPresenterImpl implements UserPresenter {

    private UserModel userModel;

    @Override
    public List<String> showListMusic() {
        userModel = new UserModel();
        return userModel.readDir();
    }
}
