package com.smart.host.controller.dto;

import com.smart.host.domain.Category;
import com.smart.host.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.smart.host.domain.Category.ECONOMY;
import static com.smart.host.domain.Category.PREMIUM;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionRequest {
    private int economyRooms;
    private int premiumRooms;

    public List<Room> toRooms(){
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(createRooms(economyRooms, ECONOMY));
        rooms.addAll(createRooms(premiumRooms, PREMIUM));
        return rooms;
    }

    private List<Room> createRooms(int size, Category category){
        return IntStream.range(0, size)
            .mapToObj(i -> new Room(category))
            .collect(Collectors.toList());
    }
}
