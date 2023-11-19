--liquibase formatted sql

--changeset woloapp:1
-- Table: address
CREATE TABLE IF NOT EXISTS address (
                                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                       street VARCHAR(50) NOT NULL,
                                       home_num VARCHAR(10) NOT NULL,
                                       district_id BIGINT NOT NULL,
                                       description VARCHAR(250) NOT NULL
);
-- Table: address_to_event
CREATE TABLE IF NOT EXISTS address_to_event (
                                                id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                                event_id BIGINT NOT NULL,
                                                address_id BIGINT NOT NULL
);
-- Table: category
CREATE TABLE IF NOT EXISTS category (
                                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        "name" VARCHAR(50) NOT NULL
);
-- Table: district
CREATE TABLE IF NOT EXISTS district (
                                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        "name" VARCHAR(50) NOT NULL,
                                        city VARCHAR(50) NOT NULL
);
-- Table: event
CREATE TABLE IF NOT EXISTS event (
                                     id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                     "name" VARCHAR(250) NOT NULL,
                                     description TEXT NOT NULL,
                                     category_id BIGINT,
                                     is_pesel_ver_req BOOLEAN NOT NULL,
                                     is_agreement_needed BOOLEAN NOT NULL,
                                     organisation_id BIGINT NOT NULL,
                                     image_url VARCHAR(255),
                                     is_approved BOOLEAN NOT NULL
);
-- Table: organisation
CREATE TABLE IF NOT EXISTS organisation (
                                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                            "name" VARCHAR(250) NOT NULL,
                                            description TEXT NOT NULL,
                                            email VARCHAR(50) NOT NULL,
                                            phone_num VARCHAR(9) NOT NULL,
                                            address_id BIGINT NOT NULL,
                                            is_approved BOOLEAN NOT NULL,
                                            moderator_id BIGINT NOT NULL,
                                            logo_url VARCHAR(255)
);
-- Table: role
CREATE TABLE IF NOT EXISTS role (
                                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    "name" VARCHAR(20) NOT NULL
);
-- Table: shift
CREATE TABLE IF NOT EXISTS shift (
                                     id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                     address_to_event_id BIGINT NOT NULL,
                                     start_time TIME NOT NULL,
                                     end_time TIME NOT NULL,
                                     "date" DATE NOT NULL,
                                     capacity INT NOT NULL,
                                     is_leader_required BOOLEAN NOT NULL,
                                     required_min_age INT NOT NULL
);
-- Table: shift_to_user
CREATE TABLE IF NOT EXISTS shift_to_user (
                                             id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                             user_id BIGINT NOT NULL,
                                             shift_id BIGINT NOT NULL,
                                             is_on_reserve_list BOOLEAN NOT NULL,
                                             is_leader BOOLEAN NOT NULL
);
-- Table: user
CREATE TABLE IF NOT EXISTS "user" (
                                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      firstname VARCHAR(50) NOT NULL,
                                      lastname VARCHAR(50) NOT NULL,
                                      email VARCHAR(50) NOT NULL,
                                      phone_number VARCHAR(9) NOT NULL,
                                      role_id BIGINT NOT NULL,
                                      is_pesel_verified BOOLEAN NOT NULL,
                                      is_agreement_signed BOOLEAN NOT NULL,
                                      is_adult BOOLEAN NOT NULL
);
-- Table: category_to_event
CREATE TABLE IF NOT EXISTS category_to_event (
                                     id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                     category_id BIGINT NOT NULL,
                                     event_id BIGINT NOT NULL
);

--changeset woloapp:2

CREATE UNIQUE INDEX idx_unique_email ON "user" (email);
-- foreign keys
-- Tabela: address
ALTER TABLE address
    ADD CONSTRAINT fk_address_district_id
        FOREIGN KEY (district_id)
            REFERENCES district (id);
-- Tabela: address_to_event
ALTER TABLE address_to_event
    ADD CONSTRAINT fk_address_to_event_event_id
        FOREIGN KEY (event_id)
            REFERENCES event (id),
    ADD CONSTRAINT fk_address_to_event_address_id
        FOREIGN KEY (address_id)
            REFERENCES address (id);
-- Tabela: event
ALTER TABLE event
    ADD CONSTRAINT fk_event_category_id
        FOREIGN KEY (category_id)
            REFERENCES category (id),
    ADD CONSTRAINT fk_event_organisation_id
        FOREIGN KEY (organisation_id)
            REFERENCES organisation (id);
-- Tabela: organisation
ALTER TABLE organisation
    ADD CONSTRAINT fk_organisation_address_id
        FOREIGN KEY (address_id)
            REFERENCES address (id)
            ON DELETE CASCADE,
    ADD CONSTRAINT fk_organisation_moderator_id
        FOREIGN KEY (moderator_id)
            REFERENCES "user" (id);
-- Tabela: shift
ALTER TABLE shift
    ADD CONSTRAINT fk_shift_address_to_event_id
        FOREIGN KEY (address_to_event_id)
            REFERENCES address_to_event (id);
-- Tabela: shift_to_user
ALTER TABLE shift_to_user
    ADD CONSTRAINT fk_shift_to_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    ADD CONSTRAINT fk_shift_to_user_shift_id
        FOREIGN KEY (shift_id)
            REFERENCES shift (id);
-- Tabela: user
ALTER TABLE "user"
    ADD CONSTRAINT fk_user_role_id
        FOREIGN KEY (role_id)
            REFERENCES role (id);
-- Tabela: category_to_event
ALTER TABLE category_to_event
    ADD CONSTRAINT fk_category_to_event_category_id
        FOREIGN KEY (category_id)
            REFERENCES category (id),
    ADD CONSTRAINT fk_category_to_event_event_id
        FOREIGN KEY (event_id)
            REFERENCES event (id);

CREATE OR REPLACE FUNCTION update_event_after_category_delete()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE event
    SET category_id = (SELECT id FROM category WHERE "name" = 'Podstawowa')
    WHERE category_id = OLD.id;
    RETURN NULL;
END $$ LANGUAGE plpgsql;

CREATE TRIGGER update_event_after_category_delete
    AFTER DELETE ON category
    FOR EACH ROW
EXECUTE FUNCTION update_event_after_category_delete();

INSERT INTO role ("name") VALUES
                              ( 'Administrator'),
                              ( 'Lider'),
                              ( 'Użytkownik');

INSERT INTO district ( "name", city) VALUES
                                         ( 'Centrum', 'Warszawa'),
                                         ( 'Wrzeszcz', 'Gdańsk'),
                                         ( 'Śródmieście', 'Gdańsk');

INSERT INTO category ( "name") VALUES
                                   ( 'Sport'),
                                   ( 'Pomoc'),
                                   ( 'Edukacja'),
                                   ( 'Podstawowa');

