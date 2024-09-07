package br.com.flaviohblima.orders_report.domain;

import br.com.flaviohblima.orders_report.infra.persistence.Item;

public record EnqueuedItem(
        String produto,
        Integer quantidade,
        Float preco
) {

    public EnqueuedItem(Item item) {
        this(item.getProduto(), item.getQuantidade(), item.getPreco());
    }
}
