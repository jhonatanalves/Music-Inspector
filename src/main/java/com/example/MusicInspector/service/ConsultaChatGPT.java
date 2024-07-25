package com.example.MusicInspector.service;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class ConsultaChatGPT {

    private String apiKey;

    public ConsultaChatGPT (String apiKey) {
        this.apiKey = apiKey;
    }

    public static String consultar(String prompt) {
        OpenAiChatModel model = OpenAiChatModel.withApiKey(System.getenv("OPENAI_APIKEY"));

        return model.generate(prompt);

    }
}