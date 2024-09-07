package br.com.flaviohblima.orders_report.tests_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Configuration
public class TestMockMvcConfiguration {

    @Bean
    public MockMvc mockMvc(WebApplicationContext webApplicationContext) {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .defaultRequest(get("/").characterEncoding("UTF-8"))
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .alwaysExpect(content().encoding("UTF-8"))
                .build();
    }

}
