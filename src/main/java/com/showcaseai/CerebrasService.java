package com.showcaseai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CerebrasService {

    @Value("${cerebras.api.key}")
    private String apiKey;

    private final String CEREBRAS_URL = "https://api.cerebras.ai/v1/chat/completions";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String enhanceProfessionalSummary(String resumeText) {
        String prompt = String.format("""
            Transform this resume into a compelling professional summary that stands out to employers.
            Make it concise, impactful, and highlight key achievements. Remove weak language and add strong action words.
            Focus on results and quantifiable achievements.

            Original Resume:
            %s

            Generate ONLY the enhanced professional summary (2-3 sentences max), no extra text:
            """, resumeText);

        return callCerebrasAPI(prompt);
    }

    // You can implement similar methods for enhancing skills, projects, work experience, etc.

    private String callCerebrasAPI(String prompt) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CEREBRAS_URL);

            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3.1-8b");

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);
            requestBody.put("messages", new Map[]{message});

            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                JsonNode jsonResponse = objectMapper.readTree(responseBody);
                return jsonResponse.path("choices").get(0)
                        .path("message").path("content").asText();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "Error generating enhanced content. Please try again.";
        }
    }
}
