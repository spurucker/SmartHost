package com.smart.host.domain;

import lombok.Data;

@Data
public class Room {
    private Category category;
    private Guest guest;

    public Room(Category category){
        this.category = category;
    }
}
