package dev.ray.prompts;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimplePromptController {

    private final ChatClient chatClient;

    public SimplePromptController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("prompt")
    public String simple() {
        return chatClient.call(new Prompt("Tell me a dad joke")).getResult().getOutput().getContent();
    }
}
