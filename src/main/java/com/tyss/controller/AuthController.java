package com.tyss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tyss.dto.RegisterRequest;
import com.tyss.entity.ChatHistory;
import com.tyss.entity.ChatSession;
import com.tyss.entity.User;
import com.tyss.repository.ChatSessionRepository;
import com.tyss.repository.UserRepository;
import com.tyss.service.ChatService;
import com.tyss.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    // ---------------- HOME ----------------
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "html/login";
    }

    @GetMapping("/register")
    public String register() {
        return "html/register";
    }

    // ---------------- CHAT HOME (NEW CHAT) ----------------
    
    @GetMapping("/chat")
    public String chat(Authentication authentication, Model model) {

        User user = getUser(authentication);

        if (user != null) {

            model.addAttribute("user", user);

            // sidebar sessions
            List<ChatSession> sessions =
                    chatService.getUserSessions(user);

            model.addAttribute("sessions", sessions);

            ChatSession currentSession;

            // 👉 IMPORTANT FIX
            if (sessions == null ||sessions.isEmpty()) {
                currentSession = chatService.createSession(user, "New Chat");
                
                if(currentSession == null) {
                	throw new RuntimeException("Session creation failed");
                }
            } else {
                currentSession = sessions.get(0); // latest session optional
                
                if(currentSession == null) {
                	currentSession = chatService.createSession(user, "New Chat");
                }
            }

            model.addAttribute("currentSession", currentSession);

            List<ChatHistory> chats =
                    chatService.getChats(currentSession);

            model.addAttribute("chatHistory", chats);
        }

        return "html/chat";
    }
    
    
    @PostMapping("/chat/new")
    @ResponseBody
    public String newChat(Authentication authentication) {

        User user = getUser(authentication);

        ChatSession session =
                chatService.createSession(user, "New Chat");

        return session.getId().toString();
    }
    
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "html/forgot-password";
    }

    // ---------------- OPEN EXISTING SESSION ----------------
    @GetMapping("/chat/{sessionId}")
    public String openSession(@PathVariable Long sessionId,
                              Authentication authentication,
                              Model model) {

        User user = getUser(authentication);

        if (user != null) {

            model.addAttribute("user", user);

            List<ChatSession> sessions =
                    chatService.getUserSessions(user);

            model.addAttribute("sessions", sessions);

            ChatSession currentSession =
                    chatSessionRepository.findById(sessionId).orElse(null);

            // 🔐 SAFETY CHECK (VERY IMPORTANT)
            if (currentSession == null ||
                !currentSession.getUser().getId().equals(user.getId())) {
                return "redirect:/chat";
            }

            model.addAttribute("currentSession", currentSession);

            List<ChatHistory> chats =
                    chatService.getChats(currentSession);

            model.addAttribute("chatHistory", chats);
        }

        return "html/chat";
    }

    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    public String register(RegisterRequest request) {

        String result = userService.register(request);

        if (result.equals("Registration Successful")) {
            return "redirect:/login";
        }

        return "redirect:/register";
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