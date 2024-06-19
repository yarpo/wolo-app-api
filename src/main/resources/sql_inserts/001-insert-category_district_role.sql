--liquibase formatted sql

--changeset woloapp:001

INSERT INTO category ( "name") VALUES
                                   ( 'Kultura'),
                                   ( 'Sport'),
                                   ( 'Edukacja'),
                                   ( 'Ekologia'),
                                   ( 'Pomoc'),
                                   ( 'Podstawowa');

INSERT INTO "role" ("name") VALUES
                                ( 'ADMIN'),
                                ( 'MODERATOR'),
                                ( 'USER');