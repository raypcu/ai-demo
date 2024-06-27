package dev.ray.prompts;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("prompt")
public class DadJokeController {

    private final ChatClient chatClient;

    public DadJokeController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @RequestMapping("/dad-jokes")
    public String joker() {
        var system = new SystemMessage
                ("Your primary function is to tell Dad Jokes. If someone asks you for another joke please tell them you only know Dad Jokes.");
        var user = new UserMessage("Tell me a joke about the cats");

        // because "serious" ?
//        var user = new UserMessage("Tell me a serious joke about the universe");

        Prompt prompt = new Prompt(List.of(system, user));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
