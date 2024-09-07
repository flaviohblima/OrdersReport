package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.CustomerOrdersCount;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerReportsTest {

    @InjectMocks
    private CustomerReports service;

    @Mock
    private OrdersRepository ordersRepository;

    @Test
    void getCustomerCountOfOrdersShouldReturnCountZero() {
        var expected = new CustomerOrdersCount(1L, 0L);
        CustomerOrdersCount actual = service.getCustomerCountOfOrders(1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getCustomerCountOfOrdersShouldReturnCountBiggerThanZero() {
        var expected = new CustomerOrdersCount(1L, 2L);
        when(ordersRepository.countByCodigoCliente(1L)).thenReturn(2L);

        CustomerOrdersCount actual = service.getCustomerCountOfOrders(1L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getCustomerOrdersShouldReturnPageOfOrders() {
        var pageable = Pageable.ofSize(2);
        OrderDetails orderOne = mock(OrderDetails.class);
        OrderDetails orderTwo = mock(OrderDetails.class);
        List<OrderDetails> expectedList = Arrays.asList(orderOne, orderTwo);
        Page<OrderDetails> expected = new PageImpl<>(expectedList);
        when(ordersRepository.findByCodigoCliente(1L, pageable)).thenReturn(expected);

        Page<OrderDetails> actual = service.getCustomerOrders(1L, pageable);
        assertThat(actual).isEqualTo(expected);
    }
}