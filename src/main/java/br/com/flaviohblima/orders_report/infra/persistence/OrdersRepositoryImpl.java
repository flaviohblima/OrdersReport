package br.com.flaviohblima.orders_report.infra.persistence;

import br.com.flaviohblima.orders_report.application.OrdersRepository;
import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrdersRepositoryImpl implements OrdersRepository {

    private final OrdersJpaRepository jpaRepository;

    public OrdersRepositoryImpl(OrdersJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsByCodigoPedido(Long codigoPedido) {
        return jpaRepository.existsByCodigoPedido(codigoPedido);
    }

    @Override
    public Long countByCodigoCliente(Long codigoCliente) {
        return jpaRepository.countByCodigoCliente(codigoCliente);
    }

    @Override
    public Page<OrderDetails> findByCodigoCliente(Long codigoCliente, Pageable pageable) {
        Page<Order> orders = jpaRepository.findByCodigoCliente(codigoCliente, pageable);
        return orders.map(OrderDetails::new);
    }

    @Override
    public OrderDetails save(CreateOrderData orderToCreate) {
        Order saved = jpaRepository.save(new Order(orderToCreate));
        return new OrderDetails(saved);
    }
}
