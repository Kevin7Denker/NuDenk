CREATE TABLE
    ClientStandard_bank (
        nome VARCHAR(255),
        cpf VARCHAR(11) PRIMARY KEY,
        email VARCHAR(255),
        password VARCHAR(255),
        telefone VARCHAR(20),
        endereco VARCHAR(255),
        saldo DOUBLE
    );