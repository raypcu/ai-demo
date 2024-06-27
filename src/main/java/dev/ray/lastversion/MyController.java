package dev.ray.lastversion;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lastversion")
class MyController {

    private final ChatClient chatClient;

    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/dadjoke")
    String generation(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}