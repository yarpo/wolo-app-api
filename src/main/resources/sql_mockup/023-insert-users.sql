--liquibase formatted sql

--changeset woloapp:023
-- final mockup data


INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Jan',
     'Kowalski',
     'jan.kowalski@example.pl',
     '000000000',
     true,
     '',
     '',
     '',
     '',
     true,
     '',
     '');

INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Anna',
     'Nowak',
     'anna.nowak@example.pl',
     '111111111',
     true,
     '',
     '',
     '',
     '',
     true,
     '',
     '');

INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    (
     'Piotr',
     'Wójcik',
     'piotr.wojcik@example.com',
     '222222222',
     true,
     '',
     '',
     '',
     true,
     '',
     '');

INSERT INTO "user" ( first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    (
     'Nikodem',
     'Kowalczyk',
     'nikodem.kowalczyk@example.com',
     '333333333',
     false,
     '',
     '',
     '',
     true,
     '',
     '');

INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Julia',
     'Kaczmarek',
     'julia.kaczmarek@example.com',
     '444444444',
     true,
     '',
     '',
     '',
     '',
     true,
     '',
     '');
INSERT INTO "user" (first_name, last_name, email, phone_number, is_pesel_verified, is_agreement_signed, is_adult, organisation_id, password, active,otp,otp_generated_time) VALUES
    ('Aleksander',
     'Mazur',
     'aleksander.mazur@example.com',
     '555555555',
     true,
     '',
     '',
     '',
     '',
     true,
     '',
     '');

-- insert shift_to_user
INSERT INTO shift_to_user

-- user_to_role
