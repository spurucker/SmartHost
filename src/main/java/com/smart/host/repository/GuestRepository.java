package com.smart.host.repository;

import com.smart.host.domain.Guest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestRepository {
    private static final List<Guest> POTENTIAL_GUESTS = List.of(
        new Guest(374f),
        new Guest(209f),
        new Guest(155f),
        new Guest(115f),
        new Guest(101f),
        new Guest(100f),
        new Guest(99.99f),
        new Guest(45f),
        new Guest(23f),
        new Guest(22f)
        );

    public List<Guest> getAllPotentialGuestsSorted(){
        return POTENTIAL_GUESTS;
    }
}
