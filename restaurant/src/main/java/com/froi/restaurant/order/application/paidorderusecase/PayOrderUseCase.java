package com.froi.restaurant.order.application.paidorderusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbOutputAdapter;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import com.froi.restaurant.order.infrastructure.inputports.restapi.PayOrderInputPort;
import com.froi.restaurant.order.infrastructure.outputadapters.db.OrderDbOutputAdapter;
import com.froi.restaurant.order.infrastructure.outputadapters.restapi.FindDiscountsOutputAdapter;
import com.froi.restaurant.order.infrastructure.outputports.restapi.PayBillOutputPort;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UseCase
@Transactional
public class PayOrderUseCase implements PayOrderInputPort {

    private PayBillOutputPort payBillOutputAdapter;
    private OrderDbOutputAdapter orderDbOutputAdapter;
    private RestaurantDbOutputAdapter restaurantDbOutputAdapter;
    private DishDbOutputAdapter dishDbOutputAdapter;
    private FindDiscountsOutputAdapter findDiscountsOutputAdapter;

    @Autowired
    public PayOrderUseCase(PayBillOutputPort payBillOutputAdapter, OrderDbOutputAdapter orderDbOutputAdapter, RestaurantDbOutputAdapter restaurantDbOutputAdapter, DishDbOutputAdapter dishDbOutputAdapter, FindDiscountsOutputAdapter findDiscountsOutputAdapter) {
        this.payBillOutputAdapter = payBillOutputAdapter;
        this.orderDbOutputAdapter = orderDbOutputAdapter;
        this.restaurantDbOutputAdapter = restaurantDbOutputAdapter;
        this.dishDbOutputAdapter = dishDbOutputAdapter;
        this.findDiscountsOutputAdapter = findDiscountsOutputAdapter;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public byte[] payOrder(PayOrderRequest payOrderRequest) throws NetworkMicroserviceException, OrderException {
        Order order = orderDbOutputAdapter.findOrderdById(payOrderRequest.getOrderId());
        String restaurantId = orderDbOutputAdapter.findRestaurantIdByOrderId(payOrderRequest.getOrderId());
        Restaurant restaurant = restaurantDbOutputAdapter.findRestaurantById(restaurantId);

        if (order.getPaidDate() != null) {
            throw new OrderException("Order already paid");
        }

        List<BillDetail> billDetails = new ArrayList<>();
        List<BillDiscount> billDiscounts = new ArrayList<>();
        processOrder(order, billDetails, billDiscounts);
        checkCustomerDiscount(payOrderRequest, order, billDiscounts);
        orderDbOutputAdapter.updateBill(order.getId().toString());

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

    private void processOrder(Order order, List<BillDetail> billDetails, List<BillDiscount> billDiscounts) {
        for (Dish dish : order.getOrderDetail()) {
            dish.setName(dishDbOutputAdapter.findDishNameById(dish.getName()));
            billDetails.add(new BillDetail(dish.getName(), dish.getCurrentPrice()));

            BillDiscount dishDiscount = findDiscountsOutputAdapter.findDishDiscount(dish.getId().toString(), LocalDate.now());
            if (dishDiscount != null) {
                double percentage = Math.round((dish.getCurrentPrice() / 100) * dishDiscount.getDiscounted() * 100.0) / 100.0;
                BillDiscount billDiscount = new BillDiscount("Dish Discount-" + dishDiscount.getDescription(), percentage);
                billDiscounts.add(billDiscount);
            }
        }
    }

    private void checkCustomerDiscount(PayOrderRequest payOrderRequest, Order order, List<BillDiscount> billDiscounts) {
        if (payOrderRequest.isHasDiscount() && payOrderRequest.getCustomerNit() != null) {
            BillDiscount customerDiscount = findDiscountsOutputAdapter.findCustomerDiscount(payOrderRequest.getCustomerNit(), LocalDate.now());
            if (customerDiscount != null) {
                double percentage = Math.round((order.getTotal() / 100) * customerDiscount.getDiscounted() * 100.0) / 100.0;
                BillDiscount billDiscount = new BillDiscount("Customer Discount-" + customerDiscount.getDescription(), percentage);
                billDiscounts.add(billDiscount);
            }
        }

    }
}
