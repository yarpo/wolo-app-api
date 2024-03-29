--liquibase formatted sql

--changeset :006
INSERT INTO privilege ("name")VALUES
                                  ('CREATE_REPORT'),
                                  ('EDIT_REPORT'),
                                  ('DELETE_REPORT');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
                                                          (2, 25),
                                                          (1, 25),
                                                          (2, 26),
                                                          (1, 26),
                                                          (2, 27),
                                                          (1, 27);