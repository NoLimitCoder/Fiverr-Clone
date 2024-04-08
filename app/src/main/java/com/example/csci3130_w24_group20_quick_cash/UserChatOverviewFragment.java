package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.BaseEmployeeActivity;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobDetailsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserChatOverviewFragment extends Fragment implements ChatAdapter.OnChatItemClickListener {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatData> chatList = new ArrayList<>();
    private FirebaseAuth mAuth;

    public UserChatOverviewFragment() {
        // Required empty public constructor
    }

    public static UserChatOverviewFragment newInstance() {;
        return new UserChatOverviewFragment();
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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            fetchChatsForCurrentUser(currentUserId);
        }

        return view;
    }

    private void openChatInstanceFragment(ChatData chatData) {
        ChatInstanceFragment chatInstanceFragment = ChatInstanceFragment.newInstance(chatData);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (getActivity() instanceof BaseEmployeeActivity) {
            transaction.replace(R.id.baseEmployee, chatInstanceFragment);
        } else {
            transaction.replace(R.id.baseEmployer, chatInstanceFragment);
        }
        transaction.addToBackStack("fragment_job_search").commit();
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
        openChatInstanceFragment(chatData);
    }
}
