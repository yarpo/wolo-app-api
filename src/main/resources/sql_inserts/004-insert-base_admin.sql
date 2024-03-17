--liquibase formatted sql

--changeset kwajda:004
INSERT INTO "user" ( firstname, lastname, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, "password") VALUES
                                                                                                                                                  ( 'Admin', 'Admin', 'admin@example.com', '000000000', true, true, true, null, 'null');

INSERT INTO user_to_role (role_id, user_id) VALUES
                                                (1, 1);