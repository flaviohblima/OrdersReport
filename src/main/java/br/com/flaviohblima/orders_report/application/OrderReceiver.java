package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderReceiver {

    private final OrdersRepository ordersRepository;

    public OrderReceiver(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void receiveOrder(CreateOrderData createOrderData) {
        if (ordersRepository.existsByCodigoPedido(createOrderData.codigoPedido())) {
            log.info("Order already persisted! Ignoring it.");
            return;
        }

        OrderDetails savedOrder = ordersRepository.save(createOrderData);
        log.info("Persisted order id: {}", savedOrder.orderId());
    }

}
