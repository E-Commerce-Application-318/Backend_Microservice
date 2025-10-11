package com.backend.ddd.controller.resource;

import com.backend.ddd.application.agent.CustomerSupportAgent;
import com.backend.ddd.application.service.AgentAppService;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import dev.langchain4j.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private CustomerSupportAgent customerSupportAgent;

    @Autowired
    private AgentAppService agentAppService;

    @GetMapping
    public ResponseEntity<String> customerSupportAgent(
            @RequestParam("sessionId") String sessionId,
            @RequestParam("userMessage") String userMessage
    ) {
        try {
            // Call the agent service to get the response
            Result<String> result = customerSupportAgent.answer(sessionId, userMessage);
            return ResponseEntity.ok(result.content());
        } catch (Exception e) {
            // AI service failures
            String errorMessage = "I'm sorry, but I'm currently experiencing technical issues.";
            if (e.getMessage() != null && e.getMessage().contains("overloaded")) {
                errorMessage += "The AI service is temporarily overloaded. Please try again in a few minutes.";
            } else {
                errorMessage += "Please try again later or contact support if the issue persists.";
            }
            return ResponseEntity.status(503).body(errorMessage);
        }
    }

    @GetMapping("/test/get-all-order/{userId}")
    public ResponseEntity<List<ExternalOrderResponse>> getOrder(
            @PathVariable("userId") UUID userId
    ) {
        List<ExternalOrderResponse> externalOrderResponseList = agentAppService.getAllOrdersByUserId(userId);
        return ResponseEntity.ok(externalOrderResponseList);
    }
}
