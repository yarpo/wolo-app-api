--liquibase formatted sql

--changeset woloapp:019

-- Table: faq
CREATE TABLE IF NOT EXISTS faq (
                                   id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   question_pl VARCHAR(255) NOT NULL,
                                   answer_pl TEXT NOT NULL,
                                   question_en VARCHAR(255) NOT NULL,
                                   answer_en TEXT NOT NULL,
                                   question_ua VARCHAR(255) NOT NULL,
                                   answer_ua TEXT NOT NULL,
                                   question_ru VARCHAR(255) NOT NULL,
                                   answer_ru TEXT NOT NULL
);
