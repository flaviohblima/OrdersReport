package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.CustomerOrdersCount;
import br.com.flaviohblima.orders_report.domain.EnqueuedOrder;
import br.com.flaviohblima.orders_report.infra.persistence.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<EnqueuedOrder> getCustomerOrders(Long codigoCliente, Pageable page) {
        Page<Order> orders = ordersRepository.findByCodigoCliente(codigoCliente, page);
        return orders.map(EnqueuedOrder::new);
    }

}
