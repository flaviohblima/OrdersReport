package br.com.flaviohblima.orders_report.infra.queues;

import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrdersQueuePublisherTest {

    @Mock
    private RabbitTemplate rabbitmq;

    @Test
    void publishOrderToQueue() {
        var publisher = new OrdersQueuePublisher(rabbitmq, "queueName");
        CreateOrderData order = mock(CreateOrderData.class);
        publisher.publishOrderToQueue(order);
        verify(rabbitmq).convertAndSend("queueName", order);
    }
}