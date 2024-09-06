package com.froi.restaurant.order.application.paidorderusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbOutputAdapter;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import com.froi.restaurant.order.infrastructure.inputports.restapi.PayOrderInputPort;
import com.froi.restaurant.order.infrastructure.outputadapters.db.OrderDbOutputAdapter;
import com.froi.restaurant.order.infrastructure.outputports.restapi.PayBillOutputPort;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@UseCase
@Transactional
public class PayOrderUseCase implements PayOrderInputPort {

    private PayBillOutputPort payBillOutputAdapter;
    private OrderDbOutputAdapter orderDbOutputAdapter;
    private RestaurantDbOutputAdapter restaurantDbOutputAdapter;
    private DishDbOutputAdapter dishDbOutputAdapter;

    @Autowired
    public PayOrderUseCase(PayBillOutputPort payBillOutputAdapter, OrderDbOutputAdapter orderDbOutputAdapter, RestaurantDbOutputAdapter restaurantDbOutputAdapter, DishDbOutputAdapter dishDbOutputAdapter) {
        this.payBillOutputAdapter = payBillOutputAdapter;
        this.orderDbOutputAdapter = orderDbOutputAdapter;
        this.restaurantDbOutputAdapter = restaurantDbOutputAdapter;
        this.dishDbOutputAdapter = dishDbOutputAdapter;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public byte[] payOrder(PayOrderRequest payOrderRequest) throws NetworkMicroserviceException, OrderException {
        // Buscar la orden
        Order order = orderDbOutputAdapter.findOrderdById(payOrderRequest.getOrderId());
        String restaurantId = orderDbOutputAdapter.findRestaurantIdByOrderId(payOrderRequest.getOrderId());
        Restaurant restaurant = restaurantDbOutputAdapter.findRestaurantById(restaurantId);

        // Verificar que la orden no haya sido pagada anteriormente
        if (order.getPaidDate() != null) {
            throw new OrderException("Order already paid");
        }

        List<BillDetail> billDetails = new ArrayList<>();
        for (Dish dish : order.getOrderDetail()) {
            System.out.println(dish.getName());
            System.out.println(dish.getCurrentPrice());
            dish.setName(dishDbOutputAdapter.findDishNameById(dish.getName()));
            billDetails.add(new BillDetail(dish.getName(), dish.getCurrentPrice()));
        }

        // Buscar descuentos
        List<BillDiscount> billDiscounts = new ArrayList<>();

        // actualizar fecha de pago
        orderDbOutputAdapter.updateBill(order.getId().toString());

        // Enviar la petición
        MakeBillRequest makeBillRequest = new MakeBillRequest(
                payOrderRequest.getCustomerNit(),
                payOrderRequest.getOptionalCustomerDpi(),
                payOrderRequest.getOptionalCustomerFirstName(),
                payOrderRequest.getOptionalCustomerLastName(),
                payOrderRequest.getOptionalCustomerBirthDate(),
                restaurant.getId().toString(),
                order.getId().toString(),
                billDetails,
                billDiscounts,
                payOrderRequest.isHasDiscount()
        );

        return payBillOutputAdapter.payBill(makeBillRequest);
    }
}
