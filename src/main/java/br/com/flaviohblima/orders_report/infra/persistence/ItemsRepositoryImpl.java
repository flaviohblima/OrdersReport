package br.com.flaviohblima.orders_report.infra.persistence;


import br.com.flaviohblima.orders_report.application.ItemsRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ItemsRepositoryImpl implements ItemsRepository {

    private final ItemsJpaRepository jpaRepository;

    public ItemsRepositoryImpl(ItemsJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Float calcTotalByCodigoPedido(Long codigoPedido) {
        return jpaRepository.calcTotalByCodigoPedido(codigoPedido);
    }

}
