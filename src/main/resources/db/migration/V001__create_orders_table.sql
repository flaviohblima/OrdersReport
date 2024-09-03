CREATE TABLE orders (
    order_id serial PRIMARY KEY,
    codigoPedido BIGINT UNIQUE,
    codigoCliente BIGINT
);

CREATE INDEX idx_orders_codigoPedido ON orders(codigoPedido);
CREATE INDEX idx_orders_codigoCliente ON orders(codigoCliente);