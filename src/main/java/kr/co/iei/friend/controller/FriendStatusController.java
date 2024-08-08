package kr.co.iei.friend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.co.iei.friend.service.FriendStatusService;

@RestController
@RequestMapping("/api")
public class FriendStatusController {

    @Autowired
    private FriendStatusService friendStatusService;

    @GetMapping("/friend-status")
    public String getFriendStatus(Model model) {
        return "api/friend-status";
    }
}
