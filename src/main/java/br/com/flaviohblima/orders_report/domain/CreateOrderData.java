package br.com.flaviohblima.orders_report.domain;

import java.util.List;

public record CreateOrderData(
        Long codigoPedido,
        Long codigoCliente,
        List<CreateItemData> itens
) {
}
