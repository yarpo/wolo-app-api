--liquibase formatted sql

--changeset woloapp:005
ALTER TABLE "user" RENAME COLUMN firstname TO first_name;
ALTER TABLE "user" RENAME COLUMN lastname TO last_name;