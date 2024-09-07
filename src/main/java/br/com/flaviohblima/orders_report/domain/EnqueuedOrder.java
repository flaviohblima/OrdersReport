package br.com.flaviohblima.orders_report.domain;

import br.com.flaviohblima.orders_report.infra.persistence.Order;

import java.time.LocalDateTime;
import java.util.List;

public record EnqueuedOrder(
        Long codigoPedido,
        Long codigoCliente,
        List<EnqueuedItem> itens,
        LocalDateTime createdAt
) {

    public EnqueuedOrder(Order order) {
        this(order.getCodigoPedido(), order.getCodigoCliente(),
                order.getItens().stream().map(EnqueuedItem::new).toList(),
                order.getCreatedAt());
    }
}
