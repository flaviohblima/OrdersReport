# Plano de Ação

Este plano de ação descreve os passos que serão executados para concluir o épico de um projeto de processamento de pedidos.

## Épico: Criar Aplicação para Processamento de Pedidos e Geração de Relatórios

As tarefas foram estimadas usando a sequência de Fibonacci.
Estimativa total: **35 horas**

### História 1: Planejamento
- [x] Tarefa 1 **[2 horas -> 2h]**: Criar este esboço do plano de desenvolvimento, definindo as tarefas e os componentes principais (aplicação Spring Boot, RabbitMQ, PostgreSQL).

### História 2: Criar aplicação e implementar recepção de mensagens
- [x] Tarefa 2 **[1 hora / 1h30]**: Criar a estrutura inicial do projeto Spring Boot com Java 21, configurando a conexão com o PostgreSQL.
- [x] Tarefa 3 **[5 horas / 6h]**: Implementar o microsserviço para consumir mensagens da fila RabbitMQ e armazenar os dados no PostgreSQL (modelagem das tabelas).
- [x] Tarefa 4 **[2 horas / 1h30]**: Configurar o Dockerfile e Docker Compose para orquestrar a aplicação, o RabbitMQ e o PostgreSQL.
- [x] Tarefa 5 **[3 horas / 1h]**: Implementar uma forma de publicar mensagens via REST no RabbitMQ para testes.

### História 3: Adicionar endpoints para relatórios
- [x] Tarefa 6 **[5 horas / 3h30]**: Desenvolver as funcionalidades da API REST para consultar:
  - [x] Valor total do pedido
  - [x] Quantidade de pedidos por cliente
  - [x] Lista de pedidos realizados por cliente.
- [x] Tarefa 7 **[5 horas / 10h]**: Implementar testes unitários e de integração para garantir as funcionalidades da aplicação (garantir cobertura de testes).

### História 4: Publicação da Imagem e Implantação na AWS
- [ ] Tarefa 8 **[1 hora]**: Criar a imagem Docker da aplicação e publicá-la no Docker Hub.
- [ ] Tarefa 9 **[5 horas]**: Configurar a infraestrutura na AWS, incluindo a configuração do RDS PostgreSQL e o deploy da aplicação.

### História 5: Testes e Validação
- [ ] Tarefa 10 **[2 horas]**: Realizar testes finais de integração e funcionalidade na AWS.

### História 6: Documentação e Entrega
- [ ] Tarefa 11 **[3 horas]**: Escrever o relatório técnico, com os requisitos do desafio.
- [ ] Tarefa 12 **[2 horas]**: Revisão final e submissão do projeto no GitHub, com instruções claras para rodar o projeto com o docker compose.