package com.froi.restaurant.dish.infrastructure.outputadapters;

import com.froi.restaurant.dish.domain.Dish;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "dish", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishDbEntity {
    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private Double cost;

    @Column
    private Double currentPrice;

    public Dish toDomain() {
        return Dish.builder()
                .id(UUID.fromString(id))
                .name(name)
                .cost(cost)
                .currentPrice(currentPrice)
                .build();
    }

    public static DishDbEntity fromDomain(Dish dish) {
        return new DishDbEntity(UUID.randomUUID().toString(),
                dish.getName(),
                dish.getCost(),
                dish.getCurrentPrice());
    }
}
