package br.com.flaviohblima.orders_report.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateItemData(
        @NotBlank
        String produto,
        @NotNull
        Integer quantidade,
        @NotNull
        Float preco
) {
}
