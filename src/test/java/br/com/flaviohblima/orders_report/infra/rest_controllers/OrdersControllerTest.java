package br.com.flaviohblima.orders_report.infra.rest_controllers;

import br.com.flaviohblima.orders_report.application.OrderReports;
import br.com.flaviohblima.orders_report.domain.CreateItemData;
import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.OrderTotalCost;
import br.com.flaviohblima.orders_report.infra.queues.OrdersQueuePublisher;
import br.com.flaviohblima.orders_report.infra.rest_controllers.exceptions.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class OrdersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrdersQueuePublisher ordersPublisher;

    @MockBean
    private OrderReports orderReports;

    @Autowired
    private JacksonTester<CreateOrderData> createOrderDataJson;

    @Autowired
    private JacksonTester<PublishResponse> publishResponseJson;

    @Autowired
    private JacksonTester<OrderTotalCost> orderTotalCostJson;

    @Autowired
    private JacksonTester<ErrorDetails> errorDetailsJson;

    @Test
    void publishOrder() throws Exception {
        CreateItemData itemOne = new CreateItemData("name", 1, 2f);
        List<CreateItemData> items = Collections.singletonList(itemOne);
        CreateOrderData order = new CreateOrderData(1L, 2L, items);
        PublishResponse expected = new PublishResponse("Order published!");

        var response = mvc.perform(post("/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderDataJson.write(order).getJson()))
                .andReturn().getResponse();

        verify(ordersPublisher, atMostOnce()).publishOrderToQueue(order);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(publishResponseJson.write(expected).getJson());
    }

    @Test
    void getOrderTotalCost() throws Exception {
        OrderTotalCost expected = new OrderTotalCost(1L, 2f);

        when(orderReports.getOrderTotalCost(1L)).thenReturn(expected);

        var response = mvc.perform(get("/orders/1/total"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(orderTotalCostJson.write(expected).getJson());
    }

    @Test
    void getOrderTotalCostShouldThrowNotFoundException() throws Exception {
        ErrorDetails expected = new ErrorDetails(404, "NOT_FOUND", "Pedido não encontrado!");

        when(orderReports.getOrderTotalCost(1L))
                .thenThrow(new EntityNotFoundException("Pedido não encontrado!"));

        var response = mvc.perform(get("/orders/1/total"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString())
                .isEqualTo(errorDetailsJson.write(expected).getJson());
    }

    @Test
    void getOrderTotalCostShouldThrowUnexpectedException() throws Exception {
        ErrorDetails expected = new ErrorDetails(500, "INTERNAL_SERVER_ERROR", "Erro não previsto!");

        when(orderReports.getOrderTotalCost(1L))
                .thenThrow(new RuntimeException("Erro não previsto!"));

        var response = mvc.perform(get("/orders/1/total"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString())
                .isEqualTo(errorDetailsJson.write(expected).getJson());
    }
}