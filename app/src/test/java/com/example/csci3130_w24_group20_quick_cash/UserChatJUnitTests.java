package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserChatJUnitTests {
    private ChatData chatData;
    private List<ChatMessage> messages;

    @Before
    public void setUp() {
        // Create a sample ChatData object
        chatData = new ChatData("jobID123", "employer123", "applicant123", "John Doe", "Employer Inc.", "Software Engineer");

        // Create sample chat messages
        messages = new ArrayList<>();
        messages.add(new ChatMessage("John Doe", "Hello, how are you?", System.currentTimeMillis()));
        messages.add(new ChatMessage("Employer Inc.", "Hi John, I'm doing well. How about you?", System.currentTimeMillis() + 1000));
    }

    @Test
    public void testChatData() {
        assertNotNull(chatData);
        assertEquals("jobID123", chatData.getJobID());
        assertEquals("employer123", chatData.getEmployerUID());
        assertEquals("applicant123", chatData.getApplicantUID());
        assertEquals("John Doe", chatData.getApplicantName());
        assertEquals("Employer Inc.", chatData.getEmployerName());
        assertEquals("Software Engineer", chatData.getJobTitle());
    }

    @Test
    public void testChatMessage() {
        assertNotNull(messages);
        assertEquals(2, messages.size());

        // Check the first message
        ChatMessage firstMessage = messages.get(0);
        assertEquals("John Doe", firstMessage.getSenderName());
        assertEquals("Hello, how are you?", firstMessage.getMessageContent());

        // Check the second message
        ChatMessage secondMessage = messages.get(1);
        assertEquals("Employer Inc.", secondMessage.getSenderName());
        assertEquals("Hi John, I'm doing well. How about you?", secondMessage.getMessageContent());
    }

    @Test
    public void testChatAdapter() {
        List<ChatData> messages2;
        messages2 = new ArrayList<>();
        messages2.add(new ChatData("jobID123", "employer123", "applicant123", "John Doe", "Employer Inc.", "Software Engineer"));
        messages2.add(new ChatData("jobID124", "employer1243", "applicant1423", "Jane Doe", "Employee Inc.", "Software Engineering"));
        ChatAdapter.OnChatItemClickListener clickListener = null;
        clickListener = new ChatAdapter.OnChatItemClickListener() {
            @Override
            public void onChatItemClick(ChatData chatData) {
                return;
            }
        };
        ChatAdapter chatAdapter = new ChatAdapter(messages2, clickListener);
        assertNotNull(chatAdapter);
        assertEquals(2, chatAdapter.getItemCount());
    }

    @Test
    public void testChatInstanceFragment() {
        // Assuming dependencies are properly mocked or stubbed
        ChatInstanceFragment chatInstanceFragment = ChatInstanceFragment.newInstance(chatData);

        assertNotNull(chatInstanceFragment);
        // You can add more assertions based on your implementation
    }
}
