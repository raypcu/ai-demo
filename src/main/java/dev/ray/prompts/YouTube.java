package dev.ray.prompts;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/youtube")
public class YouTube {

    private final ChatClient chatClient;

    @Value("classpath:prompts/youtube.st")
    private Resource ytPromptResource;

    public YouTube(ChatClient.Builder builder) {
        this.chatClient = builder.build();
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
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
