/ / - > Criação de tabelas
CREATE TABLE
    ClientStandard_bank (
        nome VARCHAR(100),
        sobrenome VARCHAR(100),
        cpf VARCHAR(14) PRIMARY KEY,
        email VARCHAR(255),
        password VARCHAR(255),
    );

CREATE TABLE
    Transfers_bank (
        id SERIAL PRIMARY KEY,
        cpf_remetente VARCHAR(14),
        cpf_destinatario VARCHAR(14),
        data_transferencia DATE,
        valor DOUBLE,
        FOREIGN KEY (cpf_remetente) REFERENCES ClientStandard_bank (cpf),
        FOREIGN KEY (cpf_destinatario) REFERENCES ClientStandard_bank (cpf)
    );

CREATE TABLE
    Deposit_bank (
        id SERIAL PRIMARY KEY,
        cpf VARCHAR(14),
        data_deposito DATE,
        valor DOUBLE,
        FOREIGN KEY (cpf) REFERENCES ClientStandard_bank (cpf)
    );

CREATE TABLE
    Endereco_bank (
        cpf VARCHAR(14) PRIMARY KEY,
        rua VARCHAR(255),
        numero INT,
        cidade VARCHAR(100),
        estado VARCHAR(100),
        cep VARCHAR(20),
        FOREIGN KEY (cpf) REFERENCES ClientStandard_bank (cpf)
    );

CREATE TABLE
    Balance_bank (
        cpf VARCHAR(14) PRIMARY KEY,
        saldo DOUBLE,
        limite DOUBLE,
        divida DOUBLE,
        FOREIGN KEY (cpf) REFERENCES ClientStandard_bank (cpf)
    );

CREATE TABLE
    Loan_bank (
        id SERIAL PRIMARY KEY,
        cpf VARCHAR(14),
        data_emprestimo DATE,
        valor DOUBLE,
        juros DOUBLE,
        data_vencimento DATE,
        FOREIGN KEY (cpf) REFERENCES ClientStandard_bank (cpf)
    );

/ / - > povoamento de dados
INSERT INTO
    ClientStandard_bank (nome, sobrenome, cpf, email, password, saldo)
VALUES
    (
        'João',
        'Silva',
        '12345678901',
        'joao.silva@example.com',
        'senha123',
        1000.00
    ),
    (
        'Maria',
        'Oliveira',
        '23456789012',
        'maria.oliveira@example.com',
        'senha456',
        2000.00
    ),
    (
        'Pedro',
        'Santos',
        '34567890123',
        'pedro.santos@example.com',
        'senha789',
        1500.00
    ),
    (
        'Ana',
        'Pereira',
        '45678901234',
        'ana.pereira@example.com',
        'senha101',
        2500.00
    ),
    (
        'Lucas',
        'Costa',
        '56789012345',
        'lucas.costa@example.com',
        'senha202',
        3000.00
    );

INSERT INTO
    Transfers_bank (
        cpf_remetente,
        cpf_destinatario,
        data_transferencia,
        valor
    )
VALUES
    (
        '12345678901',
        '23456789012',
        '2025-01-10',
        100.00
    ),
    (
        '23456789012',
        '34567890123',
        '2025-01-11',
        200.00
    ),
    (
        '34567890123',
        '45678901234',
        '2025-01-12',
        150.00
    ),
    (
        '45678901234',
        '56789012345',
        '2025-01-13',
        300.00
    ),
    ('56789012345', '12345678901', '2025-01-14', 50.00);

INSERT INTO
    Deposit_bank (cpf, data_deposito, valor)
VALUES
    ('12345678901', '2025-01-15', 500.00),
    ('23456789012', '2025-01-16', 1000.00),
    ('34567890123', '2025-01-17', 750.00),
    ('45678901234', '2025-01-18', 1200.00),
    ('56789012345', '2025-01-19', 900.00);

INSERT INTO
    Endereco_bank (cpf, rua, numero, cidade, estado, cep)
VALUES
    (
        '12345678901',
        'Rua A',
        123,
        'São Paulo',
        'SP',
        '01010-000'
    ),
    (
        '23456789012',
        'Rua B',
        456,
        'Rio de Janeiro',
        'RJ',
        '02020-000'
    ),
    (
        '34567890123',
        'Rua C',
        789,
        'Belo Horizonte',
        'MG',
        '03030-000'
    ),
    (
        '45678901234',
        'Rua D',
        101,
        'Curitiba',
        'PR',
        '04040-000'
    ),
    (
        '56789012345',
        'Rua E',
        112,
        'Salvador',
        'BA',
        '05050-000'
    );

INSERT INTO
    Balance_bank (cpf, saldo, limite, divida)
VALUES
    ('12345678901', 1000.00, 5000.00, 200.00),
    ('23456789012', 2000.00, 7000.00, 300.00),
    ('34567890123', 1500.00, 6000.00, 100.00),
    ('45678901234', 2500.00, 8000.00, 400.00),
    ('56789012345', 3000.00, 9000.00, 250.00);

INSERT INTO
    Loan_bank (
        cpf,
        data_emprestimo,
        valor,
        juros,
        data_vencimento
    )
VALUES
    (
        '12345678901',
        '2025-01-20',
        1000.00,
        5.0,
        '2026-01-20'
    ),
    (
        '23456789012',
        '2025-01-21',
        2000.00,
        4.5,
        '2026-01-21'
    ),
    (
        '34567890123',
        '2025-01-22',
        1500.00,
        6.0,
        '2026-01-22'
    ),
    (
        '45678901234',
        '2025-01-23',
        2500.00,
        3.5,
        '2026-01-23'
    ),
    (
        '56789012345',
        '2025-01-24',
        3000.00,
        4.0,
        '2026-01-24'
    );