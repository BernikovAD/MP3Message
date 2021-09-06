package com.example.mp3message;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

//Узнает является ли тип MIME == audio
public class DeleteNotAudio {
    private String mimeType;

    public List<String> accept(List<File> str) {
        List<String> result = new ArrayList<>();
        for (File file : str) {
            mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType != null && mimeType.indexOf("audio") == 0) {
                result.add(file.getName());
            }
        }
        return result;
    }
}
