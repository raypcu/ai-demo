package dev.ray.mywondata;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mywondata")
public class OlympicController {

    private final ChatClient chatClient;

    @Value("classpath:prompts/olympic-sports.st")
    private Resource olympicPromptResource;

    @Value("classpath:docs/olympic-sports.txt")
    private Resource docsToStuffResource;

    public OlympicController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/olympic")
    public String get2024OlympicSports(
            @RequestParam(value = "message", defaultValue = "What sports are being included in the 2024 Summer Olympics?") String message,
            @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {

        PromptTemplate promptTemplate = new PromptTemplate(olympicPromptResource);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);
        if(stuffit) {
            map.put("context", docsToStuffResource);
        }else {
            map.put("context", "");
        }

        Prompt prompt = promptTemplate.create(map);
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }
}
