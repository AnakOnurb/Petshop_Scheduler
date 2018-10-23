--CREATE DATABASE Petshop_DBase
--GO

USE Petshop_DBase
GO

CREATE TABLE Dono
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	cpf VARCHAR(14) NOT NULL,
	nome VARCHAR(70) NOT NULL,
	endereco VARCHAR(255) NOT NULL,
	telefone INT,
	celular INT NOT NULL,
	observacoes VARCHAR(MAX)
)

CREATE TABLE Raca
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
)

CREATE TABLE Pelagem
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
)

CREATE TABLE Porte
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
)

CREATE TABLE Cliente
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	idade VARCHAR(40) NOT NULL,
	racaId INT NOT NULL REFERENCES Raca(id),
	pelagemId INT NOT NULL REFERENCES Pelagem(id),
	porteId INT NOT NULL REFERENCES Porte(id),
	observacoes VARCHAR(MAX)
)

CREATE TABLE ClienteXDono
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	clienteId INT NOT NULL REFERENCES Cliente(id),
	donoId INT NOT NULL REFERENCES Dono(id)
)

CREATE TABLE Funcionario
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	cpf VARCHAR(14) NOT NULL,
	nome VARCHAR(70) NOT NULL,
	endereco VARCHAR(255) NOT NULL,
	telefone INT,
	celular INT NOT NULL,
	observacoes VARCHAR(MAX),
	salario DECIMAL(10,2) NOT NULL
)

CREATE TABLE Servico
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	tipo VARCHAR(100) NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	preco DECIMAL(10,2) NOT NULL,
	duracao TIME NOT NULL
)

CREATE TABLE TipoPacote
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	descricao VARCHAR(30) NOT NULL
)

CREATE TABLE Pacote
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	clienteId INT NOT NULL REFERENCES Cliente(id),
	tipo INT NOT NULL REFERENCES TipoPacote(id),
	funcionarioId INT NOT NULL REFERENCES Funcionario(id),
	pago BIT NOT NULL 
)

CREATE TABLE Agendamento
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	clienteId INT NOT NULL REFERENCES Cliente(id),
	data DATE NOT NULL,
	horario TIME NOT NULL,
	servicoId INT NOT NULL REFERENCES Servico(id),
	funcionarioId INT NOT NULL REFERENCES Funcionario(id),
	pago BIT NOT NULL 
)

CREATE TABLE PacoteXAgendamento
(
	id INT NOT NULL IDENTITY PRIMARY KEY,
	pacoteId INT NOT NULL REFERENCES Pacote(id),
	agendamentoId INT NOT NULL REFERENCES Agendamento(id)
)


