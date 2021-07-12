package com.example.mp3message;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class ItemMusicFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<String> items;
    private static final String MESSAGE = "\nФайл скачан с zaycev.net";
    private UserModel userModel = new UserModel();

    public ItemMusicFileAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false)) {
        };
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int index) {
        TextView name = holder.itemView.findViewById(R.id.name_music_file);
        name.setText(String.format("%s", this.items.get(index)));
        holder.itemView.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(userModel.getPath() + items.get(index));
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("audio/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(v.getContext(), "com.example.mp3message.provider", file);
                intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                try {
                    holder.itemView.getContext().startActivity(Intent.createChooser(intent, MESSAGE));
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "Не отправилось!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}

