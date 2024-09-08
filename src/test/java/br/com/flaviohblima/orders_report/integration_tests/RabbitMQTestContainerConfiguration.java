package br.com.flaviohblima.orders_report.integration_tests;

import org.junit.jupiter.api.AfterAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@Testcontainers
public class RabbitMQTestContainerConfiguration {

    @Container
    public static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.13-management")
            .withStartupTimeout(Duration.ofMinutes(4));

    static {
        if (!rabbit.isRunning()) {
            rabbit.start();
        }
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(final DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.addresses", rabbit::getAmqpUrl);
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
    }

    @AfterAll
    public static void stopRabbitMQ() {
        if (rabbit.isRunning()) {
            rabbit.stop();
        }
    }

}
