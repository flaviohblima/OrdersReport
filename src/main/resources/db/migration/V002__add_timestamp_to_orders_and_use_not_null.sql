ALTER TABLE orders ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE orders RENAME COLUMN codigoPedido TO codigo_pedido;
ALTER TABLE orders ALTER COLUMN codigo_pedido SET NOT NULL;

ALTER TABLE orders RENAME COLUMN codigoCliente TO codigo_cliente;
ALTER TABLE orders ALTER COLUMN codigo_cliente SET NOT NULL;

DROP INDEX IF EXISTS idx_orders_codigoPedido;
DROP INDEX IF EXISTS idx_orders_codigoCliente;

CREATE INDEX idx_orders_codigo_pedido ON orders(codigo_pedido);
CREATE INDEX idx_orders_codigo_cliente ON orders(codigo_cliente);