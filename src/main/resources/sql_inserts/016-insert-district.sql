--liquibase formatted sql

--changeset woloapp:014
INSERT INTO district ("name", city_id) VALUES
                                          ('Stare Miasto', 3),
                                          ('Jeżyce', 3),
                                          ('Grunwald', 3),
                                          ('Wilda', 3),
                                          ('Nowe Miasto', 3);


INSERT INTO district ("name", city_id) VALUES
                                          ('Śródmieście', 2),
                                          ('Mokotów', 2),
                                          ('Praga-Południe', 2),
                                          ('Wola', 2),
                                          ('Ursynów', 2);

INSERT INTO district ("name", city_id) VALUES
                                         ( 'Centrum', 2),
                                         ( 'Wrzeszcz', 1),
                                         ( 'Śródmieście', 1),
                                         ( 'Oliwa', 1),
                                         ( 'Aniołki', 1),
                                         ( 'Morena', 1),
                                         ( 'Orunia', 1),
                                         ( 'Jelitkowo', 1),
                                         ( 'Przymorze', 1),
                                         ( 'Zaspa', 1),
                                         ( 'Ujeścisko-Łostowice', 1),
                                         ( 'Siedlce', 1),
                                         ( 'Nowy Port', 1),
                                         ( 'Żoliborz', 2);