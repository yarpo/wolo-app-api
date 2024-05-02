--liquibase formatted sql

--changeset woloapp:015

ALTER TABLE report
    RENAME COLUMN report TO report_pl;

ALTER TABLE report
    ADD COLUMN report_en TEXT;

ALTER TABLE report
    ADD COLUMN report_ua TEXT;

ALTER TABLE report
    ADD COLUMN report_ru TEXT;
