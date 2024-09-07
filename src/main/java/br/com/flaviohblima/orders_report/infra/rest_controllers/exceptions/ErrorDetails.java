package br.com.flaviohblima.orders_report.infra.rest_controllers.exceptions;

public record ErrorDetails(Integer statusCode, String status, String message) {
}
