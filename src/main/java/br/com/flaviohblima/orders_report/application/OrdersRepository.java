package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.infra.persistence.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdersRepository {

    boolean existsByCodigoPedido(Long codigoPedido);

    Long countByCodigoCliente(Long codigoCliente);

    Page<Order> findByCodigoCliente(Long codigoCliente, Pageable pageable);

    Order save(Order order);
}
