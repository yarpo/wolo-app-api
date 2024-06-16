--liquibase formatted sql

--changeset woloapp:023

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Czym jest weryfikacja PESEL i jak mogę ją uzyskać?',
     'Aby uzyskać weryfikację PESEL, należy skontaktować się z administratorem platformy, który przeprowadzi proces weryfikacji.',
     'What is PESEL verification and how can I get it?',
     'To obtain PESEL verification, you must contact the platform administrator, who will conduct the verification process.',
     'Що таке верифікація PESEL і як її отримати?',
     'Щоб отримати верифікацію PESEL, ви повинні зв''язатися з адміністратором платформи, який проведе процес верифікації.',
     'Что такое верификация PESEL и как её получить?',
     'Чтобы получить верификацию PESEL, вам нужно связаться с администратором платформы, который проведет процесс верификации.');

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Czym jest porozumienie wolontariuszy i jak mogę je uzyskać?',
     'Porozumienie wolontariuszy to dokument, który zawiera informacje o wolontariuszu, organizacji i wydarzeniu, w którym wolontariusz bierze udział. Aby uzyskać porozumienie, należy skontaktować się z organizatorem wydarzenia.',
     'What is a volunteer agreement and how can I get it?',
     'The volunteer agreement is a document that contains information about the volunteer, the organization and the event in which the volunteer participates. To obtain an agreement, you must contact the event organizer.',
     'Що таке договір волонтера і як його отримати?',
     'Договір волонтера – це документ, який містить інформацію про волонтера, організацію та захід, у якому бере участь волонтер. Щоб отримати договір, потрібно зв''язатися з організатором заходу.',
     'Что такое соглашение волонтера и как его получить?',
     'Соглашение волонтера – это документ, который содержит информацию о волонтёре, организации и мероприятии, в котором участвует волонтёр. Чтобы получить соглашение, нужно связаться с организатором мероприятия.');

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Czy na stronie znajduje się jakaś forma historii wydarzeń, w których brało się udział?',
     'Tak, w zakładce "Twoja Strona", znajduje się lista "Twoje Przeszłe Wydarzenia", w której znajdują się wszystkie twoje przeszłe wydarzenia.',
     'Is there any form of history of events in which you participated on the website?',
     'Yes, on the "Your Page" tab there is a list of "Your Past Events", which contains all the events you participated in.',
     'Чи є на сайті якась форма історії подій, в яких брали участь?',
     'Так, на вкладці "Ваша сторінка" є список "Ваші минулі події", в якому містяться всі події, в яких ви брали участь.',
     'Есть ли на сайте какая-либо форма истории событий, в которых вы участвовали?',
     'Да, на вкладке "Ваша страница" есть список "Ваши прошлые события", в котором содержатся все события, в которых вы участвовали.');

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Co zrobić, gdy chce zapisać się na daną zmianę wydarzenia, którego termin pokrywa się z innym wydarzeniem, do którego się przypisało?',
     'Jeśli chcesz zapisać się na zmianę z pokrywającym się terminem, musisz wejść w zakładkę "Twoja Strona" i wypisać się z pokrywającego się wydarzenia, a następnie zapisać się na interesującą cię zmianę.',
     'What to do when you want to sign up for a given event change, the date of which overlaps with another event to which you have been assigned?',
     'If you want to sign up for a change with an overlapping date, you need to go to the "Your Page" tab and unsubscribe from the overlapping event, and then sign up for the change you are interested in.',
     'Що робити, коли хочете записатися на певну зміну події, дата якої перекривається з іншою подією, до якої ви призначилися?',
     'Якщо ви хочете записатися на зміну з перекриваючою датою, вам потрібно перейти на вкладку "Ваша сторінка" і відписатися від перекриваючої події, а потім записатися на зміну, яка вас цікавить.',
     'Что делать, когда хотите записаться на определенное изменение события, дата которого перекрывается с другим событием, на которое вы были назначены?',
     'Если вы хотите записаться на изменение с перекрывающейся датой, вам нужно перейти на вкладку "Ваша страница" и отписаться от перекрывающегося события, а затем записаться на интересующее вас изменение.');


INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Jako przedstawiciel organizacji, jak moge zarejestrować organizacje do platformy?',
     'Aby zarejestrować organizacje, organizator musi się zgłosić do administratora z potrzebnymi danymi. Administrator wtedy utworzy organizację na platformie i dopisze użytkownika jako moderatora. Email do kontaktu: woloappinfo@gmail.com',
     'As a representative of an organization, how can I register an organization on the platform?',
     'To register an organization, the organizer must contact the administrator with the necessary data. The administrator will then create an organization on the platform and add the user as a moderator. Email for contact: woloappinfo@gmail.com',
     'Як представник організації, як я можу зареєструвати організацію на платформі?',
     'Щоб зареєструвати організацію, організатор повинен звернутися до адміністратора з необхідними даними. Адміністратор тоді створить організацію на платформі і додасть користувача як модератора. Електронна адреса для контакту: woloappinfo@gmail.com',
     'Как представитель организации, как я могу зарегистрировать организацию на платформе?',
     'Чтобы зарегистрировать организацию, организатор должен связаться с администратором с необходимыми данными. Администратор создаст организацию на платформе и добавит пользователя в качестве модератора. Электронная почта для контакта: woloappinfo@gmail.com');

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Co zrobić, gdy chce dodać informacje na temat wydarzenia w innych językach, niż ten, w którym wydarzenie jest dodawane?',
     'Aplikacja sama tłumaczy wydarzenia na każdy inny obsługiwany język. Jeśli tłumaczenie danych informacji nie będzie zadowalające, można je zedytować w zakładce "Zarządzaj organizacją".',
     'What to do when you want to add informations about an event in languages other than the one in which the event is added?',
     'The application automatically translates events into any other supported language. If the translation of the information is unsatisfactory, you can edit it in the "Organiser Page" tab.',
     'Що робити, коли хочете додати інформацію про подію на інших мовах, ніж та, на якій подія додається?',
     'Додаток автоматично перекладає події на будь-яку іншу підтримувану мову. Якщо переклад інформації не задовільний, ви можете відредагувати його на вкладці "Управління організацією".',
     'Что делать, когда хотите добавить информацию о событии на других языках, кроме того, на котором событие добавляется?',
     'Приложение автоматически переводит события на любой другой поддерживаемый язык. Если перевод информации неудовлетворительный, вы можете отредактировать его на вкладке "Управление организацией".');

INSERT INTO faq (question_pl, answer_pl, question_en, answer_en, question_ua, answer_ua, question_ru, answer_ru) VALUES
    ('Gdzie mogę przekazać informacje na temat stanu wydarzenia, po jego zakończeniu?',
     'W zakładce "Zarządzaj organizacją" znajduje się możliwość dodania do konkretnego wydarzenia raportu, który wyświetli się na stronie wydarzenia.',
     'Where can I provide information about the status of the event after it is finished?',
     'In the "Organiser Page" tab, there is an option to add a report to a specific event, which will be displayed on the event page.',
     'Де я можу передати інформацію про стан події після її завершення?',
     'На вкладці "Управління організацією" є можливість додати до конкретної події звіт, який відобразиться на сторінці події.',
     'Где я могу передать информацию о состоянии события после его завершения?',
     'На вкладке "Управление организацией" есть возможность добавить к конкретному событию отчет, который отобразится на странице события.');
