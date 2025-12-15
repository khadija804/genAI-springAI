package ma.enset.bdcc_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.enset.bdcc_ai.output.Movie;
import ma.enset.bdcc_ai.output.MovieList;

@RestController
public class AIAgentStructuredController {

	private ChatClient chatClient;
	
	public AIAgentStructuredController(ChatClient.Builder chatClient) {
		this.chatClient = chatClient.build();
	}
	
	@GetMapping("/askAgent")
	public MovieList askLLM(String query) {
		String systemMessage ="""
				vous êtes un spécialiste dans le domaine du cinéma
				Répondre à la question de l'utilisateur à ce propos
				""";
		return chatClient.prompt()
				.system(systemMessage)
				.user(query)
				.call()
				.entity(MovieList.class);
	}
	
}
