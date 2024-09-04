package com.froi.restaurant.restaurant.infrastructure.outputadapters;

import com.froi.restaurant.restaurant.domain.Restaurant;
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
@Table(name = "restaurant", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDbEntity {
    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column
    private String city;

    @Column
    private String hotel;

    public Restaurant toDomain() {
        return Restaurant.builder()
                .id(UUID.fromString(id))
                .name(name)
                .phone1(phone1)
                .phone2(phone2)
                .city(Integer.parseInt(city))
                .hotel(hotel)
                .build();
    }

    public static RestaurantDbEntity fromDomain(Restaurant restaurant) {
        return new RestaurantDbEntity(
                UUID.randomUUID().toString(),
                restaurant.getName(),
                restaurant.getPhone1(),
                restaurant.getPhone2(),
                restaurant.getCity().toString(),
                restaurant.getHotel()
        );
    }
}
