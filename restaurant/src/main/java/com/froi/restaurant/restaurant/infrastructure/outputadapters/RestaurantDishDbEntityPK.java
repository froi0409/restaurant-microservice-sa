package com.froi.restaurant.restaurant.infrastructure.outputadapters;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDishDbEntityPK implements Serializable {
    @Column
    private String restaurant;
    @Column
    private String dish;
}
