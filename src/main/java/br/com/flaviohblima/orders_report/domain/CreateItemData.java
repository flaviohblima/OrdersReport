package br.com.flaviohblima.orders_report.domain;

public record CreateItemData(
        String produto,
        Integer quantidade,
        Float preco
) {
}
