--liquibase formatted sql

--changeset woloapp:008

UPDATE event
    SET city_id = 1
    WHERE id IN (
        SELECT id
        FROM event
        ORDER BY id
        LIMIT 44
    );

UPDATE district
    SET city_id = 2
        WHERE id = (SELECT id FROM district ORDER BY id LIMIT 1);
UPDATE district
    SET city_id = 1
        WHERE id IN (
            SELECT id FROM district ORDER BY id LIMIT 12 OFFSET 1
        );