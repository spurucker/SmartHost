package com.smart.host.service;

import com.smart.host.domain.Category;
import com.smart.host.domain.Guest;
import com.smart.host.domain.Room;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.smart.host.domain.Category.ECONOMY;
import static com.smart.host.domain.Category.PREMIUM;
import static java.lang.Math.min;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class RoomService {
    private final GuestService guestService;

    public List<Room> suggestRoomGuests(List<Room> availableRooms){
        Map<Category, List<Room>> roomsByCategory = fromRoomListToMapByCategory(availableRooms);
        Map<Category, Queue<Guest>> guestByCategory = guestService.getGuestMapByCategory();

        int availableUpgrades = availableUpgrades(roomsByCategory, guestByCategory);

        setPremiumGuests(roomsByCategory, guestByCategory);
        setUpgradedEconomicGuests(roomsByCategory, guestByCategory, availableUpgrades);
        setEconomicGuests(roomsByCategory, guestByCategory, availableUpgrades);

        List<Room> result = new ArrayList<>();
        roomsByCategory.values()
            .forEach(result::addAll);
        return result;
    }

    private Map<Category, List<Room>> fromRoomListToMapByCategory(List<Room> availableRooms){
        Map<Category, List<Room>> roomsByCategory = availableRooms.stream()
            .collect(Collectors.groupingBy(Room::getCategory));
        if(isNull(roomsByCategory.get(PREMIUM))){
            roomsByCategory.put(PREMIUM, new LinkedList<>());
        }
        if(isNull(roomsByCategory.get(ECONOMY))){
            roomsByCategory.put(ECONOMY, new LinkedList<>());
        }
        return roomsByCategory;
    }

    private void setPremiumGuests(Map<Category, List<Room>> roomsByCategory, Map<Category, Queue<Guest>> guestByCategory){
        setGuest(roomsByCategory.get(PREMIUM), guestByCategory.get(PREMIUM));
    }

    private void setUpgradedEconomicGuests(Map<Category, List<Room>> roomsByCategory, Map<Category, Queue<Guest>> guestByCategory, int availableUpgrades){
        if(availableUpgrades == 0) {
            return;
        }
        Queue<Guest> guestToAdd = guestByCategory.get(ECONOMY).stream()
            .limit(availableUpgrades)
            .collect(toCollection(LinkedList::new));
        setGuest(roomsByCategory.get(PREMIUM), guestToAdd);
    }

    private void setEconomicGuests(Map<Category, List<Room>> roomsByCategory, Map<Category, Queue<Guest>> guestByCategory, int availableUpgrades){
        Queue<Guest> guestToAdd = guestByCategory.get(ECONOMY).stream()
            .skip(availableUpgrades)
            .collect(toCollection(LinkedList::new));
        setGuest(roomsByCategory.get(ECONOMY), guestToAdd);
    }

    private void setGuest(List<Room> rooms, Queue<Guest> guests){
        rooms = cleanOccupiedRooms(rooms);
        rooms.forEach(r -> {
            if(!guests.isEmpty()) {
                r.setGuest(guests.remove());
            }
        });
    }

    private List<Room> cleanOccupiedRooms(List<Room> rooms){
        return rooms.stream()
            .filter(r -> isNull(r.getGuest()))
            .collect(toList());
    }

    private int availableUpgrades(Map<Category, List<Room>> roomsByCategory, Map<Category, Queue<Guest>> guestByCategory){
        int extraPremiumRooms = extraPremiumRooms(roomsByCategory, guestByCategory);
        int extraEconomyGuests =  extraEconomyGuests(roomsByCategory, guestByCategory);

        if(extraPremiumRooms > 0 && extraEconomyGuests > 0){
            return min(extraPremiumRooms, extraEconomyGuests);
        }
        return 0;
    }

    private int extraPremiumRooms(Map<Category, List<Room>> roomsByCategory, Map<Category, Queue<Guest>> guestByCategory){
        return roomsByCategory.get(PREMIUM).size() - guestByCategory.get(PREMIUM).size();
    }

    private int extraEconomyGuests(Map<Category, List<Room>> roomsByCategory, Map<Category, Queue<Guest>> guestByCategory){
        return guestByCategory.get(ECONOMY).size() - roomsByCategory.get(ECONOMY).size();
    }
}
