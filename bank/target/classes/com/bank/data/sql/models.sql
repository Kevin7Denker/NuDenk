CREATE TABLE
    ClientStandard_bank (
        nome VARCHAR(100),
        sobrenome VARCHAR(100),
        cpf VARCHAR(11) PRIMARY KEY,
        email VARCHAR(255),
        password VARCHAR(255),
        endereco VARCHAR(255),
        saldo DOUBLE
    );