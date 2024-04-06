package com.example.csci3130_w24_group20_quick_cash;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.UUID;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatData> chatList;
    private OnChatItemClickListener clickListener;

    FirebaseAuth mAuth;

    public interface OnChatItemClickListener {
        void onChatItemClick(ChatData chatData);
    }

    public ChatAdapter(List<ChatData> chatList, OnChatItemClickListener clickListener) {
        this.chatList = chatList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        ChatData chat = chatList.get(position);

        mAuth = FirebaseAuthSingleton.getInstance();
        String UID = mAuth.getCurrentUser().getUid();
        holder.textJobTitle.setText(chat.getJobTitle());
        if (UID.equals(chat.getApplicantUID())) {
            holder.textOtherUserName.setText(chat.getEmployerName());
        } else {
            holder.textOtherUserName.setText(chat.getApplicantName());
        }
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onChatItemClick(chat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView textJobTitle, textOtherUserName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textOtherUserName = itemView.findViewById(R.id.textOtherUserName);
        }
    }
}

