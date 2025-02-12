CREATE TABLE IF NOT EXISTS supplier
(
    id           UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    cnpj         VARCHAR(18)  NOT NULL UNIQUE,
    email        VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(15)  NOT NULL UNIQUE,
    address      VARCHAR(255) NOT NULL,
    observation  VARCHAR(255)
);
