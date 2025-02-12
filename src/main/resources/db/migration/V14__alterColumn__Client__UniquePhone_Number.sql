ALTER TABLE client
    ADD CONSTRAINT phone_number_unique UNIQUE (phone_number);
