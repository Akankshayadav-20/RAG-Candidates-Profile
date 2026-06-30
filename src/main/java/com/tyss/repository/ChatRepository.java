package com.tyss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.entity.ChatHistory;
import com.tyss.entity.ChatSession;


public interface ChatRepository extends JpaRepository<ChatHistory, Long> {
	List<ChatHistory> findBySessionOrderByCreatedAtAsc(ChatSession session);

	
}
