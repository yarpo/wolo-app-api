--liquibase formatted sql

--changeset woloapp:013
INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, "password") VALUES
                                                                                                                                                  ( 'Kevin', 'User', 'kevin.user@example.com', '033333333', true, true, false, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi'),
                                                                                                                                                  ( 'Renata', 'Użytkownik', 'renata.uzytkownik@example.com', '032222222', true, true, true, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi'),
                                                                                                                                                  ( 'Robert', 'Example', 'robert.example@example.com', '031111111', true, true, true, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi'),
                                                                                                                                                  ( 'Jan', 'Test', 'jan.test@example.com', '088888888', true, true, false, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi'),
                                                                                                                                                  ( 'Johnny', 'One', 'johnny.one@example.com', '021333333', true, true, true, null, '$2a$10$mX6Rc53zw6lVn6YBk3hZ6.NTf9P7DpH/ahRRovBwzUqk74YZyhHKi');


INSERT INTO user_to_role (role_id, user_id) VALUES
                                                (3, 12),
                                                (3, 13),
                                                (3, 14),
                                                (3, 15),
                                                (3, 16);

INSERT INTO shift_to_user ( user_id, shift_id, is_on_reserve_list, is_leader) VALUES
                                                                                  ( 12, 19, false, false),
                                                                                  ( 12, 29, false, false),
                                                                                  ( 12, 33, true, false),
                                                                                  ( 13, 19, false, false),
                                                                                  ( 13, 29, false, false),
                                                                                  ( 14, 33, true, false),
                                                                                  ( 14, 15, false, false),
                                                                                  ( 14, 29, false, false),
                                                                                  ( 15, 48, true, false),
                                                                                  ( 15, 69, false, false),
                                                                                  ( 15, 70, false, false),
                                                                                  ( 16, 34, true, false),
                                                                                  ( 16, 70, true, false);