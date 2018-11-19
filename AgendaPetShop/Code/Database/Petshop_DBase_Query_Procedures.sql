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


CREATE PROCEDURE sp_Pet_Create(
	@nome VARCHAR(50),
	@idade VARCHAR(40),
	@racaId INT,
	@pelagemId INT,
	@porteId INT,
	@observacoes VARCHAR(MAX))
AS BEGIN
	INSERT INTO Pet (nome, idade, racaId, pelagemId, porteId, observacoes) VALUES (@nome, @idade, @racaId, @pelagemId, @porteId, @observacoes)
	RETURN @@IDENTITY
END
GO

CREATE PROCEDURE sp_Pet_Read (
	@id INT,
    @nome VARCHAR(MAX),
	@racaId INT,
	@porteId INT,
	@donoId INT)
AS BEGIN
	DECLARE @Condicao VARCHAR(MAX)
	SET @Condicao = ''
	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @racaId IS NOT NULL OR @porteId IS NOT NULL OR @donoId IS NOT NULL) BEGIN
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
	IF(@donoId IS NOT NULL) BEGIN
		IF(@donoId != -1) BEGIN
			SET @Condicao = @Condicao + ' pd.donoId = ' + CAST(@donoId AS VARCHAR(MAX)) + ' AND'
		END
	END

	IF(@id IS NOT NULL OR @nome IS NOT NULL OR @racaId IS NOT NULL OR @porteId IS NOT NULL OR @donoId IS NOT NULL) BEGIN
		SET @Condicao = SUBSTRING(@Condicao, 0, LEN(@Condicao) - 3 )
	END

	DECLARE @Query VARCHAR(MAX)

	SET @Query = 'SELECT p.id, p.nome, p.idade, p.racaId, p.pelagemId, p.porteId, p.observacoes FROM Pet p, Dono d, PetXDono pd' + @Condicao
	
	EXEC (@Query)
END
GO

CREATE PROCEDURE sp_Pet_Read2 (
	@id INT,
    @nome VARCHAR(MAX),
	@racaId INT,
	@porteId INT,
	@donoId INT)
AS BEGIN
	SELECT * FROM Pet WHERE id = @id
END

CREATE PROCEDURE sp_Pet_Update (
	@id INT,
	@nome VARCHAR(50),
	@idade VARCHAR(40),
	@racaId INT,
	@pelagemId INT,
	@porteId INT,
	@observacoes VARCHAR(MAX))
AS BEGIN
	UPDATE Pet SET nome = @nome, idade = @idade, racaId= @racaId, pelagemId = @pelagemId, porteId = @porteId, observacoes = @observacoes WHERE id = @id
END
GO

CREATE PROCEDURE sp_Pet_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Pet WHERE id = @id
END
GO

--DONO
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
			SET @Condicao = @Condicao + ' p.id = ' + CAST(@id AS VARCHAR(MAX)) + ' AND'
		END
	END
	IF(@nome IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' nome LIKE ''%' + @nome + '%''' + ' AND'
	END
	IF(@cpf IS NOT NULL) BEGIN
		SET @Condicao = @Condicao + ' cpf = ' + @cpf + ' AND'
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

	SET @Query = 'SELECT d.id, d.cpf, d.nome, d.endereco, d.telefone, d.celular, d.observacoes FROM Pet p, Dono d, PetXDono pd' + @Condicao
	
	EXEC (@Query)
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
	UPDATE Dono SET cpf = @cpf, nome = @nome, @endereco = @endereco, telefone = @telefone, celular = @celular, observacoes = @observacoes WHERE id = @id
END
GO

CREATE PROCEDURE sp_Dono_Delete (
    @id INT)
AS BEGIN
	DELETE FROM Dono WHERE id = @id
END
GO