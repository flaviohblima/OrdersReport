package br.com.flaviohblima.orders_report.application;

import br.com.flaviohblima.orders_report.domain.OrderTotalCost;
import br.com.flaviohblima.orders_report.infra.persistence.ItemsRepository;
import br.com.flaviohblima.orders_report.infra.persistence.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderReports {

    private final ItemsRepository itemsRepository;
    private final OrdersRepository ordersRepository;

    public OrderReports(OrdersRepository ordersRepository, ItemsRepository itemsRepository) {
        this.ordersRepository = ordersRepository;
        this.itemsRepository = itemsRepository;
    }

    public OrderTotalCost getOrderTotalCost(Long codigoPedido) {
        boolean existsOrder = ordersRepository.existsByCodigoPedido(codigoPedido);

        if(!existsOrder) {
            throw new EntityNotFoundException("Pedido não encontrado!");
        }

        Float totalCost = itemsRepository.sumPricesByCodigoPedido(codigoPedido);
        return new OrderTotalCost(codigoPedido, totalCost);
    }

}
