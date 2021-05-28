/**
* Atividade de avaliação - Banco de dados
* Projeto 3: Carrinho de compras
* Versão 1.0
* @author Jonathan Santos | Paloma Kimberly
**/

create database dbcarcom;
use dbcarcom;

create table carrinho (
	codigo int primary key auto_increment,
    produto varchar(20) not null,
    quantidade int not null,
    valor decimal(10,2)
);

describe carrinho;

insert into carrinho (produto,quantidade,valor)
values
('Arroz Tio João',5,'25.15');
insert into carrinho (produto,quantidade,valor)
values
('Feijão Namorado',3,'9.98');
insert into carrinho (produto,quantidade,valor)
values
('Café Melitta',10,'9.99');
insert into carrinho (produto,quantidade,valor)
values
('Macarrão Dona Benta',2,'2.59');
insert into carrinho (produto,quantidade,valor)
values
('Açucar União',20,'3.15');
insert into carrinho (produto,quantidade,valor)
values
('Óleo',10,'7.98');
insert into carrinho (produto,quantidade,valor)
values
('Zoião',1,15.85);

select * from carrinho;
select * from carrinho where codigo = 1;
select * from carrinho order by produto;
select sum(valor*quantidade) as Total from carrinho;

update carrinho set produto='Óleo Soya' where codigo=5;

delete from carrinho where codigo=7;