package dev.ray.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final ChatClient chatClient;

    public DemoController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @RequestMapping("/usermsg")
    public String usermsg(@RequestParam(value = "message",
            defaultValue = "Tell me a serious joke about cat.") String message) {
        Message userMessage = new UserMessage(message);

        Prompt prompt = new Prompt(List.of(userMessage));
        return chatClient.prompt(prompt).call().content();
    }

    @RequestMapping("/sysmsg")
    public String sysmsg(@RequestParam(value = "message") String message) {
        Message systemMessage = new SystemMessage
                ("Your primary function is to tell Dog Jokes. " +
                        "If someone asks you for another joke please tell them you only know Dog Jokes.");
        Message userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient.prompt(prompt).call().content();
    }
}
