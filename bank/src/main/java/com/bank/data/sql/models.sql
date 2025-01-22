CREATE TABLE
    ClientStandard_bank (
        nome VARCHAR(100),
        sobrenome VARCHAR(100),
        cpf VARCHAR(11) PRIMARY KEY,
        email VARCHAR(255),
        password VARCHAR(255),
        saldo DOUBLE
    );

CREATE TABLE
    Transfers_bank (
        id SERIAL PRIMARY KEY,
        cpf_remetente VARCHAR(11),
        cpf_destinatario VARCHAR(11),
        data_transferencia DATE,
        valor DOUBLE,
        FOREIGN KEY (cpf_remetente) REFERENCES ClientStandard_bank (cpf),
        FOREIGN KEY (cpf_destinatario) REFERENCES ClientStandard_bank (cpf)
    );

CREATE TABLE
    Deposit_bank (
        id SERIAL PRIMARY KEY,
        cpf VARCHAR(11),
        data_deposito DATE,
        valor DOUBLE,
        FOREIGN KEY (cpf) REFERENCES ClientStandard_bank (cpf)
    );

CREATE TABLE
    Endereco_bank (
        cpf VARCHAR(11) PRIMARY KEY,
        rua VARCHAR(255),
        numero INT,
        cidade VARCHAR(100),
        estado VARCHAR(100),
        cep VARCHAR(20),
        FOREIGN KEY (cpf) REFERENCES ClientStandard_bank (cpf)
    );