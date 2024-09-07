package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdersRepository {

    boolean existsByCodigoPedido(Long codigoPedido);

    Long countByCodigoCliente(Long codigoCliente);

    Page<OrderDetails> findByCodigoCliente(Long codigoCliente, Pageable pageable);

    OrderDetails save(CreateOrderData order);

}
