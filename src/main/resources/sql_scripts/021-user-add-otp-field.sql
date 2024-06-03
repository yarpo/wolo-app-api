--liquibase formatted sql

--changeset woloapp:021
ALTER TABLE "user"
    ADD COLUMN active BOOLEAN DEFAULT FALSE,
    ADD COLUMN otp VARCHAR(6),
    ADD COLUMN otp_generated_time TIMESTAMP;

-- Updating existing records to set active = TRUE
UPDATE "user"
SET active = TRUE;