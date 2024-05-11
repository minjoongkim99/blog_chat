package board.demo.chat.repository;

import board.demo.chat.domain.ChatRoom;


import java.util.List;

public interface RoomRepository {

    ChatRoom findRoomById(String roomId);

    List<ChatRoom> findAllRoom();

    void save(String id, ChatRoom room);
}
