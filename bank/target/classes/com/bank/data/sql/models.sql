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

CREATE TABLE
    transfers_bank (
        id SERIAL PRIMARY KEY,
        cpf_remetente VARCHAR(11),
        cpf_destinatario VARCHAR(11),
        data_transferencia DATE,
        valor DOUBLE,
        FOREIGN KEY (cpf_remetente) REFERENCES ClientStandard_bank (cpf),
        FOREIGN KEY (cpf_destinatario) REFERENCES ClientStandard_bank (cpf)
    );