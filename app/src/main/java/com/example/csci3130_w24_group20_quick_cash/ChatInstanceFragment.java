package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatInstanceFragment extends Fragment {

    private static final String ARG_CHAT_DATA = "chatData";
    private ChatData chatData;

    private RecyclerView chatRecyclerView;
    private EditText chatMessageET;

    private MessageAdapter messageAdapter;
    List<ChatMessage> messages = new ArrayList<>();


    FirebaseAuth mAuth;
    private Button chatSendBtn;

    public ChatInstanceFragment() {
        // Required empty public constructor
    }

    /**
     * A fragment representing a chat instance between users.
     */




    /**
     * Factory method to create a new instance of this fragment.
     *
     * @param chatData The chat data to initialize the fragment with.
     * @return A new instance of ChatInstanceFragment.
     */

    public static ChatInstanceFragment newInstance(ChatData chatData) {
        ChatInstanceFragment fragment = new ChatInstanceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHAT_DATA, chatData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuthSingleton.getInstance();
        if (getArguments() != null) {
            chatData = (ChatData) getArguments().getSerializable(ARG_CHAT_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_instance, container, false);

        // Initialize views
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        chatMessageET = view.findViewById(R.id.chatMessageET);
        chatSendBtn = view.findViewById(R.id.chatSendBtn);

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chatRecyclerView.setLayoutManager(layoutManager);

         messageAdapter = new MessageAdapter(messages);
        chatRecyclerView.setAdapter(messageAdapter);
         retrieveMessages();
        // Setup button click listener
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        return view;
    }

    /**
     * Sends the message typed by the user.
     */

    private void sendMessage() {
        String messageText = chatMessageET.getText().toString().trim();
        String UID = mAuth.getCurrentUser().getUid();
        String senderName;
        if (UID.equals(chatData.getApplicantUID())) {
            senderName = chatData.getApplicantName();
        } else {
            senderName = chatData.getEmployerName();
        }

        // Check if message is not empty
        if (!messageText.isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(senderName, messageText, System.currentTimeMillis());
            DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(chatData.getChatID()).child("messages");
            chatRef.push().setValue(chatMessage);

            chatMessageET.setText("");
        } else {
            // Show a toast message if the message is empty
            Toast.makeText(getContext(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves the message typed by the user.
     */

    private void retrieveMessages() {
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(chatData.getChatID()).child("messages");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear(); // Clear the current list of messages
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        messages.add(chatMessage);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

}
