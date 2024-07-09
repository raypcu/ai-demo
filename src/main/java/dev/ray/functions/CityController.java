package dev.ray.functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.FunctionMessage;
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
@RequestMapping
public class CityController {
    private final ChatClient chatClient;

    public CityController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("You are a helpful AI Assistant answering questions about cities around the world.")
                .defaultFunctions("currentWeatherFunction")
                .build();
    }

    @GetMapping("/cities")
    public String cityFaq(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
