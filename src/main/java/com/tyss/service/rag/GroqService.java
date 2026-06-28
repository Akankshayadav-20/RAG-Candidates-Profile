package com.tyss.service.rag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String askGroq(String prompt) {

        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ),
                "temperature", 0.2
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);
       
        try {
        Map response =
                restTemplate.postForObject(url, request, Map.class);

        List choices = (List) response.get("choices");

        Map choice = (Map) choices.get(0);

        Map message = (Map) choice.get("message");

        return message.get("content").toString();
        
        }catch(Exception e) {
        	  return "Sorry, the AI service is temporarily unavailable. Please try again later";
        }
    }
}