package br.com.flaviohblima.orders_report.infra.persistence;

import br.com.flaviohblima.orders_report.application.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersJpaRepository extends OrdersRepository, JpaRepository<Order, Long> {

    boolean existsByCodigoPedido(Long codigoPedido);

    Long countByCodigoCliente(Long codigoCliente);

    Page<Order> findByCodigoCliente(Long codigoCliente, Pageable pageable);

}
