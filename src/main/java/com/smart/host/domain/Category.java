package com.smart.host.domain;

public enum Category {
    PREMIUM, ECONOMY;

    public static Category getCategory(Float price){
        if(price.compareTo(100f) >= 0){
            return PREMIUM;
        }
        return ECONOMY;
    }
}
