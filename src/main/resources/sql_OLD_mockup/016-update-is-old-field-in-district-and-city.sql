UPDATE city
SET is_old = TRUE
WHERE id = 5;

UPDATE district
SET is_old = TRUE
WHERE city_id = 5 ;

UPDATE district
SET is_old = TRUE
WHERE id BETWEEN 22 AND 23;
