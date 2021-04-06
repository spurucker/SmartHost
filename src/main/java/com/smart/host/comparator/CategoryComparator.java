package com.smart.host.comparator;

import com.smart.host.domain.Room;

import java.util.Comparator;

import static com.smart.host.domain.Category.PREMIUM;

public class CategoryComparator implements Comparator<Room> {
    @Override
    public int compare(Room r1, Room r2) {
        if(r1.getCategory().equals(PREMIUM)){
            return -1;
        }
        return 1;
    }
}
