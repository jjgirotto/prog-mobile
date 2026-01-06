package com.example.eventapp.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView user, text, date;
        MessageViewHolder(View view) {
            super(view);
            user = view.findViewById(R.id.tv_user);
            text = view.findViewById(R.id.tv_text);
            date = view.findViewById(R.id.tv_date);
        }
    }

    @Override
    @NonNull
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_api, parent, false);
        return new MessageAdapter.MessageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.user.setText(message.getUsername());
        holder.text.setText(message.getText());
        holder.date.setText(message.getFormattedDate());
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }
}
