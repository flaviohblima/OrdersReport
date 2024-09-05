package br.com.flaviohblima.orders_report.infra.queues;

import br.com.flaviohblima.orders_report.domain.EnqueuedOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrdersQueueConsumer {

    @RabbitListener(queues = "${app.streams.rabbitmq.ordersQueue}")
    public void receiveOrderMessage(EnqueuedOrder enqueuedOrder) {
       log.info(enqueuedOrder.toString());
    }
}
