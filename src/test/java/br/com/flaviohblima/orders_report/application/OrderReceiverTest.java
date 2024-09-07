package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderReceiverTest {

    @InjectMocks
    private OrderReceiver service;

    @Mock
    private OrdersRepository ordersRepository;

    @Test
    void receiveOrderShouldNotPersistAnOrderAlreadyPersisted() {
        CreateOrderData createOrderData = new CreateOrderData(1L, 0L, null);
        when(ordersRepository.existsByCodigoPedido(1L)).thenReturn(true);

        service.receiveOrder(createOrderData);
        verify(ordersRepository, never()).save(any());
    }

    @Test
    void receiveOrderShouldPersist() {
        CreateOrderData createOrderData = mock(CreateOrderData.class);
        OrderDetails saved = mock(OrderDetails.class);
        when(ordersRepository.existsByCodigoPedido(anyLong())).thenReturn(false);
        when(ordersRepository.save(createOrderData)).thenReturn(saved);

        service.receiveOrder(createOrderData);
        verify(ordersRepository, atMostOnce()).save(createOrderData);
    }

}