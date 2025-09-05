package com.karan.mini_metaverse.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String model = "mistral:latest";

    public AIService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:11434/api")
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String generateEvent(String prompt) {
        try {
            String enhancedPrompt = "Generate a short, creative and exciting game event (1-2 sentences) for a mini metaverse based on this player action: " + prompt;

            String response = webClient.post()
                    .uri("/generate")
                    .bodyValue(Map.of(
                            "model", model,
                            "prompt", enhancedPrompt,
                            "stream", false,
                            "options", Map.of(
                                    "temperature", 0.8,
                                    "max_tokens", 100,
                                    "top_p", 0.9
                            )
                    ))
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(15))
                    .block();

            return parseOllamaResponse(response);

        } catch (Exception e) {
            System.err.println("Error calling local Ollama: " + e.toString());
            return generateFallbackEvent(prompt);
        }
    }

    public Mono<String> generateEventReactive(String prompt) {
        String enhancedPrompt = "Generate a short, creative and exciting game event (1-2 sentences) for a mini metaverse based on this player action: " + prompt;

        return webClient.post()
                .uri("/generate")
                .bodyValue(Map.of(
                        "model", model,
                        "prompt", enhancedPrompt,
                        "stream", false,
                        "options", Map.of(
                                "temperature", 0.8,
                                "max_tokens", 100,
                                "top_p", 0.9
                        )
                ))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(15))
                .map(this::parseOllamaResponse)
                .doOnError(error -> System.err.println("Reactive error: " + error.toString()))
                .onErrorReturn(generateFallbackEvent(prompt));
    }

    private String parseOllamaResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);

            // Check for errors first
            if (root.has("error")) {
                System.err.println("Ollama API Error: " + root.get("error").asText());
                return "AI model is currently unavailable.";
            }

            // Parse successful response
            if (root.has("response")) {
                String generatedText = root.get("response").asText().trim();

                // Clean up the response
                if (generatedText.length() > 300) {
                    generatedText = generatedText.substring(0, 300) + "...";
                }

                // Remove any unwanted formatting
                generatedText = generatedText.replaceAll("\\n+", " ").trim();

                return generatedText.isEmpty() ? generateFallbackEvent("empty_response") : generatedText;
            }

        } catch (Exception e) {
            System.err.println("Error parsing Ollama response: " + e.toString());
        }
        return generateFallbackEvent("parse_error");
    }

    // Fallback method when Ollama is not available
    private String generateFallbackEvent(String prompt) {
        System.out.println("Using fallback event generation for: " + prompt);

        String[] fallbackEvents = {
                "A mysterious portal shimmers into existence, revealing glimpses of distant magical realms!",
                "Ancient runes begin glowing on nearby surfaces, responding to your presence with mystical energy.",
                "A helpful spirit appears and whispers secrets about hidden treasures in the area.",
                "The very air around you sparkles with magic, and you feel your abilities temporarily enhanced!",
                "A shooting star streaks across the sky and lands nearby, transforming into a magical artifact.",
                "Time seems to slow as you enter a pocket of temporal magic, giving you a moment to think clearly.",
                "A gentle breeze carries the songs of ancient bards, filling you with inspiration and courage.",
                "Ethereal lights dance around you, forming patterns that seem to tell a story of great adventures."
        };

        int index = Math.abs(prompt.hashCode()) % fallbackEvents.length;
        return fallbackEvents[index];
    }

    // Method to check if Ollama is running
    public boolean isOllamaAvailable() {
        try {
            webClient.get()
                    .uri("/tags")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(3))
                    .block();
            return true;
        } catch (Exception e) {
            System.err.println("Ollama not available: " + e.toString());
            return false;
        }
    }
}