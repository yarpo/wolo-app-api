--liquibase formatted sql

--changeset woloapp:001

INSERT INTO category ( "name") VALUES
                                   ( 'Kultura'),
                                   ( 'Sport'),
                                   ( 'Edukacja'),
                                   ( 'Ekologia'),
                                   ( 'Pomoc'),
                                   ( 'Podstawowa');

INSERT INTO district ( "name", city) VALUES
                                         ( 'Centrum', 'Warszawa'),
                                         ( 'Wrzeszcz', 'Gdańsk'),
                                         ( 'Śródmieście', 'Gdańsk'),
                                         ( 'Oliwa', 'Gdańsk'),
                                         ( 'Aniołki', 'Gdańsk'),
                                         ( 'Morena', 'Gdańsk'),
                                         ( 'Orunia', 'Gdańsk'),
                                         ( 'Jelitkowo', 'Gdańsk'),
                                         ( 'Przymorze', 'Gdańsk'),
                                         ( 'Zaspa', 'Gdańsk'),
                                         ( 'Ujeścisko-Łostowice', 'Gdańsk'),
                                         ( 'Siedlce', 'Gdańsk'),
                                         ( 'Nowy Port', 'Gdańsk');
INSERT INTO "role" ("name") VALUES
                                ( 'ADMIN'),
                                ( 'MODERATOR'),
                                ( 'USER');