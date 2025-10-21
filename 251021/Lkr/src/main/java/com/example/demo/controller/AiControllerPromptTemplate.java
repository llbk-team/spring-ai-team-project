package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AiServicePromptTemplate;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@Slf4j
public class AiControllerPromptTemplate {

  @Autowired
  private AiServicePromptTemplate aiService;

  // ##### 요청 매핑 메소드 #####
  @PostMapping(
      value = "/prompt-template", 
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
      produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<String> promptTemplate(@RequestParam("language") String language,
      @RequestParam("statement") String statement
      ) {


    // Flux<String> response = aiService.promptTemplate1(language,statement);
    // Flux<String> response = aiService.promptTemplate2(language,statement);
     //Flux<String> response = aiService.promptTemplate3(language,statement);
    Flux<String> response = aiService.promptTemplate4(language,statement);
   
    
    return response;
  }

}
