package br.com.flaviohblima.orders_report.infra.rest_controllers;

import br.com.flaviohblima.orders_report.application.CustomerReports;
import br.com.flaviohblima.orders_report.domain.CustomerOrdersCount;
import br.com.flaviohblima.orders_report.domain.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomerReports customerReports;

    public CustomersController(CustomerReports customerReports) {
        this.customerReports = customerReports;
    }

    @GetMapping("/{codigoCliente}/orders/count")
    public ResponseEntity<CustomerOrdersCount> getCustomerCountOfOrders(@PathVariable Long codigoCliente) {
        var totalCost = customerReports.getCustomerCountOfOrders(codigoCliente);
        return ResponseEntity.ok(totalCost);
    }

    @GetMapping("/{codigoCliente}/orders")
    public ResponseEntity<Page<OrderDetails>> getCustomerOrders(
            @PathVariable Long codigoCliente,
            @PageableDefault(size = 20, sort = {"createdAt"}) Pageable pageable) {
        Page<OrderDetails> orders = customerReports.getCustomerOrders(codigoCliente, pageable);
        return ResponseEntity.ok(orders);
    }

}
