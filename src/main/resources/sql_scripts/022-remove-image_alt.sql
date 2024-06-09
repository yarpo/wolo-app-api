--liquibase formatted sql

--changeset woloapp:022

ALTER TABLE event DROP COLUMN image_alt;