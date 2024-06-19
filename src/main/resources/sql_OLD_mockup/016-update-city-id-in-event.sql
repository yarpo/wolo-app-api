
UPDATE event
SET city_id = 5
WHERE id BETWEEN 16 AND 17;

UPDATE event
SET city_id = 2
WHERE id BETWEEN 14 AND 15;

INSERT INTO address (street, home_num, district_id) VALUES
                                                        ('Stolarska', '25/1', 14),
                                                        ('Świętosławska', '2A/4', 15),
                                                        ('Wodna', '8', 16),
                                                        ('Klasztorna', '67', 14),
                                                        ('Wrocławska', '82', 14),
                                                        ('Grunwaldzka', '9', 15),
                                                        ('Dąbrowskiego', '8', 15),
                                                        ('Kościuszki', '6E', 15),
                                                        ('Kubusia Puchatka', '4', 16),
                                                        ('Piastowska', '90B', 19),
                                                        ('Poczty Gdańskiej', '7', 21),
                                                        ('Marii Skłodowskiej-Curie', '3b', 12),
                                                        ('Wronia', '31', 22),
                                                        ('Daniela Chodowieckiego', '7', 23);

UPDATE shift
SET address_id = 58
WHERE id = 17;

UPDATE shift
SET address_id = 61
WHERE id = 16;

UPDATE shift
SET address_id = 62
WHERE id = 14;

UPDATE shift
SET address_id = 63
WHERE id = 15;


