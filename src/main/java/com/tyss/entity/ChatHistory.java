package com.tyss.entity;



import jakarta.persistence.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "chat_history")
@Data
public class ChatHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	 // Logged-in user
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
	
	 // Chat belongs to a session
	
    @ManyToOne
    @JoinColumn(name = "session_id")
    private ChatSession session;

    @Column(length = 1000)
    private String question;

    @Column(length = 5000)
    private String answer;

    private LocalDateTime createdAt;
	
}
