--liquibase formatted sql

--changeset woloapp:020

-- INSERT INTO faq (question, answer) VALUES
--                                    ('Jak mogę sprawdzić wydarzenia z większym zapotrzebowaniem w danym momencie?', 'Aby sprawdzić wydarzenia z większym wymaganiem, trzeba wejść w zakładkę "Potrzebują Ciebie", gdzie wyświetlą się wydarzenia z krótkim odstępem czasowym do ich rozpoczęcia');

INSERT INTO privilege ("name")VALUES
                            ('CREATE_FAQ'),
                            ('EDIT_FAQ'),
                            ('DELETE_FAQ');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
                                                          (1, 37),
                                                          (1, 38),
                                                          (1, 39);