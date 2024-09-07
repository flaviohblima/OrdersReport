package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.CustomerOrdersCount;
import br.com.flaviohblima.orders_report.infra.persistence.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerReports {

    private final OrdersRepository ordersRepository;

    public CustomerReports(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public CustomerOrdersCount getCustomerCountOfOrders(Long codigoCliente) {
        Long count = ordersRepository.countByCodigoCliente(codigoCliente);
        return new CustomerOrdersCount(codigoCliente, count);
    }

}
