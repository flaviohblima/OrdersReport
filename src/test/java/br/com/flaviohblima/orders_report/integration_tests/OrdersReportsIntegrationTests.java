package br.com.flaviohblima.orders_report.integration_tests;

import br.com.flaviohblima.orders_report.domain.*;
import br.com.flaviohblima.orders_report.infra.persistence.OrdersJpaRepository;
import br.com.flaviohblima.orders_report.infra.persistence.OrdersRepositoryImpl;
import br.com.flaviohblima.orders_report.infra.queues.OrdersQueueConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@SpringRabbitTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class OrdersReportsIntegrationTests extends RabbitMQTestContainerConfiguration {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CustomerOrdersCount> customerOrdersCountJson;

    @Autowired
    private JacksonTester<PagedModel<OrderDetails>> orderDetailsPageJson;

    @Autowired
    private JacksonTester<OrderTotalCost> orderTotalCostJson;

    @Autowired
    private OrdersQueueConsumer consumer;

    @Autowired
    private OrdersJpaRepository ordersJpaRepository;

    @Autowired
    private OrdersRepositoryImpl ordersRepository;

    private final Long customer1 = 10L;
    private final Long customer2 = 20L;

    private final CreateItemData item1 = new CreateItemData("item1", 1, 1f);
    private final CreateItemData item2 = new CreateItemData("item2", 2, 2f);
    private final CreateItemData item3 = new CreateItemData("item3", 3, 3f);
    private final CreateItemData item4 = new CreateItemData("item4", 4, 4f);
    private final CreateItemData item5 = new CreateItemData("item5", 5, 5f);

    private final List<CreateItemData> order1Items = Collections.singletonList(item1);
    private final CreateOrderData order1 = new CreateOrderData(1L, customer1, order1Items);

    private final List<CreateItemData> order2Items = Arrays.asList(item1, item2);
    private final CreateOrderData order2 = new CreateOrderData(2L, customer1, order2Items);

    private final List<CreateItemData> order3Items = Arrays.asList(item3, item4);
    private final CreateOrderData order3 = new CreateOrderData(3L, customer2, order3Items);

    private final List<CreateItemData> order4Items = Arrays.asList(item3, item4, item5);
    private final CreateOrderData order4 = new CreateOrderData(4L, customer2, order4Items);

    private final List<CreateItemData> order5Items = Collections.singletonList(item5);
    private final CreateOrderData order5 = new CreateOrderData(5L, customer2, order5Items);

    private final Sort sort = Sort.by("createdAt");
    private final Pageable pageable = PageRequest.of(0, 20, sort);

    @BeforeEach
    void sendOrders() {
        consumer.receiveOrderMessage(order1);
        consumer.receiveOrderMessage(order2);
        consumer.receiveOrderMessage(order3);
        consumer.receiveOrderMessage(order4);
        consumer.receiveOrderMessage(order5);
    }

    @AfterEach
    void deleteOrders() {
        ordersJpaRepository.deleteAll();
    }


    @Test
    void shouldReceiveOrderFromRabbitMQ() {
        assertTrue(ordersJpaRepository.existsByCodigoPedido(order1.codigoPedido()));
        assertTrue(ordersJpaRepository.existsByCodigoPedido(order2.codigoPedido()));
        assertTrue(ordersJpaRepository.existsByCodigoPedido(order3.codigoPedido()));
        assertTrue(ordersJpaRepository.existsByCodigoPedido(order4.codigoPedido()));
        assertTrue(ordersJpaRepository.existsByCodigoPedido(order5.codigoPedido()));
    }

    @Test
    void shouldReturnCorrectItemsCount() throws Exception {
        CustomerOrdersCount count1 = performGetCustomerOrderCount(customer1);
        assertEquals(2, count1.ordersCount());

        CustomerOrdersCount count2 = performGetCustomerOrderCount(customer2);
        assertEquals(3, count2.ordersCount());
    }

    private CustomerOrdersCount performGetCustomerOrderCount(Long customer) throws Exception {
        var response = mvc.perform(get("/customers/" + customer + "/orders/count"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        return customerOrdersCountJson.parseObject(response.getContentAsString());
    }

    @Test
    void shouldReturnCorrectCustomerOrders() throws Exception {
        testCustomerOrders(customer1);
        testCustomerOrders(customer2);
    }

    private void testCustomerOrders(Long customerId) throws Exception {
        Page<OrderDetails> result1 = ordersRepository.findByCodigoCliente(customerId, pageable);
        PagedModel<OrderDetails> expected1 = new PagedModel<>(result1);

        String customer1orders = performGetCustomerOrders(customerId);
        assertThat(customer1orders).isEqualTo(
                orderDetailsPageJson.write(expected1).getJson());
    }

    private String performGetCustomerOrders(Long customer) throws Exception {
        var response = mvc.perform(get("/customers/" + customer + "/orders"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        return response.getContentAsString();
    }

    @Test
    void shouldReturnCorrectTotalCostOfOrders() throws Exception {
        shouldReturnCorrectTotalCostOfOrder(order1);
        shouldReturnCorrectTotalCostOfOrder(order2);
        shouldReturnCorrectTotalCostOfOrder(order3);
        shouldReturnCorrectTotalCostOfOrder(order4);
        shouldReturnCorrectTotalCostOfOrder(order5);
    }

    private void shouldReturnCorrectTotalCostOfOrder(CreateOrderData order) throws Exception {
        Float totalCost = order.itens().stream()
                .reduce(0.0f,
                        (subtotal, item) -> subtotal + (item.quantidade() * item.preco()),
                        Float::sum);

        OrderTotalCost expected = new OrderTotalCost(order.codigoPedido(), totalCost);

        var response = mvc.perform(get("/orders/" + order.codigoPedido() + "/total"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(orderTotalCostJson.write(expected).getJson());
    }

}