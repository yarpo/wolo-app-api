--liquibase formatted sql

--changeset woloapp:009
INSERT INTO privilege ("name")VALUES
                                  ('USERS_LIST_PDF');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
                                                          (2, 28);
