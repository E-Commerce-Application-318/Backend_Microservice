package com.backend.ddd.application.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Tools {
    @Tool
    public String getAllCarts(UUID userId) {
        return "List of cart";
    }
}
