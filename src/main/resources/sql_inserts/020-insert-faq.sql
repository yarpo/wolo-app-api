--liquibase formatted sql

--changeset woloapp:020

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Chce zapisać się na wydarzenie, jak to mogę zrobić?',
     'Wejdź w zakładkę "Wszystkie wydarzenia" i wybierz interesujące cię wydarzenie, następnie sprawdź dostępne zmiany na nim i zapisz na tą odpowiadającą Tobie.',
     'Want to register for an event, how can I do it?',
     'Go to the "All Events" tab and select the event you are interested in, then check the available changes on it and write to the one that corresponds to you.',
     'Ви хочете записатися на події, як я можу це зробити?',
     'Перейти на закладку "Все події" і виберите event, який вас цікавить, а потім перевірите доступні зміни на ньому і запишите на той, що відповідає вам.',
     'Хочу записаться на мероприятие, как я могу это сделать?',
     'Зайдите во вкладку "Все мероприятия" и выберите интересующее вас мероприятие, затем проверьте доступные изменения и запишитесь на подходящее вам.');

INSERT INTO privilege ("name") VALUES
                            ('CREATE_FAQ'),
                            ('EDIT_FAQ'),
                            ('DELETE_FAQ');

INSERT INTO privilege_to_role (role_id, privilege_id) VALUES
                                                          (1, 37),
                                                          (1, 38),
                                                          (1, 39);