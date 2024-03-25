--liquibase formatted sql

--changeset kwajda:004
INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, "password") VALUES
                                                                                                                                                  ( 'Admin', 'Admin', 'admin@example.com', '000000000', true, true, true, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi');

INSERT INTO user_to_role (role_id, user_id) VALUES
                                                (1, 1);