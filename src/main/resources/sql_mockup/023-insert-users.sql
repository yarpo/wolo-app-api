--liquibase formatted sql

--changeset woloapp:023
-- final mockup data


INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Jan',
     'Kowalski',
     'jan.kowalski@example.pl',
     '000000000',
     true,
     true,
     true,
     1,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);

INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Anna',
     'Nowak',
     'anna.nowak@example.pl',
     '111111111',
     true,
     true,
     true,
     null,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);

INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    (
     'Piotr',
     'Wójcik',
     'piotr.wojcik@example.com',
     '222222222',
     true,
     true,
     true,
     2,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);

INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    (
     'Nikodem',
     'Kowalczyk',
     'nikodem.kowalczyk@example.com',
     '333333333',
     false,
     true,
     true,
     null,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);

INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Julia',
     'Kaczmarek',
     'julia.kaczmarek@example.com',
     '444444444',
     true,
     false,
     true,
     null,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);
INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Aleksander',
     'Mazur',
     'aleksander.mazur@example.com',
     '555555555',
     true,
     true,
     false,
     null,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);

INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Antoni',
     'Krawczyk',
     'antoni.krawczyk@example.com',
     '666666666',
     true,
     true,
     true,
     3,
     '$2a$10$fkGJzWRkLY5JzUHqTBzZ4OTDzp3bOTMEI9ji8x6tRC6T6oQED3cSm',
     true,
     null,
     null);


INSERT INTO user_to_role (role_id, user_id)
VALUES (3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),(2,2),(2,4),(2,8);

