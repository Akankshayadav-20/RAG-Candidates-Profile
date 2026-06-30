package com.tyss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.tyss.dto.AskRequest;
import com.tyss.dto.AskResponse;
import com.tyss.entity.ChatSession;
import com.tyss.entity.User;
import com.tyss.repository.ChatSessionRepository;
import com.tyss.repository.UserRepository;
import com.tyss.service.ChatService;
import com.tyss.service.rag.RagService;

@RestController
public class ChatController {

    @Autowired
    private RagService ragService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @PostMapping("/ask")
    public AskResponse askQuestion(@RequestBody AskRequest request,
                                   Authentication authentication) {

        String answer = ragService.askQuestion(request.getQuestion());

        User user = getUser(authentication);

        ChatSession session =
                chatSessionRepository.findById(request.getSessionId())
                        .orElse(null);

        if (user != null && session != null) {

            // ✅ SAVE CHAT IN SESSION
            chatService.saveChat(session,
                    request.getQuestion(),
                    answer);

            // ✅ AUTO TITLE (first message becomes title)
            if ("New Chat".equals(session.getTitle())) {
                session.setTitle(request.getQuestion());
                chatSessionRepository.save(session);
            }
        }

        return new AskResponse(answer);
    }

    // ---------------- HELPER ----------------
    private User getUser(Authentication authentication) {

        String email;

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } 
        else if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
            email = oauthUser.getAttribute("email");
        } 
        else {
            email = authentication.getName();
        }

        return userRepository.findByEmail(email).orElse(null);
    }
}