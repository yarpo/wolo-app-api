--liquibase formatted sql

--changeset woloapp:007

INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, "password") VALUES
                                                                                                                                                  ( 'John', 'Doe', 'john.doe@example.com', '111111111', true, true, true, null, '$2a$10$UPdnqe99llJJGnLtuQmTtuQq9jveK6hnVwSN39Kda6EReka4GNxza'),
                                                                                                                                                  ( 'Nikodem', 'Kowalczyk', 'nikodem.kowalczyk@example.com', '444555666', true, true, true, null, '$2a$10$A17RXJZyPgicC.0C7viS4OadAqOMAkoEZpK1OOLNwXjdjZypSHyUy'),
                                                                                                                                                  ( 'Aleksander', 'Mazur', 'aleksander.mazur@example.com', '666555444', true, true, true, 4, '$2a$10$oH8iTe/894PN8rKnCrJhAeiSjcO6M1MAIvUoyRmfzMsoSMpKLI36q'),
                                                                                                                                                  ( 'Antoni', 'Krawczyk', 'antoni.krawczyk@example.com', '321321321', true, true, true, null, '$2a$10$SM0IgDKVKQzZZifY3Rk7geYTTVFzQ6dCLOWdSRzFjLb4Wfyp6U1ce'),
                                                                                                                                                  ( 'Julia', 'Kaczmarek', 'julia.kaczmarek@example.com', '123123123', true, true, true, 7, '$2a$10$X9LOjXMc8dvhQ4396s7d/eVXZEyiW5tbZAbMqFD6gfdndscdkRSQe');
INSERT INTO user_to_role (role_id, user_id) VALUES
                                                (2, 5),
                                                (3, 6),
                                                (2, 7),
                                                (1, 8),
                                                (2, 9);
