package com.smart.host.controller;

import com.smart.host.controller.dto.DistributionRequest;
import com.smart.host.controller.dto.RoomsOccupancyByDTO;
import com.smart.host.domain.Room;
import com.smart.host.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.smart.host.controller.dto.RoomsOccupancyByDTO.roomsOccupancyByCategoryDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smart/host/room")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/suggest/distribution")
    public RoomsOccupancyByDTO suggestRoomDistribution(@RequestBody DistributionRequest request){
        List<Room> suggestions = roomService.suggestRoomGuests(request.toRooms());
        return roomsOccupancyByCategoryDTO(suggestions);
    }
}
