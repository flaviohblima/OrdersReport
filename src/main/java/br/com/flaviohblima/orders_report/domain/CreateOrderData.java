package br.com.flaviohblima.orders_report.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderData(
        @NotNull
        @Positive
        Long codigoPedido,
        @NotNull
        @Positive
        Long codigoCliente,
        @NotNull @NotEmpty @Valid
        List<CreateItemData> itens
) {
}
