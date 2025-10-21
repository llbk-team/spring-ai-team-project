package com.example.demo.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class AiService {
    private ChatClient chatClient;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public Flux<String> promptTemplate1(String language, String statement) {
        Prompt prompt = userTemplate.create(Map.of("language", language, "statement", statement));
        Flux<String> fluxString = chatClient.prompt(prompt)
        .stream()
        .content();

        return fluxString;
    }

    private PromptTemplate systemTemplate = SystemPromptTemplate.builder()
            .template("""
                    답변을 생성할 때 HTML와 CSS를 사용해서 파란 글자로 출력하세요.
                    <span> 태그 안에 들어갈 내용만 출력하세요.""")
            .build();

    public PromptTemplate userTemplate = PromptTemplate.builder()
            .template("다음 한국어 문장을 {language}로 번역해 주세요. \n 문장: {statement}")
            .build();
}

