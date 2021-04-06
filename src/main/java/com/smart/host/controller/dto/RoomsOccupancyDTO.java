package com.smart.host.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomsOccupancyDTO {
    private Integer occupancy = 0;
    private Float earning = 0f;

    public void sumOccupancy(Integer newOccupancies){
        occupancy+= newOccupancies;
    }

    public void sumEarning(Float newEarning){
        earning+= newEarning;
    }
}
