package com.example.postapp.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.DetalhesActivity;
import com.example.postapp.R;

import java.util.List;

public class FavPostAdapter extends RecyclerView.Adapter<FavPostAdapter.FavPostViewHolder>{

    private List<Post> posts;

    public FavPostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    static class FavPostViewHolder extends RecyclerView.ViewHolder {
        TextView id, title, body;
        FavPostViewHolder (View view) {
            super(view);
            id = view.findViewById(R.id.tv_id);
            title = view.findViewById(R.id.tv_titulo);
            body = view.findViewById(R.id.tv_body);
        }
    }

    @Override
    @NonNull
    public FavPostAdapter.FavPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_post_detail, parent, false);
        return new FavPostAdapter.FavPostViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull FavPostAdapter.FavPostViewHolder holder, int position) {
        Post p = posts.get(position);
        holder.id.setText(String.valueOf(p.getId()));
        holder.title.setText(p.getTitle());
        holder.body.setText(p.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
