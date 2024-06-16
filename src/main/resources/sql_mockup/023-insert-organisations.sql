--liquibase formatted sql

--changeset woloapp:023
-- final mockup data

INSERT INTO organisation ("name", email, phone_num, address_id, is_approved, logo_url, description_pl, description_en, description_ua, description_ru)
VALUES ('PJATK', 'pjatk@example.com', '123456789', 1, true,
        'https://activfirst.co.uk/wp-content/uploads/logo-example.jpg',
        'PJATK to wiodąca prywatna uczelnia, która regularnie organizuje różne wydarzenia, takie jak konferencje, warsztaty, wystawy oraz spotkania networkingowe. Uczestniczymy także w akcjach charytatywnych, angażując społeczność uczelni w pomoc potrzebującym.',
        'PJATK is a leading private university that regularly organizes various events such as conferences, workshops, exhibitions, and networking meetings. We also participate in charitable initiatives, involving our university community in helping those in need.',
        'PJATK є провідним приватним університетом, який регулярно організовує різноманітні події, такі як конференції, семінари, виставки та мережеві зустрічі. Ми також беремо участь у благодійних ініціативах, залучаючи наше університетське співтовариство до допомоги потребуючим.',
        'PJATK — ведущий частный университет, регулярно організуючи різні заходи, такі як конференції, семінари, виставки та мережеві зустрічі. Ми також беремо участь у благодійних ініціативах, залучаючи наше університетське співтовариство в допомогу потребуючим.');

INSERT INTO organisation ("name", email, phone_num, address_id, is_approved, logo_url, description_pl, description_en, description_ua, description_ru)
VALUES ('Nadzieja', 'nadzieja@example.com', '987654321', 2, true,
        'https://s3.amazonaws.com/www-inside-design/uploads/2019/05/woolmarkimagelogo-1024x576.png',
        'Nadzieja to globalna organizacja humanitarna, która chroni prawa dzieci i poprawia ich życie. Działa w ponad 190 krajach, oferując pomoc w sytuacjach kryzysowych oraz wsparcie w dziedzinach zdrowia, edukacji i ochrony dzieci.',
        'Nadzieja is a global humanitarian organization that protects children''s rights and improves their lives. It operates in over 190 countries, providing emergency assistance and support in health, education, and child protection.',
        'Nadzieja — це глобальна гуманітарна організація, яка захищає права дітей і покращує їхнє життя. Вона діє в понад 190 країнах, надаючи екстрену допомогу та підтримку в галузі охорони здоров''я, освіти та захисту дітей.',
        'Nadzieja — это глобальная гуманитарная организация, которая защищает права детей и улучшает их жизнь. Она работает более чем в 190 странах, предоставляя экстренную помощь и поддержку в области здравоохранения, образования и защиты детей');

INSERT INTO organisation ("name", email, phone_num, address_id, is_approved, logo_url, description_pl, description_en, description_ua, description_ru)
VALUES ('Red Cross', 'red.cross@example.com', '123123123', 3, true,
        'https://vme-stiftung.de/wp-content/uploads/2019/09/example-logo-2.jpg',
        'Red Cross to międzynarodowa organizacja humanitarna, która pomaga ludziom w potrzebie, chroni życie i zdrowie oraz promuje godność ludzką poprzez udzielanie pomocy w sytuacjach kryzysowych i wspieranie lokalnych społeczności.',
        'Red Cross is an international humanitarian organization that helps people in need, protects life and health, and promotes human dignity by providing assistance in crisis situations and supporting local communities.',
        'Червоний Хрест — це міжнародна гуманітарна організація, яка допомагає людям у потребі, захищає життя та здоров''я і просуває людську гідність, надаючи допомогу в надзвичайних ситуаціях і підтримуючи місцеві спільноти.',
        'Красный Крест — это международная гуманитарная организация, которая помогает людям в беде, защищает жизнь и здоровье и продвигает человеческое достоинство, предоставляя помощь в чрезвычайных ситуациях и поддерживая местные сообщества.');
