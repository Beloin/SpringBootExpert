create table produto(
    id integer PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(100),
    preco_unitario NUMERIC(20,2)
);

create table cliente (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    CPF VARCHAR(11)
);


create table pedido(
    id integer PRIMARY KEY AUTO_INCREMENT,
    cliente_id integer REFERENCES cliente (id),
    data_pedido TIMESTAMP,
    STATUS VARCHAR(20),
    total NUMERIC(20,2)
);

create table item_pedido(
    id integer PRIMARY KEY AUTO_INCREMENT,
    pedido_id integer REFERENCES pedido (id),
    produto_id integer REFERENCES produto (id),
    quantidade integer
);