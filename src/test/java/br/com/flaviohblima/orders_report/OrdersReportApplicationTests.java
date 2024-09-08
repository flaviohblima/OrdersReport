package br.com.flaviohblima.orders_report;

import br.com.flaviohblima.orders_report.integration_tests.RabbitMQTestContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringRabbitTest
class OrdersReportApplicationTests extends RabbitMQTestContainerConfiguration {

    @Test
    void contextLoads() {
    }

}
