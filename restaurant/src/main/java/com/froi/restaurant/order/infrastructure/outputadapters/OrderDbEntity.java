package com.froi.restaurant.order.infrastructure.outputadapters;

import com.froi.restaurant.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDbEntity {
    @Id
    @Column
    private String id;

    @Column
    private String waiter;

    @Column
    private String restaurant;

    @Column
    private LocalDateTime date;

    @Column(name = "paid_date")
    private LocalDateTime paidDate;

    @Column
    private Double total;

    @Column
    private Double subtotal;

    @Column(name = "order_name")
    private String orderName;

    public Order toDomain() {
        return Order.builder()
                .id(UUID.fromString(id))
                .waiter(waiter)
                .date(date)
                .paidDate(paidDate)
                .total(total)
                .subtotal(subtotal)
                .orderName(orderName)
                .build();
    }

    public static OrderDbEntity fromDomain(Order order) {
        return new OrderDbEntity(
                UUID.randomUUID().toString(),
                order.getWaiter(),
                order.getRestaurant().getId().toString(),
                order.getDate(),
                order.getPaidDate(),
                order.getTotal(),
                order.getSubtotal(),
                order.getOrderName()
        );
    }
}
