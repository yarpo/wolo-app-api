--liquibase formatted sql

--changeset woloapp:008

--Summary: fills in missing data after removal of event_to_address.

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

WITH ranked AS (
    SELECT id, row_number() OVER (ORDER BY id) AS rank
    FROM shift
    LIMIT 44
)
UPDATE shift
SET address_id = ranked.rank,
    event_id = ranked.rank
    FROM ranked
WHERE shift.id = ranked.id;

UPDATE shift
SET address_id = 2, event_id = 2
WHERE id BETWEEN 45 AND 48;

UPDATE shift
SET address_id = 3 ,event_id = 3
WHERE id BETWEEN 49 AND 52;

UPDATE shift
SET address_id = 4, event_id = 4
WHERE id BETWEEN 53 AND 56;

UPDATE shift
SET address_id = 5, event_id = 5
WHERE id BETWEEN 57 AND 60;

UPDATE shift
SET address_id = 6, event_id = 6
WHERE id BETWEEN 61 AND 64;

UPDATE shift
SET address_id = 7, event_id = 7
WHERE id BETWEEN 65 AND 66;

UPDATE shift
SET address_id = 8, event_id = 8
WHERE id BETWEEN 67 AND 69;

UPDATE shift
SET address_id = 9, event_id = 9
WHERE id BETWEEN 70 AND 71;

UPDATE shift
SET address_id = 10, event_id = 10
WHERE id BETWEEN 72 AND 74;

UPDATE shift
SET address_id = 11, event_id = 11
WHERE id BETWEEN 75 AND 77;

UPDATE shift
SET address_id = 12, event_id = 12
WHERE id BETWEEN 78 AND 79;

UPDATE shift
SET address_id = 13, event_id = 13
WHERE id BETWEEN 80 AND 87;

UPDATE shift
SET address_id = 15, event_id = 15
WHERE id = 88;



