package com.example.postapp.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.DetalhesActivity;
import com.example.postapp.MainActivity;
import com.example.postapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView id, title;
        PostViewHolder (View view) {
            super(view);
            id = view.findViewById(R.id.tv_post_id_api);
            title = view.findViewById(R.id.tv_post_titulo_api);
        }
    }

    @Override
    @NonNull
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_postapi, parent, false);
        return new PostViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post p = posts.get(position);
        holder.id.setText(String.valueOf(p.getId()));
        holder.title.setText(p.getTitle());
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent i = new Intent(context, DetalhesActivity.class);
            i.putExtra(DetalhesActivity.VALUE_ID, p.getId());
            i.putExtra(DetalhesActivity.VALUE_USERID, p.getUserId());
            i.putExtra(DetalhesActivity.VALUE_TITLE, p.getTitle());
            i.putExtra(DetalhesActivity.VALUE_BODY, p.getBody());
            context.startActivity(i);
        });
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }

}
