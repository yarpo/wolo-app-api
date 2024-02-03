--liquibase formatted sql

--changeset woloapp:3
ALTER TABLE address
    RENAME COLUMN description TO description_pl;

ALTER TABLE address
    ADD COLUMN description_en VARCHAR(250),
    ADD COLUMN description_ua VARCHAR(250),
    ADD COLUMN description_ru VARCHAR(250);

ALTER TABLE event
    RENAME COLUMN "name" TO name_pl;

ALTER TABLE event
    ADD COLUMN name_en VARCHAR(250),
    ADD COLUMN name_ua VARCHAR(250),
    ADD COLUMN name_ru VARCHAR(250);

ALTER TABLE event
    RENAME COLUMN description TO description_pl;

ALTER TABLE event
    ADD COLUMN description_en TEXT,
    ADD COLUMN description_ua TEXT,
    ADD COLUMN description_ru TEXT;

