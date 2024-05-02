--liquibase formatted sql

--changeset woloapp:014

-- move date column from table 'shift'
ALTER TABLE event
    ADD COLUMN "date" DATE;

-- add alt for images
ALTER TABLE event
    ADD COLUMN image_alt VARCHAR(255);

-- add columns with name translations
ALTER TABLE event
    RENAME COLUMN "name" TO name_pl;

ALTER TABLE event
    ADD COLUMN name_en VARCHAR(250);

ALTER TABLE event
    ADD COLUMN name_ru VARCHAR(250);

ALTER TABLE event
    ADD COLUMN name_ua VARCHAR(250);

-- add columns with description translations
ALTER TABLE event
    RENAME COLUMN description TO description_pl;

ALTER TABLE event
    ADD COLUMN description_en TEXT;

ALTER TABLE event
    ADD COLUMN description_ru TEXT;

ALTER TABLE event
    ADD COLUMN description_ua TEXT;
