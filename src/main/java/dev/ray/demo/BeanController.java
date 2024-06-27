package dev.ray.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("demo")
public class BeanController {

    private final ChatClient chatClient;

    public BeanController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/actor")
    String actor() {
        ActorFilms actorFilms = chatClient.prompt()
                .user("Generate the filmography for a random actor.")
                .call()
                .entity(ActorFilms.class);
        return actorFilms.toString();
    }

    @GetMapping("/actors")
    String actors() {
        List<ActorFilms> actorFilms = chatClient.prompt()
                .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                });
        return actorFilms.toString();
    }

    @GetMapping("/stream")
    String stream() {
        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorFilms>>() {
        });

        Flux<String> flux = this.chatClient.prompt()
                .user(u -> u.text("""
                            Generate the filmography for a random actor.
                            {format}
                          """)
                        .param("format", converter.getFormat()))
                .stream()
                .content();

        String content = flux.collectList().block().stream().collect(Collectors.joining());

        List<ActorFilms> actorFilms = converter.convert(content);
        return actorFilms.toString();
    }

    @GetMapping("/map/joke2")
    Map<String, String> joke2(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of(
                "joke2",
                chatClient.prompt().user(message).call().content()
        );
    }

    @GetMapping("/map/joke3")
    Map<String, String> joke3(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of(
                "joke3",
                // call method is deprecated (since = "1.0.0 M1"), use prompt instead
                chatClient.call(message)
        );
    }

}
