CREATE TABLE IF NOT EXISTS sale (
                      id SERIAL PRIMARY KEY,
                      client_id UUID NOT NULL,
                      sale_date DATE NOT NULL DEFAULT CURRENT_DATE,
                      total_price DECIMAL(10,2) NOT NULL CHECK (total_price >= 0),
                      payment_method VARCHAR(50) NOT NULL,
                      FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sale_product (
                              id SERIAL PRIMARY KEY,
                              sale_id INT NOT NULL,
                              product_id INT NOT NULL,
                              quantity INT NOT NULL CHECK (quantity > 0),
                              FOREIGN KEY (sale_id) REFERENCES sale(id) ON DELETE CASCADE,
                              FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
