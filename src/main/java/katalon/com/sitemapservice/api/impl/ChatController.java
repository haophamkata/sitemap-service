package katalon.com.sitemapservice.api.impl;

import katalon.com.sitemapservice.dto.PromptDTO;
import katalon.com.sitemapservice.openapi.dto.ChatRequest;
import katalon.com.sitemapservice.openapi.dto.ChatResponse;
import katalon.com.sitemapservice.utils.PromptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    /**
     * Creates a chat request and sends it to the OpenAI API
     * Returns the first message from the API response
     *
     * @param prompt the prompt to send to the API
     * @return first message from the API response
     */
    @PostMapping("/v1/chat")
    public String chat(@RequestBody PromptDTO prompt)  {
        ChatRequest request = new ChatRequest(model, PromptGenerator.generatePrompt(prompt.getUrls()));
        ChatResponse response = restTemplate.postForObject(
                apiUrl,
                request,
                ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}