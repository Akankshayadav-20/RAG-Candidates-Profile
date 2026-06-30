package com.tyss.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.entity.ChatHistory;
import com.tyss.entity.ChatSession;
import com.tyss.entity.User;
import com.tyss.repository.ChatRepository;
import com.tyss.repository.ChatSessionRepository;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private ChatSessionRepository chatSessionRepository;
	
	public void saveChat(ChatSession session, String question, String answer) {
		
		ChatHistory chat = new ChatHistory();
		
		chat.setSession(session);
		chat.setQuestion(question);
		chat.setAnswer(answer);
		chat.setCreatedAt(LocalDateTime.now());
		
		chatRepository.save(chat);
		
	}
	
	 // ✅ Get chats of a session
    public List<ChatHistory> getChats(ChatSession session) {

        return chatRepository.findBySessionOrderByCreatedAtAsc(session);
    }

    // ✅ Create new session
    public ChatSession createSession(User user, String title) {

        ChatSession session = new ChatSession();

        session.setUser(user);
        session.setTitle(title);
        session.setCreatedAt(LocalDateTime.now());

        return chatSessionRepository.save(session);
    }

    // ✅ Get all sessions for sidebar
    public List<ChatSession> getUserSessions(User user) {

        return chatSessionRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
