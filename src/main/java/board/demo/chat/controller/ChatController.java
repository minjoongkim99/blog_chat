package board.demo.chat.controller;


import board.demo.chat.domain.ChatRoom;
import board.demo.chat.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @RequestMapping("/chat/chatList")
    public String chatList(Model model){
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList",roomList);
        return "chat/chatList";
    }


    @GetMapping("/chat/createRoom")
    public String createRoom(Model model) {
        // 세션에서 사용자 ID 가져오기
        String username = getLoggedInUsername();
        System.out.println("username = " + username);
        //Long memberId = (Long) session.getAttribute("memberId");
        model.addAttribute("memberName", username);

        // 채팅방 목록 등 필요한 정보를 모델에 추가
        // model.addAttribute("roomList", chatService.findAllRooms());
        System.out.println("username = " + username);
        return "chat/chatRoom";
    }


    @PostMapping("/chat/createRoom")  //방을 만들었으면 해당 방으로 가야지.
    public String createRoom(Model model, @RequestParam String name) {
        ChatRoom room = chatService.createRoom(name);
        String username = getLoggedInUsername();
        System.out.println("username IS " + username);
        model.addAttribute("room",room);
        model.addAttribute("username",username);
        return "chat/chatRoom";  //만든사람이 채팅방 1빠로 들어가게 됩니다
    }

    @GetMapping("/chat/chatRoom")
    public String chatRoom(Model model, @RequestParam String roomId){
        ChatRoom room = chatService.findRoomById(roomId);
        String username = getLoggedInUsername();
        model.addAttribute("room",room);   //현재 방에 들어오기위해서 필요한데...... 접속자 수 등등은 실시간으로 보여줘야 돼서 여기서는 못함
        model.addAttribute("username",username);
        return "chat/chatRoom";
    }

    private String getLoggedInUsername() { // 현재 로그인한 사용자의 이름을 반환.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}