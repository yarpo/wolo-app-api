--liquibase formatted sql

--changeset woloapp:011
INSERT INTO privilege ("name")VALUES
                                  ('CREATE_CITY'),
                                  ('EDIT_CITY'),
                                  ('DELETE_CITY');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
    (1, 29),
    (1, 30),
    (1, 31),
    (1, 32);
