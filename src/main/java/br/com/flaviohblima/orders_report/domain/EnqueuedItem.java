package br.com.flaviohblima.orders_report.domain;

public record EnqueuedItem(
        String produto,
        Integer quantidade,
        Float preco
) {
}
