package com.smart.host.controller.dto;

import com.smart.host.domain.Guest;
import com.smart.host.domain.Room;
import com.smart.host.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
@AllArgsConstructor
public class RoomsOccupancyByDTO {
    private Map<Category, RoomsOccupancyDTO> occupancies;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public static RoomsOccupancyByDTO roomsOccupancyByCategoryDTO(List<Room> rooms){
        Map<Category, RoomsOccupancyDTO> occupancies = new HashMap<>();

        Arrays.stream(Category.values()).forEach(category ->
            occupancies.put(category, roomsOccupancyDTOByCategory(rooms, category)));

        return new RoomsOccupancyByDTO(occupancies);
    }

    private static RoomsOccupancyDTO roomsOccupancyDTOByCategory(List<Room> rooms, Category category){
        return rooms.stream()
            .filter(r -> r.getCategory().equals(category) && nonNull(r.getGuest()))
            .map(r -> new RoomsOccupancyDTO(1, guestPriceOrDefault(r.getGuest())))
            .reduce(new RoomsOccupancyDTO(), RoomsOccupancyByDTO::addNewRoomToMap);
    }

    private static RoomsOccupancyDTO addNewRoomToMap(RoomsOccupancyDTO lastRoom, RoomsOccupancyDTO roomsOccupancy){
        roomsOccupancy.sumOccupancy(lastRoom.getOccupancy());
        roomsOccupancy.sumEarning(Float.valueOf(decimalFormat.format(lastRoom.getEarning())));
        return roomsOccupancy;
    }

    private static Float guestPriceOrDefault(Guest guest){
        return isNull(guest) ? 0f : guest.getPrice();
    }
}
