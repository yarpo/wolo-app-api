--liquibase formatted sql

--changeset woloapp:019

DELETE FROM privilege_to_role
WHERE role_id = 3 AND privilege_id = 19;