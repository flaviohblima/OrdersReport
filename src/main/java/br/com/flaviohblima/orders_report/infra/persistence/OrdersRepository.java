package br.com.flaviohblima.orders_report.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

    boolean existsByCodigoPedido(Long codigoPedido);

    Long countByCodigoCliente(Long codigoCliente);
}
