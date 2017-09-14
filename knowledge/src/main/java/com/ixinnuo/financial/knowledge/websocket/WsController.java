package com.ixinnuo.financial.knowledge.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage welcome(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }
    
    @MessageMapping("/sendMsg")
    @SendTo("/topic/chatroom")
    public ResponseMessage sendMsg(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage(message.getName());
    }
}
