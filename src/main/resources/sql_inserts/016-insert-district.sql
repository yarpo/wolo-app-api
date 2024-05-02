--liquibase formatted sql

--changeset woloapp:014
INSERT INTO district ("name", city_id) VALUES
                                          ('Stare Miasto', 5),
                                          ('Jeżyce', 5),
                                          ('Grunwald', 5),
                                          ('Wilda', 5),
                                          ('Nowe Miasto', 5);


INSERT INTO district ("name", city_id) VALUES
                                          ('Śródmieście', 2),
                                          ('Mokotów', 2),
                                          ('Praga-Południe', 2),
                                          ('Wola', 2),
                                          ('Ursynów', 2);