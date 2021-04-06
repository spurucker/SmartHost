package com.smart.host.integration;

import com.smart.host.controller.RoomController;
import com.smart.host.controller.dto.DistributionRequest;
import com.smart.host.controller.dto.RoomsOccupancyByDTO;
import com.smart.host.controller.dto.RoomsOccupancyDTO;
import com.smart.host.domain.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static com.smart.host.domain.Category.ECONOMY;
import static com.smart.host.domain.Category.PREMIUM;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SuggestRoomDistributionIntegrationTest {
    @Autowired
    private RoomController roomController;

    @Test
    public void suggestRoomGuests_noPremium(){
        DistributionRequest request = new DistributionRequest(3, 0);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(0, 0f),
            ECONOMY, new RoomsOccupancyDTO(3, 167.99f)
        );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    public void suggestRoomGuests_noEconomy(){
        DistributionRequest request = new DistributionRequest(0, 3);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(3, 738f),
            ECONOMY, new RoomsOccupancyDTO(0, 0f)
        );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    public void suggestRoomGuests_noRooms(){
        DistributionRequest request = new DistributionRequest(0, 0);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(0, 0f),
            ECONOMY, new RoomsOccupancyDTO(0, 0f)
        );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    public void suggestRoomGuests_allCategoriesFull(){
        DistributionRequest request = new DistributionRequest(3, 3);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(3, 738f),
            ECONOMY, new RoomsOccupancyDTO(3, 167.99f)
        );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    public void suggestRoomGuests_noCategoryFull(){
        DistributionRequest request = new DistributionRequest(5, 7);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(6, 1054f),
            ECONOMY, new RoomsOccupancyDTO(4, 189.99f)
            );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    public void suggestRoomGuests_premiumFull(){
        DistributionRequest request = new DistributionRequest(7, 2);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(2, 583f),
            ECONOMY, new RoomsOccupancyDTO(4, 189.99f)
        );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    public void suggestRoomGuests_economyGuestsUpgraded(){
        DistributionRequest request = new DistributionRequest(1, 7);
        Map<Category, RoomsOccupancyDTO> expectedMap= Map.of(
            PREMIUM, new RoomsOccupancyDTO(7, 1153.99f),
            ECONOMY, new RoomsOccupancyDTO(1, 45.00f)
        );
        RoomsOccupancyByDTO expected = new RoomsOccupancyByDTO(expectedMap);

        RoomsOccupancyByDTO got = roomController.suggestRoomDistribution(request);

        assertThat(got).isEqualTo(expected);
    }
}
