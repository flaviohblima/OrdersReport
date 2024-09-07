package br.com.flaviohblima.orders_report.infra.rest_controllers;

import br.com.flaviohblima.orders_report.application.OrderReports;
import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.OrderTotalCost;
import br.com.flaviohblima.orders_report.infra.queues.OrdersQueuePublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersQueuePublisher ordersPublisher;
    private final OrderReports orderReports;

    public OrdersController(OrdersQueuePublisher ordersPublisher, OrderReports orderReports) {
        this.ordersPublisher = ordersPublisher;
        this.orderReports = orderReports;
    }

    @PostMapping
    public ResponseEntity<PublishResponse> publishOrder(@RequestBody CreateOrderData createOrderData) {
        ordersPublisher.publishOrderToQueue(createOrderData);
        return ResponseEntity.ok(new PublishResponse("Order published!"));
    }

    @GetMapping("/{codigoPedido}/total")
    public ResponseEntity<OrderTotalCost> getOrderTotalCost(@PathVariable Long codigoPedido) {
        var totalCost = orderReports.getOrderTotalCost(codigoPedido);
        return ResponseEntity.ok(totalCost);
    }

}
