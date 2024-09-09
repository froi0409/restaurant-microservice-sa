package com.froi.restaurant.restaurant.infrastructure.outputadapters;

import com.froi.restaurant.restaurant.domain.RestaurantDish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant_dish", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDishDbEntity {
    @EmbeddedId
    private RestaurantDishDbEntityPK id;

    @Column
    private boolean isEnable;

    public RestaurantDish toDomain() {
        return RestaurantDish.builder()
                .isEnable(isEnable)
                .build();
    }

    public static RestaurantDishDbEntity fromDomain(RestaurantDish restaurantDish) {
        return new RestaurantDishDbEntity(
                new RestaurantDishDbEntityPK(restaurantDish.getRestaurant().getId().toString(), restaurantDish.getDish().getId().toString()),
                restaurantDish.isEnable()
        );
    }
}
