package dev.ray.demo;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("demo")
public class MapObjController {
    private final ChatModel chatModel;

    public MapObjController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/map/joke")
    Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("joke", chatModel.call(message));
    }
}
