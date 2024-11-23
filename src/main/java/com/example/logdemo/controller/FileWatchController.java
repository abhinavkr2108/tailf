package com.example.logdemo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.logdemo.models.Message;

@Controller
public class FileWatchController {
    @MessageMapping("/logs")
    @SendTo("/topic/log")
    public Message getMessage(Message message) {
        return message;
    }
}
