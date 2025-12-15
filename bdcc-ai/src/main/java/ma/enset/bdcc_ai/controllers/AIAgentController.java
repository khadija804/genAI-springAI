package ma.enset.bdcc_ai.controllers;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIAgentController {

	private ChatClient chatClient;

	public AIAgentController(ChatClient.Builder builder, ChatMemory memory) {
		this.chatClient = builder
				.defaultAdvisors(new SimpleLoggerAdvisor())
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
				.build();
	}

	@GetMapping("/chat")
	public String askLLM(String query) {
		List<Message> examples = List.of(
				new UserMessage("6+4"),
				new AssistantMessage("result : 10")
				);
		return chatClient.prompt()
				.system("Répond toujours au majuscule")
				.messages(examples)
				.user(query)
				.call()
				.content();
	}
}
