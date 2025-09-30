package com.backend.ddd.controller.resource;

import com.backend.ddd.application.agent.CustomerSupportAgent;
import dev.langchain4j.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private CustomerSupportAgent customerSupportAgent;

    @GetMapping
    public String customerSupportAgent(
            @RequestParam String sessionId,
            @RequestParam String userMessage
    ) {
        // Call the agent service to get the response
        Result<String> result = customerSupportAgent.answer(sessionId, userMessage);
        return result.content();
    }
}
