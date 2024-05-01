--liquibase formatted sql

--changeset woloapp:015

ALTER TABLE report
    ADD COLUMN report_en TEXT;

ALTER TABLE report
    ADD COLUMN report_ua TEXT;

ALTER TABLE report
    ADD COLUMN report_ru TEXT;