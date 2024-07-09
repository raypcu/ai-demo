package dev.ray.functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2")
public class CityV2Controller {
    private final ChatClient chatClient;

    public CityV2Controller(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/cities")
    public String cities(@RequestParam("message") String message) {
        SystemMessage systemMessage = new SystemMessage("You are a helpful AI assistant answering questions about cities around the world.");
        UserMessage userMessage = new UserMessage(message);
        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage, userMessage)));
        return response.getResult().getOutput().getContent();
    }
}
