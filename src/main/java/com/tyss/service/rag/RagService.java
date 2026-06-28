package com.tyss.service.rag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RagService {

	
	@Autowired
	private RetrieverService retrieverService;
	
	@Autowired
	private GroqService groqService;
	
	public String askQuestion(String question) {
		
		   String context =
	                retrieverService.retrieveContext(question);

	        String prompt = """
	You are an AI Candidate Profile Assistant.

	Answer ONLY using the candidate profile below.

	If the answer is not present in the profile, politely say that the information is not available.

	Candidate Profile:

	%s

	Interview Question:

	%s
	""".formatted(context, question);

	        return groqService.askGroq(prompt);
	}
}
