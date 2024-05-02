--liquibase formatted sql

--changeset woloapp:014

-- move date column to table 'event'
ALTER TABLE shift DROP COLUMN "date";

-- add columns with direction translations
ALTER TABLE shift
    RENAME COLUMN directions TO directions_pl;

ALTER TABLE shift
    ADD COLUMN directions_en VARCHAR(255);

ALTER TABLE shift
    ADD COLUMN directions_ru VARCHAR(255);

ALTER TABLE shift
    ADD COLUMN directions_ua VARCHAR(255);