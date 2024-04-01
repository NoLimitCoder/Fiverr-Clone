package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserChatOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserChatOverview extends Fragment implements ChatAdapter.OnChatItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;

    private List<ChatData> chatList = new ArrayList<>();
    private FirebaseAuth mAuth;

    public UserChatOverview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserChatOverview.
     */
    // TODO: Rename and change types and number of parameters
    public static UserChatOverview newInstance(String param1, String param2) {
        UserChatOverview fragment = new UserChatOverview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_chat_overview, container, false);
        chatRecyclerView = view.findViewById(R.id.chatRecyclerViewer);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatAdapter = new ChatAdapter(chatList, this);
        chatRecyclerView.setAdapter(chatAdapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();;

        if (currentUser != null){
            String currentUserId = currentUser.getUid();
            fetchChatsForCurrentUser(currentUserId);
        }

        return view;
    }

    private void fetchChatsForCurrentUser(String currentUserId) {
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats");

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    ChatData chatData = chatSnapshot.getValue(ChatData.class);
                    if (chatData != null && (chatData.getApplicantUID().equals(currentUserId)
                            || chatData.getEmployerUID().equals(currentUserId))) {
                        chatList.add(chatData);
                    }
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    @Override
    public void onChatItemClick(ChatData chatData) {

    }
}