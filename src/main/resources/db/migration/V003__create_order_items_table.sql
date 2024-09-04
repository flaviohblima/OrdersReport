CREATE TABLE order_items (
    item_id SERIAL PRIMARY KEY,
    order_id BIGINT,
    produto VARCHAR(128) NOT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);

ALTER TABLE order_items ADD CONSTRAINT fk_order_items_order_id
FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE;

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_preco ON order_items(preco);