--liquibase formatted sql

--changeset woloapp:008

ALTER TABLE district DROP COLUMN city;

-- Table: city
CREATE TABLE IF NOT EXISTS city (
                                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        "name" VARCHAR(50) NOT NULL,
                                        is_old BOOLEAN DEFAULT false
);

ALTER TABLE district
    ADD COLUMN city_id BIGINT;

ALTER TABLE district
    ADD CONSTRAINT fk_district_city
        FOREIGN KEY (city_id)
            REFERENCES city (id);

ALTER TABLE event
    ADD COLUMN city_id BIGINT;

ALTER TABLE event
    ADD CONSTRAINT fk_event_city_id
        FOREIGN KEY (city_id)
            REFERENCES city (id);

DROP TABLE IF EXISTS adress_to_event;