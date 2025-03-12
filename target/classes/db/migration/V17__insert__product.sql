INSERT INTO product (name, quantity, id_product_type, id_supplier, price, description)
VALUES ('Vinho Tinto Reserva', 50,
        (SELECT id FROM product_type WHERE name = 'Vinho'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor A'),
        120.99,
        'Vinho tinto encorpado, envelhecido em barris de carvalho.'),

       ('Espumante Brut', 30,
        (SELECT id FROM product_type WHERE name = 'Vinho'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor B'),
        89.50,
        'Espumante seco de alta qualidade, ideal para celebrações.'),

       ('Vinho Branco Chardonnay', 40,
        (SELECT id FROM product_type WHERE name = 'Vinho'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor C'),
        95.75,
        'Vinho branco suave, com notas frutadas e equilibrada acidez.'),

       ('Vodka Premium', 20,
        (SELECT id FROM product_type WHERE name = 'Vodka'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor D'),
        150.00,
        'Vodka destilada 5 vezes para máxima pureza.'),

       ('Whisky 12 anos', 15,
        (SELECT id FROM product_type WHERE name = 'Whisky'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor E'),
        250.00,
        'Whisky envelhecido por 12 anos em barris de carvalho.'),

       ('Cerveja Lager', 60,
        (SELECT id FROM product_type WHERE name = 'Cerveja'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor F'),
        12.50,
        'Cerveja clara e refrescante, com amargor equilibrado.'),

       ('Refrigerante Cola', 100,
        (SELECT id FROM product_type WHERE name = 'Refrigerante'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor G'),
        6.99,
        'Refrigerante de cola com sabor intenso e borbulhante.'),

       ('Cachaça Artesanal', 35,
        (SELECT id FROM product_type WHERE name = 'Cachaça'),
        (SELECT id FROM supplier WHERE name = 'Fornecedor H'),
        49.90,
        'Cachaça artesanal produzida com cana-de-açúcar selecionada.');
