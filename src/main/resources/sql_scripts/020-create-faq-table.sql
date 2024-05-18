--liquibase formatted sql

--changeset woloapp:019

-- Table: faq
CREATE TABLE IF NOT EXISTS faq (
                                   id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   question VARCHAR(255) NOT NULL,
                                   answer TEXT NOT NULL
);
