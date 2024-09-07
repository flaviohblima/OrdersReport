package br.com.flaviohblima.orders_report.infra.persistence;

import br.com.flaviohblima.orders_report.domain.CreateItemData;
import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.ItemDetails;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersRepositoryImplTest {

    @InjectMocks
    private OrdersRepositoryImpl ordersRepository;

    @Mock
    private OrdersJpaRepository jpaRepository;

    @Test
    void existsByCodigoPedido() {
        when(jpaRepository.existsByCodigoPedido(1L)).thenReturn(true);
        assertTrue(ordersRepository.existsByCodigoPedido(1L));

        when(jpaRepository.existsByCodigoPedido(1L)).thenReturn(false);
        assertFalse(ordersRepository.existsByCodigoPedido(1L));
    }

    @Test
    void countByCodigoCliente() {
        when(jpaRepository.countByCodigoCliente(1L)).thenReturn(2L);
        assertEquals(2L, ordersRepository.countByCodigoCliente(1L));
    }

    @Test
    void findByCodigoCliente() {
        Sort sort = Sort.by("createdAt");
        var page = PageRequest.of(0, 20, sort);

        LocalDateTime orderOneCreatedAt = LocalDateTime.now();
        LocalDateTime orderTwoCreatedAt = LocalDateTime.now();

        Item itemOne = new Item(1L, "produto1", 3, 4f, null);
        Item itemTwo = new Item(2L, "produto2", 5, 6f, null);
        List<Item> items = Arrays.asList(itemOne, itemTwo);

        Order orderOne = new Order(1L, 2L, 3L, orderOneCreatedAt, items);
        Order orderTwo = new Order(2L, 3L, 4L, orderTwoCreatedAt, Collections.singletonList(itemOne));
        List<Order> orderList = Arrays.asList(orderOne, orderTwo);
        Page<Order> orderPage = new PageImpl<>(orderList, page, 2);

        when(jpaRepository.findByCodigoCliente(1L, page)).thenReturn(orderPage);

        Page<OrderDetails> expected = getOrderDetails(orderOneCreatedAt, orderTwoCreatedAt, page);

        Page<OrderDetails> actual = ordersRepository.findByCodigoCliente(1L, page);

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
        assertThat(actual.getTotalElements()).isEqualTo(expected.getTotalElements());
        assertThat(actual.getTotalPages()).isEqualTo(expected.getTotalPages());
        assertThat(actual.getSize()).isEqualTo(expected.getSize());
        assertThat(actual.getSort()).isEqualTo(expected.getSort());
    }

    private static Page<OrderDetails> getOrderDetails(LocalDateTime orderOneCreatedAt, LocalDateTime orderTwoCreatedAt, PageRequest page) {
        ItemDetails itemDetailsOne = new ItemDetails(1L, "produto1", 3, 4f);
        ItemDetails itemDetailsTwo = new ItemDetails(2L, "produto2", 5, 6f);
        List<ItemDetails> itemDetailsList = Arrays.asList(itemDetailsOne, itemDetailsTwo);

        OrderDetails orderDetailsOne = new OrderDetails(1L, 2L, 3L, itemDetailsList, orderOneCreatedAt);
        OrderDetails orderDetailsTwo = new OrderDetails(2L, 3L, 4L,
                Collections.singletonList(itemDetailsOne), orderTwoCreatedAt);
        List<OrderDetails> orderDetailsList = Arrays.asList(orderDetailsOne, orderDetailsTwo);
        return new PageImpl<>(orderDetailsList, page, 2);
    }

    @Test
    void save() {
        CreateItemData item = new CreateItemData("produto1", 3, 4f);
        CreateOrderData toCreate = new CreateOrderData(1L, 2L, Collections.singletonList(item));
        Order order = new Order(toCreate);
        Order orderWithId = new Order(toCreate);
        orderWithId.setOrderId(10L);

        OrderDetails expected = new OrderDetails(orderWithId);

        when(jpaRepository.save(order)).thenReturn(orderWithId);
        OrderDetails actual = ordersRepository.save(toCreate);

        assertEquals(expected, actual);
    }
}