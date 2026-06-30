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

				   Answer ONLY using the information from the Candidate Profile.

				   If the answer is not available in the profile, reply exactly:

				   <b>Information Not Available</b><br>
				   Sorry, this information is not available in the candidate profile.

				   IMPORTANT RULES:

				   1. Never invent information.
				   2. Never answer in one long paragraph.
				   3. Format every answer using HTML.
				   4. Use <h3> for headings.
				   5. Use <ul><li> for bullet points.
				   6. Use <ol><li> for numbered lists.
				   7. Use <b> to highlight important labels.
				   8. Use <br><br> between sections.
				   9. Do NOT use Markdown (#, ##, *, **, etc.).
				   10. Return only HTML that can be displayed directly inside a web page.

				   Example 1:

				   <h3>About Me</h3>

				   <ul>
				   <li><b>Name:</b> Akanksha Yadav</li>
				   <li><b>Education:</b> B.Tech</li>
				   <li><b>Career Goal:</b> Associate Software Engineer</li>
				   </ul>

				   Example 2:

				   <h3>Technical Skills</h3>

				   <ul>
				   <li>Java</li>
				   <li>Spring Boot</li>
				   <li>SQL</li>
				   <li>Docker</li>
				   <li>Git</li>
				   </ul>

				   Example 3:

				   <h3>Projects</h3>

				   <ol>
				   <li>
				   <b>Student Management System</b>

				   <ul>
				   <li><b>Technologies:</b> Spring Boot, Spring Security, MySQL</li>
				   <li><b>Features:</b> Authentication, CRUD, Dashboard</li>
				   <li><b>Outcome:</b> Secure student management system</li>
				   </ul>

				   </li>

				   <li>
				   <b>Candidate Profile AI Agent</b>

				   <ul>
				   <li><b>Technologies:</b> Spring Boot, Groq API, Docker</li>
				   <li><b>Features:</b> RAG-based profile assistant</li>
				   <li><b>Outcome:</b> Answers interview questions using profile data</li>
				   </ul>

				   </li>

				   </ol>

				   Candidate Profile:

				   %s

				   Interview Question:

				   %s
				   """.formatted(context, question);
		   
	        return groqService.askGroq(prompt);
	}
}
