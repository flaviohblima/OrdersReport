package br.com.flaviohblima.orders_report.application;

public interface ItemsRepository {

    Float calcTotalByCodigoPedido(Long codigoPedido);

}
