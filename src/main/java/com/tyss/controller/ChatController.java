package com.tyss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.dto.AskRequest;
import com.tyss.dto.AskResponse;
import com.tyss.service.rag.RagService;

@RestController
public class ChatController {

	@Autowired
	private RagService ragService;
	
	@PostMapping("/ask")
	public AskResponse askQuestion(@RequestBody AskRequest request) {
		
		String answer = ragService.askQuestion(request.getQuestion());
		
		return new AskResponse(answer);
	}
}
