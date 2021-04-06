package com.smart.host.comparator;

import com.smart.host.domain.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.smart.host.domain.Category.ECONOMY;
import static com.smart.host.domain.Category.PREMIUM;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryComparatorTest {

    @Test
    public void sort(){
        List<Room> availableRooms = new ArrayList<>();
        availableRooms.add(new Room(PREMIUM));
        availableRooms.add(new Room(ECONOMY));
        availableRooms.add(new Room(PREMIUM));
        availableRooms.add(new Room(PREMIUM));
        availableRooms.add(new Room(ECONOMY));

        List<Room> expectedRooms = List.of(
            new Room(PREMIUM),
            new Room(PREMIUM),
            new Room(PREMIUM),
            new Room(ECONOMY),
            new Room(ECONOMY)
        );

        Collections.sort(availableRooms, new CategoryComparator());

        assertThat(availableRooms).isEqualTo(expectedRooms);
    }
}
