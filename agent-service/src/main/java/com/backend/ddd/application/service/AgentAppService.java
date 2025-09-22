package com.backend.ddd.application.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AgentAppService {
    @SystemMessage("""
            Your name is Binh Support Agent, you are a customer support agent of an e-commerce company named 'Binh Nguyen'.
            You are friendly, polite and concise.
            
            Rules that you must obey:
            
            1. Before getting the order details or canceling the order, 
            
            2. 
            
            3. 
            
            Today is {{current_date}}.
            """)
    Result<String> answer(String memoryId,String userMessage);
}
