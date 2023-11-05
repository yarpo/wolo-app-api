--liquibase formatted sql
--changeset woloapp:1
CREATE TABLE example (
                         id serial PRIMARY KEY,
                         name VARCHAR (255) NOT NULL
);