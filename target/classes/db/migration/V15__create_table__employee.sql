CREATE TABLE employee
(
    id           UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    date_birth   DATE         NOT NULL,
    cpf          VARCHAR(14)  NOT NULL UNIQUE,
    address      VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(15)  NOT NULL UNIQUE
);