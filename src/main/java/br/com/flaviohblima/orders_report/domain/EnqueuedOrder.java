package br.com.flaviohblima.orders_report.domain;

import java.util.List;

public record EnqueuedOrder(
        Long codigoPedido,
        Long codigoCliente,
        List<EnqueuedItem> itens
) {
}
