--liquibase formatted sql

--changeset woloapp:002

INSERT INTO "user" ( firstname, lastname, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, "password") VALUES
                                                                                                                                                 ( 'Jan', 'Kowalski', 'jan.kowalski@example.com', '123456789', true, true, true, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi'),
                                                                                                                                                 ( 'Anna', 'Nowak', 'anna.nowak@example.com', '987654321', false, true, true, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi'),
                                                                                                                                                 ( 'Piotr', 'Wójcik', 'piotr.wojcik@example.com', '456789123', true, false, false, 13, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi');
INSERT INTO user_to_role (role_id, user_id) VALUES
                                                (1, 2),
                                                (3, 3),
                                                (2, 3),
                                                (2, 4),
                                                (3, 4);

INSERT INTO shift_to_user ( user_id, shift_id, is_on_reserve_list, is_leader) VALUES
                                                                                  ( 2, 1, false, true),
                                                                                  ( 3, 2, false, false),
                                                                                  ( 4, 3, true, false);
