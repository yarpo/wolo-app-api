--liquibase formatted sql

--changeset woloapp:017
INSERT INTO privilege ("name")VALUES
                                  ('READ_ALL_CITY'),
                                  ('READ_ALL_DISTRICT'),
                                  ('READ_ALL_ORGANISATION');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
                                                          (1, 34),
                                                          (1, 35),
                                                          (1, 36);
