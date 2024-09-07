package br.com.flaviohblima.orders_report.infra.rest_controllers;

import br.com.flaviohblima.orders_report.application.CustomerReports;
import br.com.flaviohblima.orders_report.domain.CustomerOrdersCount;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CustomersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerReports service;

    @Autowired
    private JacksonTester<CustomerOrdersCount> customerOrdersCountJson;

    @Autowired
    private JacksonTester<PagedModel<OrderDetails>> orderDetailsPageJson;

    @Test
    void getCustomerCountOfOrders() throws Exception {
        CustomerOrdersCount expected = new CustomerOrdersCount(1L, 2L);
        when(service.getCustomerCountOfOrders(1L)).thenReturn(expected);

        var response = mvc.perform(get("/customers/1/orders/count"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(customerOrdersCountJson.write(expected).getJson());
    }

    @Test
    void getCustomerOrders() throws Exception {
        Sort sort = Sort.by("createdAt");
        var page = PageRequest.of(0, 20, sort);

        OrderDetails orderOne = mock(OrderDetails.class);
        OrderDetails orderTwo = mock(OrderDetails.class);
        List<OrderDetails> expectedList = Arrays.asList(orderOne, orderTwo);
        Page<OrderDetails> expectedPage = new PageImpl<>(expectedList, page, 2);
        PagedModel<OrderDetails> expected = new PagedModel<>(expectedPage);

        when(service.getCustomerOrders(1L, page)).thenReturn(expectedPage);

        var response = mvc.perform(get("/customers/1/orders"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(orderDetailsPageJson.write(expected).getJson());
    }

}