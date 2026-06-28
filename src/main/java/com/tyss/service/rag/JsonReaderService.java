package com.tyss.service.rag;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class JsonReaderService {
	
	public String readCandidateProfile() {
		
		try {
			ClassPathResource resource = new ClassPathResource("data/candidate-profile.json");
			
			byte[] bytes = resource.getInputStream().readAllBytes();
			
			return new String(bytes, StandardCharsets.UTF_8);
		}catch(IOException e) {
			
			throw new RuntimeException("Unable to read candidate profile", e);
		}
	}

}
