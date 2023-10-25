ALTER TABLE services
    ADD COLUMN staffid BINARY(16),
    MODIFY doctorid binary(16),
    DROP FOREIGN KEY services_ibfk_1;