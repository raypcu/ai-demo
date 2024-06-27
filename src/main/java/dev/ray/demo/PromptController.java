package dev.ray.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("demo/prompt")
public class PromptController {

    private final ChatClient chatClient;

    @Value("classpath:prompts/youtube.st")
    private Resource ytPromptResource;

    public PromptController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("popular")
    public String findPopularYouTubersByGenre(@RequestParam( value = "genre", defaultValue = "tech") String genre) {
//        String message = """
//                List 10 of the most popular YouTubers in {genre} along with their current subscriber counts. If you don't know the answer,
//                just say "I don't know".
//                """;
//
//        PromptTemplate promptTemplate = new PromptTemplate(message);

        PromptTemplate promptTemplate = new PromptTemplate(ytPromptResource);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        return chatClient.prompt(prompt).call().content();
    }

    @RequestMapping("/joke")
    public String joke() {
        var system = new SystemMessage
                ("Your primary function is to tell Dad Jokes. If someone asks you for another joke please tell them you only know Dad Jokes.");
        var user = new UserMessage("Tell me a joke about the cats");

        // because "serious" ?
//        var user = new UserMessage("Tell me a serious joke about the universe");

        Prompt prompt = new Prompt(List.of(system, user));
        return chatClient.prompt(prompt).call().content();
    }

    @RequestMapping("/joke2")
    public String joke2() {
        Message systemMessage = new SystemMessage
                ("Your primary function is to tell Dad Jokes. If someone asks you for another joke please tell them you only know Dad Jokes.");

        Message userMessage = new UserMessage("Tell me a serious joke about the universe");

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient.prompt(prompt).call().content();
    }

    @RequestMapping("/joke3")
    public String joke3() {
        String systemText = "Your primary function is to tell Dad Jokes. If someone asks you for another joke please tell them you only know Dad Jokes.";

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage();

        Message userMessage = new UserMessage("Tell me a serious joke about the universe");

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient.prompt(prompt).call().content();
    }
}
