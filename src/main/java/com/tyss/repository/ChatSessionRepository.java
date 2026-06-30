package com.tyss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.entity.ChatSession;
import com.tyss.entity.User;

@Repository
public interface ChatSessionRepository
        extends JpaRepository<ChatSession,Long>{

    List<ChatSession> findByUserOrderByCreatedAtDesc(User user);

}