USE master
GO
USE Petshop_DBase
GO

--FUNCIONARIOS
CREATE PROCEDURE sp_Funcionario_Create(
	@cpf VARCHAR(14),
	@nome VARCHAR(70),
	@endereco VARCHAR(255),
	@telefone VARCHAR(13),
	@celular VARCHAR(14),
	@observacoes VARCHAR(MAX),
	@salario DECIMAL(10,2))
AS BEGIN
	INSERT INTO Funcionario (cpf, nome, endereco, telefone, celular, observacoes, salario) VALUES (@cpf, @nome, @endereco, @telefone, @celular, @observacoes, @salario)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Funcionario_Read (
    @id INT,
	@nome VARCHAR(MAX),
	@cpf VARCHAR(MAX))
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @cpf IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' WHERE'
	END
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@nome IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' nome LIKE ''%' + @nome + '%''' + ' AND'
	END
	IF(@cpf IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' cpf = ' + @cpf + ' AND'
	END

	IF(@Id IS NOT NULL OR @nome IS NOT NULL OR @cpf IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)
	SET @Query = 'SELECT * FROM Funcionario' + @Condicao

	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_Funcionario_ReadSimple
AS BEGIN
	SELECT * FROM Funcionario
END
GO

CREATE PROCEDURE sp_Funcionario_Update (
	@id INT,
	@cpf VARCHAR(14),
	@nome VARCHAR(70),
	@endereco VARCHAR(255),
	@telefone VARCHAR(13),
	@celular VARCHAR(14),
	@observacoes VARCHAR(MAX),
	@salario DECIMAL(10,2))
AS BEGIN
	UPDATE Funcionario SET cpf = @cpf, nome = @nome, endereco = @endereco, telefone = @telefone, celular = @celular, observacoes = @observacoes, salario = @salario WHERE id = @id
END
GO

CREATE PROCEDURE sp_Funcionario_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Funcionario WHERE id = @id
END
GO

--PETS

select * from Pet
insert into Pelagem values ('Curto')
insert into Pelagem values ('Longo')
insert into Porte values ('Pequeno')
insert into Porte values ('Médio')
insert into Porte values ('Grande')
insert into Raca values ('Lhasa')
insert into Raca values ('Boxer')
insert into Raca values ('Poodle')
insert into Raca values ('Labrador')
insert into Raca values ('Golden')
insert into Especie values ('Cão')
insert into Especie values ('Gato')
GO

INSERT INTO Pet (nome, idade, especieId, racaId, pelagemId, porteId, observacoes) VALUES ('Thor', '4 anos', 2, 2, 2, 2, 'é um Deus')
GO

CREATE PROCEDURE sp_Pet_Create(
	@nome VARCHAR(50),
	@idade VARCHAR(40),
	@especieId INT,
	@racaId INT,
	@pelagemId INT,
	@porteId INT,
	@observacoes VARCHAR(MAX))
AS BEGIN
	INSERT INTO Pet (nome, idade, especieId, racaId, pelagemId, porteId, observacoes) VALUES (@nome, @idade, @especieId, @racaId, @pelagemId, @porteId, @observacoes)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Pet_Read (
	@id INT,
    @nome VARCHAR(MAX),
	@especieId INT,
	@racaId INT,
	@porteId INT,
	@donoId INT)
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @especieId IS NOT NULL OR @racaId IS NOT NULL OR @porteId IS NOT NULL OR @donoId IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' WHERE'
	END
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' p.id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@nome IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' p.nome LIKE ''%' + @nome + '%''' + ' AND'
	END
	IF(@especieId IS NOT NULL) BEGIN
		IF(@especieId != -1) BEGIN
			SET @Condicao = @Condicao + ' p.especieId = ' + CAST(@especieId AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@racaId IS NOT NULL) BEGIN
		IF(@racaId != -1) BEGIN
			SET @Condicao = @Condicao + ' p.racaId = ' + CAST(@racaId AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@porteId IS NOT NULL) BEGIN
		IF(@porteId != -1) BEGIN
			SET @Condicao = @Condicao + ' p.porteId = ' + CAST(@porteId AS VARCHAR(MAX)) + ' AND'
		END
	END
	/*IF(@donoId IS NOT NULL) BEGIN
		IF(@donoId != -1) BEGIN
			SET @Condicao = @Condicao + ' pd.donoId = ' + CAST(@donoId AS VARCHAR(MAX)) + ' AND'
		END
	END*/

	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @racaId IS NOT NULL OR @porteId IS NOT NULL OR @donoId IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)

	--SET @Query = 'SELECT p.id, p.nome, p.idade, p.racaId, p.pelagemId, p.porteId, p.observacoes FROM Pet p, Dono d, PetXDono pd' + @Condicao
	SET @Query = 'SELECT p.id, p.nome, p.idade, p.especieId, p.racaId, p.pelagemId, p.porteId, p.observacoes FROM Pet p' + @Condicao

	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_Pet_ReadSimple
AS BEGIN
	SELECT * FROM Pet
END
GO

CREATE PROCEDURE sp_Pet_Update (
	@id INT,
	@nome VARCHAR(50),
	@idade VARCHAR(40),
	@especieId INT,
	@racaId INT,
	@pelagemId INT,
	@porteId INT,
	@observacoes VARCHAR(MAX))
AS BEGIN
	UPDATE Pet SET nome = @nome, idade = @idade, especieId = @especieId, racaId= @racaId, pelagemId = @pelagemId, porteId = @porteId, observacoes = @observacoes WHERE id = @id
END
GO

CREATE PROCEDURE sp_Pet_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Pet WHERE id = @id
END
GO

--DONO
SELECT * FROM Dono

CREATE PROCEDURE sp_Dono_Create(
	@cpf VARCHAR(14),
	@nome VARCHAR(70),
	@endereco VARCHAR(255),
	@telefone VARCHAR(13),
	@celular VARCHAR(14),
	@observacoes VARCHAR(MAX))
AS BEGIN
	INSERT INTO Dono (cpf, nome, endereco, telefone, celular, observacoes) VALUES (@cpf, @nome, @endereco, @telefone, @celular, @observacoes)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Dono_Read (
	@id INT,
    @cpf VARCHAR(MAX),
	@nome VARCHAR(MAX),
	@petId INT)
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @cpf IS NOT NULL OR @petId IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' WHERE'
	END
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' d.id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@nome IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' d.nome LIKE ''%' + @nome + '%''' + ' AND'
	END
	IF(@cpf IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' d.cpf LIKE ''%' + @cpf + '%''' + ' AND'
	END
	IF(@petId IS NOT NULL) BEGIN
		IF(@petId != -1) BEGIN
			SET @Condicao = @Condicao + ' pd.petId = ' + CAST(@petId AS VARCHAR(MAX)) + ' AND'
		END
	END

	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @cpf IS NOT NULL OR @petId IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)

	SET @Query = 'SELECT d.id, d.cpf, d.nome, d.endereco, d.telefone, d.celular, d.observacoes FROM Dono d' + @Condicao
	
	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_Dono_ReadSimple
AS BEGIN
	SELECT * FROM Dono
END
GO

CREATE PROCEDURE sp_Dono_Update (
	@id INT,
	@cpf VARCHAR(14),
	@nome VARCHAR(70),
	@endereco VARCHAR(255),
	@telefone VARCHAR(13),
	@celular VARCHAR(14),
	@observacoes VARCHAR(MAX))
AS BEGIN
	UPDATE Dono SET cpf = @cpf, nome = @nome, endereco = @endereco, telefone = @telefone, celular = @celular, observacoes = @observacoes WHERE id = @id
END
GO

CREATE PROCEDURE sp_Dono_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Dono WHERE id = @id
END
GO

--SERVICO
CREATE PROCEDURE sp_Servico_Create(
	@descricao VARCHAR(100),
	@preco DECIMAL(10,2),
	@duracao INT)
AS BEGIN
	INSERT INTO Servico(descricao, preco, duracao) VALUES (@descricao, @preco, @duracao)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Servico_Read (
	@id INT,
    @descricao VARCHAR(100),
	@preco DECIMAL(10,2),
	@duracao INT)
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @descricao IS NOT NULL OR @preco IS NOT NULL OR @duracao IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' WHERE'
	END
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@descricao IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' descricao LIKE ''%' + @descricao + '%''' + ' AND'
	END
	IF(@preco IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' preco = ' + CAST(@preco AS VARCHAR(MAX)) + ' AND'
	END
	IF(@duracao IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' duracao = ' + CAST(@duracao AS VARCHAR(MAX)) + ' AND'
		END
	END

	IF(@id IS NOT NULL OR @descricao IS NOT NULL OR @preco IS NOT NULL OR @duracao IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)

	SET @Query = 'SELECT id, descricao, preco, duracao FROM Servico' + @Condicao
	
	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_Servico_ReadSimple
AS BEGIN
	SELECT * FROM Servico
END
GO

CREATE PROCEDURE sp_Servico_Update (
	@id INT,
	@descricao VARCHAR(100),
	@preco DECIMAL(10,2),
	@duracao INT)
AS BEGIN
	UPDATE Servico SET descricao = @descricao, preco = @preco, duracao = @duracao WHERE id = @id
END
GO

CREATE PROCEDURE sp_Servico_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Servico WHERE id = @id
END
GO

--TIPOPACOTE
CREATE PROCEDURE sp_TipoPacote_Create(
	@descricao VARCHAR(100))
AS BEGIN
	INSERT INTO TipoPacote(descricao) VALUES (@descricao)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_TipoPacote_Read (
	@id INT,
    @descricao VARCHAR(100))
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @descricao IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' WHERE'
	END
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@descricao IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' descricao LIKE ''%' + @descricao + '%''' + ' AND'
	END
	
	IF(@id IS NOT NULL OR @descricao IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)

	SET @Query = 'SELECT id, descricao FROM TipoPacote' + @Condicao
	
	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_TipoPacote_ReadSimple
AS BEGIN
	SELECT * FROM TipoPacote
END
GO

CREATE PROCEDURE sp_TipoPacote_Update (
	@id INT,
	@descricao VARCHAR(100))
AS BEGIN
	UPDATE TipoPacote SET descricao = @descricao WHERE id = @id
END
GO

CREATE PROCEDURE sp_TipoPacote_Delete (
    @id INT)
AS BEGIN
	DELETE FROM TipoPacote WHERE id = @id
END
GO

--PACOTE
CREATE PROCEDURE sp_Pacote_Create(
	@petId INT,
	@tipo INT,
	@funcionarioId INT)
AS BEGIN
	INSERT INTO Pacote (petId, tipo, funcionarioId) VALUES (@petId, @tipo, @funcionarioId)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Pacote_Read (
	@id INT,
    @petId INT,
	@tipo INT,
	@funcionarioId INT)
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @petId IS NOT NULL OR @tipo IS NOT NULL OR @funcionarioId IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' WHERE'
	END
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SET @Condicao = @Condicao + ' id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@petId IS NOT NULL) BEGIN
		IF(@petId != -1) BEGIN
			SET @Condicao = @Condicao + ' petId = ' + CAST(@petId AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@tipo IS NOT NULL) BEGIN
		IF(@tipo != -1) BEGIN
			SET @Condicao = @Condicao + ' tipo = ' + CAST(@tipo AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@funcionarioId IS NOT NULL) BEGIN
		IF(@funcionarioId != -1) BEGIN
			SET @Condicao = @Condicao + ' funcionarioId = ' + CAST(@funcionarioId AS VARCHAR(MAX)) + ' AND'
		END
	END

	IF(@id IS NOT NULL OR @petId IS NOT NULL OR @tipo IS NOT NULL OR @funcionarioId IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)

	SET @Query = 'SELECT id, petId, tipo, funcionarioId FROM Pacote' + @Condicao
	
	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_Pacote_ReadSimple
AS BEGIN
	SELECT * FROM Pacote
END
GO

CREATE PROCEDURE sp_Pacote_Update (
	@id INT,
	@petId INT,
	@tipo INT,
	@funcionarioId INT)
AS BEGIN
	UPDATE Pacote SET petId = @petId, tipo = @tipo, funcionarioId = @funcionarioId WHERE id = @id
END
GO

CREATE PROCEDURE sp_Pacote_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Pacote WHERE id = @id
END
GO

--PAGAMENTO

select * from TipoPagamento
insert into TipoPagamento values ('Dinheiro')
insert into TipoPagamento values ('Cartão de Débito')
insert into TipoPagamento values ('Cartão de Crédito')
insert into TipoPagamento values ('Cheque')
GO

CREATE PROCEDURE sp_Pagamento_Create(
	@data DATE,
	@tipoId INT)
AS BEGIN
	INSERT INTO Pagamento (data, tipoId) VALUES (@data, @tipoId)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Pagamento_ReadSimple
AS BEGIN
	SELECT * FROM Pagamento
END
GO

--AGENDAMENTO
CREATE PROCEDURE sp_Agendamento_Create(
	@petId INT,
	@data DATE,
	@horario TIME,
	@servicoId INT,
	@funcionarioId INT,
	@pacoteId INT,
	@pagamentoId INT,
	@cancelado BIT)
AS BEGIN
	INSERT INTO Agendamento (petId, data, horario, servicoId, funcionarioId, pacoteId, pagamentoId, cancelado) 
					 VALUES (@petId, @data, @horario, @servicoId, @funcionarioId, @pacoteId, @pagamentoId, @cancelado)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Agendamento_ReadSimple
AS BEGIN
	SELECT * FROM Agendamento
END
GO

CREATE PROCEDURE sp_Agendamento_Update (
	@id INT,
	@petId INT,
	@data DATE,
	@horario TIME,
	@servicoId INT,
	@funcionarioId INT,
	@pacoteId INT,
	@pagamentoId INT,
	@cancelado BIT)
AS BEGIN
	UPDATE Agendamento SET petId = @petId, data = @data, horario = @horario, servicoId = @servicoId, funcionarioId = @funcionarioId,
						   pacoteId = @pacoteId, pagamentoId = @pagamentoId, cancelado = @cancelado WHERE id = @id
END
GO

CREATE PROCEDURE sp_Agendamento_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Agendamento WHERE id = @id
END
GO

--READSIMPLE PARA COMBOBOX
CREATE PROCEDURE sp_Raca_ReadSimple
AS BEGIN
	SELECT * FROM Raca
END
GO

CREATE PROCEDURE sp_raca_Read(
	@id INT)
AS BEGIN
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SELECT * FROM Raca WHERE id = @id
		END
	END
END
GO

CREATE PROCEDURE sp_Pelagem_ReadSimple
AS BEGIN
	SELECT * FROM Pelagem
END
GO

CREATE PROCEDURE sp_Pelagem_Read(
	@id INT)
AS BEGIN
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SELECT * FROM Pelagem WHERE id = @id
		END
	END
END
GO

CREATE PROCEDURE sp_Porte_ReadSimple
AS BEGIN
	SELECT * FROM Porte
END
GO

CREATE PROCEDURE sp_Porte_Read(
	@id INT)
AS BEGIN
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SELECT * FROM Porte WHERE id = @id
		END
	END
END
GO

CREATE PROCEDURE sp_Especie_ReadSimple
AS BEGIN
	SELECT * FROM Especie
END
GO

CREATE PROCEDURE sp_Especie_Read(
	@id INT)
AS BEGIN
	IF(@id IS NOT NULL) BEGIN
		IF(@id != -1) BEGIN
			SELECT * FROM Especie WHERE id = @id
		END
	END
END
GO

CREATE PROCEDURE sp_TipoPagamento_ReadSimple
AS BEGIN
	SELECT * FROM TipoPagamento
END
GO