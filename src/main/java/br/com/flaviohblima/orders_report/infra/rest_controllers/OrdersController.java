package br.com.flaviohblima.orders_report.infra.rest_controllers;

import br.com.flaviohblima.orders_report.domain.EnqueuedOrder;
import br.com.flaviohblima.orders_report.infra.queues.OrdersQueuePublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersQueuePublisher ordersPublisher;

    public OrdersController(OrdersQueuePublisher ordersPublisher) {
        this.ordersPublisher = ordersPublisher;
    }

    @PostMapping
    public ResponseEntity<PublishResponse> publishOrder(@RequestBody EnqueuedOrder enqueuedOrder) {
        ordersPublisher.publishOrderToQueue(enqueuedOrder);
        return ResponseEntity.ok(new PublishResponse("Message Published!"));
    }

}
