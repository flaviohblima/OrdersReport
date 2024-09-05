package br.com.flaviohblima.orders_report.infra.queues;

import br.com.flaviohblima.orders_report.domain.EnqueuedOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrdersQueuePublisher {

    private final RabbitTemplate rabbitmq;
    private final String queueName;

    public OrdersQueuePublisher(
            RabbitTemplate rabbitmq,
            @Value("${app.streams.rabbitmq.ordersQueue}") String queueName) {
        this.rabbitmq = rabbitmq;
        this.queueName = queueName;
    }

    public void publishOrderToQueue(EnqueuedOrder enqueuedOrder) {
        log.debug(enqueuedOrder.toString());
        rabbitmq.convertAndSend(queueName, enqueuedOrder);
    }
}
