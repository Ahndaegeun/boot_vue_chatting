package com.example.vue.controller;

import com.example.vue.vo.SocketVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/receive")
    @SendTo("/send")
    public SocketVO socketHandler(SocketVO socketVO) {
        return new SocketVO(socketVO.getUserName(), socketVO.getContent());
    }
}
