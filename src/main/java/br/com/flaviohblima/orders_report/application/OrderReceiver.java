package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.EnqueuedOrder;
import br.com.flaviohblima.orders_report.infra.persistence.Order;
import br.com.flaviohblima.orders_report.infra.persistence.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderReceiver {

    private final OrdersRepository ordersRepository;

    public OrderReceiver(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void receiveOrder(EnqueuedOrder enqueuedOrder) {
        if (ordersRepository.existsByCodigoPedido(enqueuedOrder.codigoPedido())) {
            log.info("Order already persisted! Ignoring it.");
            return;
        }

        Order order = new Order(enqueuedOrder);
        Order savedOrder = ordersRepository.save(order);
        log.info("Persisted order id: {}", savedOrder.getOrderId());
    }

}
