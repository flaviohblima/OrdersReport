package br.com.flaviohblima.orders_report.domain;

import br.com.flaviohblima.orders_report.infra.persistence.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetails(
        Long orderId,
        Long codigoPedido,
        Long codigoCliente,
        List<ItemDetails> itens,
        LocalDateTime createdAt
) {

    public OrderDetails(Order order) {
        this(order.getOrderId(), order.getCodigoPedido(), order.getCodigoCliente(),
                order.getItens().stream().map(ItemDetails::new).toList(),
                order.getCreatedAt());
    }

}
