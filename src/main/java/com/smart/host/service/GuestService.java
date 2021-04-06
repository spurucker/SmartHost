package com.smart.host.service;

import com.smart.host.domain.Category;
import com.smart.host.domain.Guest;
import com.smart.host.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.smart.host.domain.Category.getCategory;

@Service
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public Map<Category, Queue<Guest>> getGuestMapByCategory(){
        List<Guest> guests = guestRepository.getAllPotentialGuestsSorted();

        return guests.stream()
            .collect(Collectors.groupingBy(g -> getCategory(g.getPrice()), Collectors.toCollection(LinkedList::new)));
    }

}
