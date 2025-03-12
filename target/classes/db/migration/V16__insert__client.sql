INSERT INTO client (id, name, email, phone_number, address, date_brith, cpf)
VALUES (gen_random_uuid(), 'João Silva', 'joao.silva@email.com', '11999999999', 'Rua das Flores, 123 - São Paulo',
        '1985-03-15', '123.456.789-01'),
       (gen_random_uuid(), 'Maria Souza', 'maria.souza@email.com', '11988888888', 'Av. Brasil, 456 - Rio de Janeiro',
        '1990-07-22', '987.654.321-00'),
       (gen_random_uuid(), 'Carlos Pereira', 'carlos.pereira@email.com', '11977777777',
        'Rua Central, 789 - Belo Horizonte', '1982-11-30', '456.123.789-10');
