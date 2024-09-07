package br.com.flaviohblima.orders_report.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsJpaRepository extends JpaRepository<Item, Long> {

    @Query("SELECT sum(i.preco) FROM Item i WHERE i.order.codigoPedido = :codigoPedido")
    Float sumPricesByCodigoPedido(Long codigoPedido);
}
