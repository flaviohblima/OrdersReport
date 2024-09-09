# OrdersReport

Aplicação Spring Boot para processar uma fila de pedidos de um RabbitMQ e persistir em um banco de dados PostgreSQL,
permitindo gerar relatórios com dados dos pedidos.

## Plano de ação inicial versus o que foi realizado

Descrito no arquivo [plano-acao-inicial-vs-realizado.md](plano-acao-inicial-vs-realizado.md).

## Relatório técnico

É possível encontrar o relatório técnico do projeto no arquivo [relatorio-tecnico.md](relatorio-tecnico.md).

## Executando o Projeto

Para executar o projeto, você pode baixar apenas o arquivo [docker compose](docker-compose.yml) e executar o comando:
```sh
docker compose up
```

Para testar os endpoints da aplicação, recomendo que você faça o download do arquivo [Orders API.postman_collection.json](Orders API.postman_collection.json).
Ele contém a coleção de endpoints disponíveis para interação com a aplicação. 

Na coleção você irá encontrar os seguintes métodos:
- **[POST]** Publicar pedido (com um modelo de pedido)
- **[GET]** Obter custo total de um pedido
- **[GET]** Obter contagem de pedidos de um cliente
- **[GET]** Obter lista de pedidos de um cliente paginada