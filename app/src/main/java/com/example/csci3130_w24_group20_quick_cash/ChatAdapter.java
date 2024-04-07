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

    /**
     * Constructs a new ChatAdapter.
     *
     * @param chatList      The list of chat data to display.
     * @param clickListener The click listener for chat items.
     */

    public ChatAdapter(List<ChatData> chatList, OnChatItemClickListener clickListener) {
        this.chatList = chatList;
        this.clickListener = clickListener;
    }

    /**
     * Inflates the layout for a chat item.
     *
     * @param parent   The parent view group.
     * @param viewType The type of view.
     * @return A new ChatViewHolder instance.
     */

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }


    /**
     * Binds chat data to the view holder.
     *
     * @param holder   The view holder to bind data to.
     * @param position The position of the item in the list.
     */

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

    /**
     * Gets the total number of items in the chat list.
     *
     * @return The total number of chat items.
     */

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * ViewHolder for holding views of each chat item.
     */

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView textJobTitle, textOtherUserName;

        /**
         * Constructs a new ChatViewHolder.
         *
         * @param itemView The view for this view holder.
         */

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textOtherUserName = itemView.findViewById(R.id.textOtherUserName);
        }
    }
}

