package com.vibecoding.aiassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendPrompt(String prompt) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
            post.addHeader("Authorization", "Bearer " + openAiApiKey);
            post.addHeader("Content-Type", "application/json");

            String systemMsg = "You are a helpful coding tutor. Answer clearly with examples, short explanations, and code snippets when applicable. Focus on Java, SQL, and HTML.";
            String body = objectMapper.writeValueAsString(new Object() {
                public final String model = "gpt-3.5-turbo";
                public final Object[] messages = new Object[] {
                        new Object() { public final String role = "system"; public final String content = systemMsg; },
                        new Object() { public final String role = "user"; public final String content = prompt; }
                };
                public final double temperature = 0.2;
                public final int max_tokens = 800;
            });

            post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));

            try (InputStream responseStream = client.execute(post).getEntity().getContent()) {
                JsonNode root = objectMapper.readTree(responseStream);
                JsonNode choice = root.path("choices").get(0);
                if (choice != null) {
                    JsonNode message = choice.path("message");
                    String content = message.path("content").asText();
                    return content;
                } else {
                    return "No response from AI";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
