DROP DATABASE IF EXISTS crd;
CREATE DATABASE crd;
USE crd;

CREATE TABLE tvseries(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(35),
    ano integer,
    temporadas integer,
    episodios integer,
    minutos integer,
    preco double
);

CREATE TABLE movies(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(35),
    ano integer,
    minutos integer,
    preco double
);