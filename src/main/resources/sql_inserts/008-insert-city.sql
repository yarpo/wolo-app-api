--liquibase formatted sql

--changeset woloapp:008

INSERT INTO city ("name")VALUES
                             ('Gdańsk'),
                             ('Warszawa'),
                             ('Poznań');