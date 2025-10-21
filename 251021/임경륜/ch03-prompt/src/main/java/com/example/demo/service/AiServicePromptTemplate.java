package com.example.demo.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class AiServicePromptTemplate {
  private ChatClient chatClient;

  private PromptTemplate systemTemplate = SystemPromptTemplate.builder()
      .template("""
            답변을 생성할때 HTML과 css를 사용해서 파란 글자로 출력하세요.
            <span>태그 안에 들어갈 내용만 출력하세요.
          """)
      .build();

  private PromptTemplate userTemplate = PromptTemplate.builder()
      .template("""
          답변을 {language}로  해주세요.
          {statement}

          """)
      .build();

  public AiServicePromptTemplate(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  public Flux<String> promptTemplate1(String language,String statement ) {

    Prompt prompt = userTemplate.create(
        Map.of("language", language,"statement", statement ));

    Flux<String> fluxString = chatClient.prompt(prompt)
        .stream()
        .content();
    return fluxString;
  }

  public Flux<String> promptTemplate2(String language, String statement) {
    Flux<String> response = chatClient.prompt()
    .messages(systemTemplate.createMessage(),
    userTemplate.createMessage(Map.of("language", language,"statement", statement)))
    .stream()
    .content();
    return response;
  }

  public Flux<String> promptTemplate3(String language, String statement) {
    Flux<String> response = chatClient.prompt()
    .system(systemTemplate.render())
    .user(userTemplate.render(Map.of("language", language,"statement", statement)))
    .stream()
    .content();
    return response;
  }

  public Flux<String> promptTemplate4(String language, String statement) {
      String systemText= """
          답변을 생성할때 HTML과 css를 사용해서 파란 글자로 출력하세요.
          <span>태그 안에 들어갈 내용만 출력하세요.
          """;

      String userText="""
          다음 한국어 문장을 %s로 번역해주세요.\n 문장: %s
          """.formatted(language,statement);

     Flux<String> response = chatClient.prompt()
      .system(systemText)
      .user(userText)
      .stream()
      .content();
    return response;
  }


}
