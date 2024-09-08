package br.com.flaviohblima.orders_report.integration_tests;

import br.com.flaviohblima.orders_report.domain.CreateItemData;
import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.infra.rest_controllers.PublishResponse;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@SpringRabbitTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PublishOrderIntegrationTest extends RabbitMQTestContainerConfiguration {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JacksonTester<CreateOrderData> createOrderDataJson;

    @Autowired
    private JacksonTester<PublishResponse> publishResponseJson;

    @Test
    void shouldSendOrderToRabbitMQ() throws Exception {
        CreateItemData itemOne = new CreateItemData("item1", 1, 2f);
        List<CreateItemData> items = Collections.singletonList(itemOne);
        CreateOrderData order = new CreateOrderData(1L, 2L, items);
        PublishResponse expected = new PublishResponse("Order published!");

        String orderJson = createOrderDataJson.write(order).getJson();

        var response = mvc.perform(post("/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(publishResponseJson.write(expected).getJson());

        CreateOrderData receivedMessage = (CreateOrderData) rabbitTemplate.receiveAndConvert("ordersQueue");
        assertThat(receivedMessage).isEqualTo(order);
    }

    @Test
    void shouldThrowExceptionWhenOrderIsInvalid() throws Exception {
        String createOrderData = """
                {
                    "codigoPedido": 0,
                    "codigoCliente": 0,
                    "items": null
                }
                """;

        var response = mvc.perform(post("/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderData))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoItems() throws Exception {
        CreateOrderData order = new CreateOrderData(1L, 2L, new ArrayList<>());

        String orderJson = createOrderDataJson.write(order).getJson();

        var response = mvc.perform(post("/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoOrder() throws Exception {
        var response = mvc.perform(post("/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}