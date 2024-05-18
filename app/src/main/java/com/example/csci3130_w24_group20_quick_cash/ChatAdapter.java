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

/**
 * Adapter class for displaying chat data in a RecyclerView.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatData> chatList;
    private OnChatItemClickListener clickListener;

    FirebaseAuth mAuth;

    public interface OnChatItemClickListener {
        void onChatItemClick(ChatData chatData);
    }


    /**
     * Constructs a ChatAdapter with the given chat list and click listener.
     *
     * @param chatList     The list of chat data to display.
     * @param clickListener The listener for chat item click events.
     */

    public ChatAdapter(List<ChatData> chatList, OnChatItemClickListener clickListener) {
        this.chatList = chatList;
        this.clickListener = clickListener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The type of the new View.
     * @return A new ChatViewHolder that holds a View of the given view type.
     */

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }


    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
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
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
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
         * @param itemView The view representing a single chat item.
         */


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textOtherUserName = itemView.findViewById(R.id.textOtherUserName);
        }
    }
}

