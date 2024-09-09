package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.OrderTotalCost;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderReportsTest {

    @InjectMocks
    private OrderReports service;

    @Mock
    private ItemsRepository itemsRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @Test
    void getOrderTotalCostShouldTriggerExceptionWhenNotFound() {
        when(ordersRepository.existsByCodigoPedido(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {
            service.getOrderTotalCost(1L);
        });
        verify(itemsRepository, never()).calcTotalByCodigoPedido(1L);
    }

    @Test
    void getOrderTotalCostShouldGetTotalCost() {
        OrderTotalCost expected = new OrderTotalCost(1L, 2f);
        when(ordersRepository.existsByCodigoPedido(1L)).thenReturn(true);
        when(itemsRepository.calcTotalByCodigoPedido(1L)).thenReturn(2f);

        OrderTotalCost actual = service.getOrderTotalCost(1L);
        assertThat(actual).isEqualTo(expected);
    }
}