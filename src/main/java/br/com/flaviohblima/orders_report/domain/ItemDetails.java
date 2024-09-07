package br.com.flaviohblima.orders_report.domain;

import br.com.flaviohblima.orders_report.infra.persistence.Item;

public record ItemDetails(
        Long itemId,
        String produto,
        Integer quantidade,
        Float preco
) {

    public ItemDetails(Item item) {
        this(item.getItemId(), item.getProduto(), item.getQuantidade(), item.getPreco());
    }
}
