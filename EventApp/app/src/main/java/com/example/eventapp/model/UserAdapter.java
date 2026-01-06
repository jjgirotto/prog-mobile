package com.example.eventapp.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        UserViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_user_name);
            email = view.findViewById(R.id.tv_user_email);
        }
    }

    @Override
    @NonNull
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_part, parent, false);
        return new UserViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = users.get(position);
        holder.name.setText(u.getName());
        holder.email.setText(u.getEmail());
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
}


