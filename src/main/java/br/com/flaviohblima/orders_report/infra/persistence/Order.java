package br.com.flaviohblima.orders_report.infra.persistence;

import br.com.flaviohblima.orders_report.domain.CreateOrderData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true)
    private Long codigoPedido;

    @Column(nullable = false)
    private Long codigoCliente;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<Item> itens = new ArrayList<>();

    public Order(CreateOrderData createOrderData) {
        this.codigoPedido = createOrderData.codigoPedido();
        this.codigoCliente = createOrderData.codigoCliente();
        this.itens = createOrderData.itens().stream()
                .map(item -> new Item(item, this))
                .toList();
    }
}
