package com.tutorial.apirestfirebase.controller;

import com.tutorial.apirestfirebase.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<MessageDto> getHello() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new MessageDto("Hello " + username + " from spring boot"));
    }
}
