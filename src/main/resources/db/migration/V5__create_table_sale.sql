CREATE TABLE sale(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  id_client UUID NOT NULL,
  total_value NUMERIC,
  discount NUMERIC,
  payment_method VARCHAR(255),
  CONSTRAINT fk_id_client FOREIGN KEY (id_client) REFERENCES client (id)
);