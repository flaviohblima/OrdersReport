package br.com.flaviohblima.orders_report.infra.queues;

import br.com.flaviohblima.orders_report.application.OrderReceiver;
import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdersQueueConsumerTest {

    @InjectMocks
    private OrdersQueueConsumer consumer;

    @Mock
    private OrderReceiver receiver;

    @Test
    void receiveOrderMessage() {
        CreateOrderData order = mock(CreateOrderData.class);
        consumer.receiveOrderMessage(order);
        verify(receiver, times(1)).receiveOrder(order);
    }
}