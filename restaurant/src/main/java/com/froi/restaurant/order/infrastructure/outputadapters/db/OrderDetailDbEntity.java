package com.froi.restaurant.order.infrastructure.outputadapters.db;

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
@Table(name = "order_detail", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDbEntity {
    @Id
    @Column
    private String id;

    @Column
    private String dish;

    @Column(name = "order_id")
    private String order;

    @Column(name = "dish_price")
    private Double dishPrice;

    @Column
    private Double dishCost;

    @Column
    private String note;

    public Dish toDomain() {
        return Dish.builder()
                .currentPrice(dishPrice)
                .cost(dishCost)
                .build();
    }

    public Dish toDomainDishId() {
        return Dish.builder()
                .id(UUID.fromString(dish))
                .name(dish)
                .currentPrice(dishPrice)
                .cost(dishCost)
                .build();
    }

    public static OrderDetailDbEntity fromDomain(Dish dish, String orderId) {
        return new OrderDetailDbEntity(
                UUID.randomUUID().toString(),
                dish.getId().toString(),
                orderId,
                dish.getCurrentPrice(),
                dish.getCost(),
                dish.getNote()
        );
    }
}
