package com.tyss.service.rag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieverService {

	@Autowired
	private JsonReaderService jsonReaderService;
	
	public String retrieveContext(String question) {
		
		return jsonReaderService.readCandidateProfile();
	}
}
