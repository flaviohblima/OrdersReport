package br.com.flaviohblima.orders_report.infra.persistence;

import br.com.flaviohblima.orders_report.domain.CreateItemData;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String produto;

    @Column(nullable = false)
    @Positive
    private Integer quantidade;

    @Column(nullable = false)
    private Float preco;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Item(CreateItemData item, Order order) {
        this.produto = item.produto();
        this.quantidade = item.quantidade();
        this.preco = item.preco();
        this.order = order;
    }
}
