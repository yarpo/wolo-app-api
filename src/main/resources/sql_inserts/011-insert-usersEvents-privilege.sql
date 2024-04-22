--liquibase formatted sql

--changeset woloapp:012
INSERT INTO privilege ("name")VALUES
    ('READ_USERS_EVENTS');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
    (2, 33),
    (3, 33);