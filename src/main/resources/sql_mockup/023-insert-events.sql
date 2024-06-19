--liquibase formatted sql

--changeset woloapp:023
-- final mockup data
-- past events
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Kiermasz Dobroczynności: Tworzymy Nadzieję',
        'Jeśli chciałbyś/aś pomóc w organizacji kiermaszu dobroczynności, towarzysząc nam na tej wspólnej misji, zapraszamy serdecznie! Twoja obecność tworzy nadzieję i radość dla innych.',
        'Charity Fair: Creating Hope',
        'If you would like to help in organizing a charity fair, joining us on this shared mission, you are warmly invited! Your presence creates hope and joy for others.',
        'Благотворительная ярмарка: Создаем надежду',
        'Если вы хотите помочь в организации благотворительной ярмарки, присоединяйтесь к нам в этом совместном деле. Ваше присутствие создает надежду и радость для других.',
        'Благодійний ярмарок: Створюємо Надію',
        'Якщо ви хочете допомогти у організації благодійного ярмарку, приєднуючись до нас у цій спільній місії, ми щиро запрошуємо вас! Ваша присутність створює надію і радість для інших.' ,
        false,
        false,
        1,
        'https://images.unsplash.com/photo-1523473125050-1c9405e8b208?q=80&w=2671&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        true,
        1,
        '2024-05-13'
       );
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Koncert Uniwersytecki: Harmonie Dźwięków',
        'Z radością zapraszamy do udziału w naszym Koncercie Uniwersyteckim, który odbędzie się na naszym kampusie. To wyjątkowe wydarzenie ma na celu połączenie talentów naszych studentów i wspólnoty wokół muzyki, oferując niezapomniane przeżycia muzyczne dla wszystkich uczestników.',
        'University Concert: Harmony of Sounds',
        'With joy, we invite you to participate in our University Concert, which will take place on our campus. This special event aims to unite the talents of our students and the community around music, offering unforgettable musical experiences for all participants.',
        'Университетский концерт: Гармония звуков',
        'С радостью приглашаем вас принять участие в нашем университетском концерте, который состоится на нашем кампусе. Это особенное событие направлено на объединение талантов наших студентов и сообщества вокруг музыки, предлагая незабываемые музыкальные впечатления для всех участников.',
        'Університетський концерт: Гармонія звуків',
        'З радістю запрошуємо вас взяти участь у нашому університетському концерті, який відбудеться на нашому кампусі. Ця особлива подія спрямована на об`єднання талантів наших студентів і спільноти навколо музики, пропонуючи незабутні музичні враження для всіх учасників.',
        false,
        false,
        1,
        'https://images.unsplash.com/photo-1524368535928-5b5e00ddc76b?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        true,
        1,
        '2023-01-14'
       );

-- future events
-- fully booked, near future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Dni Kultury i Sztuki Uniwersytetu: Współorganizacja i wsparcie.',
        'Uniwersytet organizuje coroczne Dni Kultury i Sztuki, podczas których miasto i kampus główny stają się areną różnorodnych wydarzeń artystycznych i kulturalnych. Poszukujemy osób chętnych do współorganizacji i wsparcia w realizacji występów muzycznych, pokazów tanecznych, wystaw artystycznych oraz warsztatów kreatywnych. Wydarzenie integruje społeczność akademicką i lokalną, promując różnorodność oraz kreatywność. Wymagania: Dostępność czasowa, Znajomość języków obcych.',
        'University Culture and Arts Days: Co-organization and Support.',
        'The university organizes annual Culture and Arts Days, during which the city and main campus become arenas for diverse artistic and cultural events. We are looking for individuals interested in co-organizing and supporting the realization of musical performances, dance shows, art exhibitions, and creative workshops. The event integrates the academic and local community, promoting diversity and creativity.Requirements: Availability during the event dates, proficiency in foreign languages.',
        'Дни культуры и искусства университета: Совместная организация и поддержка.',
        'Университет организует ежегодные Дни культуры и искусства, в рамках которых город и основной кампус становятся ареной разнообразных художественных и культурных мероприятий. Мы ищем людей, заинтересованных в совместной организации и поддержке проведения музыкальных выступлений, танцевальных шоу, художественных выставок и творческих мастерских. Мероприятие объединяет академическое и местное сообщество, способствуя продвижению разнообразия и креативности.Требования: Доступность в период проведения мероприятия, знание иностранных языков.',
        'Дні культури і мистецтва університету: Співорганізація та підтримка.',
        'Університет організовує щорічні Дні культури і мистецтва, під час яких місто та основний кампус стають ареною різноманітних художніх та культурних подій. Ми шукаємо людей, які зацікавлені у співорганізації та підтримці проведення музичних вистав, танцювальних шоу, художніх виставок та творчих майстерень. Подія об`єднує академічну та місцеву спільноту, сприяючи розвитку різноманітності та креативності.Вимоги: Доступність у час проведення події, знання іноземних мов.',
        true,
        false,
        1,
        'https://images.unsplash.com/photo-1677427300033-0f6df9df62a4?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8Y3VsdHVyYWwlMjBmZXN0aXZhbHxlbnwwfHwwfHx8Mg%3D%3D',
        true,
        1,
        '2024-07-05'
       );
-- far future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Kampania edukacyjna dla dzieci',
        'Poszukujemy wolontariuszy do udziału w kampanii edukacyjnej skierowanej do dzieci, obejmującej interaktywne warsztaty promujące zdrowie, higienę osobistą oraz edukację ekologiczną. Kampania ma na celu angażowanie uczniów szkół podstawowych i przedszkoli poprzez praktyczne zajęcia edukacyjne.Wymagania: Dostępność, Zaangażowanie.',
        'Educational Campaign for Children',
        'We are looking for volunteers to participate in an educational campaign aimed at children, including interactive workshops promoting health, personal hygiene, and environmental education. The campaign aims to engage elementary school and preschool students through hands-on educational activities. Requirements: Availability, Commitment.',
        'Образовательная кампания для детей',
        'Мы ищем волонтеров для участия в образовательной кампании, направленной на детей, включающей интерактивные мастер-классы по продвижению здоровья, личной гигиены и экологическому образованию. Цель кампании - привлечение учащихся начальных школ и детских садов через практические образовательные занятия. Требования: Доступность, Участие.',
        'Освітня кампанія для дітей',
        'Ми шукаємо волонтерів для участі в освітній кампанії, спрямованій на дітей, включаючи інтерактивні майстер-класи з просування здоров`я, особистої гігієни та екологічної освіти. Мета кампанії - залучення учнів початкових шкіл і дошкільних закладів через практичні освітні заняття. Вимоги: Доступність, Заангажованість.',
        false,
        false,
        2,
        'https://images.unsplash.com/photo-1588072432836-e10032774350?q=80&w=2672&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        true,
        2,
        '2024-09-13'
       );

--near future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Dzień Zdrowia i Bezpieczeństwa',
        'Organizacja Dnia Zdrowia i Bezpieczeństwa, podczas którego odbędą się warsztaty i prezentacje dotyczące pierwszej pomocy, bezpieczeństwa w domu i w pracy, oraz promowania zdrowego stylu życia. Wydarzenie ma na celu edukację lokalnej społeczności w zakresie ochrony życia i zdrowia. Wymagania: Znajomość podstaw pierwszej pomocy.',
        'Day of Health and Safety',
        'Organizing a Day of Health and Safety featuring workshops and presentations on first aid, home and workplace safety, and promoting a healthy lifestyle. The event aims to educate the local community on life and health protection. Requirements: Knowledge of basic first aid.',
        'День здоровья и безопасности',
        'Организация Дня здоровья и безопасности, в рамках которого пройдут мастер-классы и презентации по первой помощи, безопасности на дому и на работе, а также по продвижению здорового образа жизни. Мероприятие направлено на образование местного сообщества в области защиты жизни и здоровья. Требования: Знание основ первой помощи.',
        'День здоров`я і безпеки',
        'Організація Дня здоров`я і безпеки, під час якого відбудуться майстер-класи та презентації з першої допомоги, безпеки вдома і на роботі, а також просування здорового способу життя. Мета події - освіта місцевої громади в галузі охорони життя і здоров`я. Вимоги: Знання основ першої допомоги.',
        true,
        false,
        3,
        'https://images.unsplash.com/photo-1630964046403-8b745c1e3c69?q=80&w=2620&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        true,
        2,
        '2024-07-06'
       );
--near future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Warsztaty Wsparcia dla Rodziców i Opiekunów',
        'Poszukujemy wolontariuszy do współorganizacji i prowadzenia cyklu warsztatów dotyczących zdrowia psychicznego rodziców, technik radzenia sobie ze stresem oraz efektywnych metod wychowawczych. Warsztaty mają na celu wspieranie rodziców i opiekunów w codziennych wyzwaniach wychowawczych oraz poprawę ich samopoczucia psychicznego. Wymagania: Doświadczenie w pracy z rodzinami, dziećmi oraz preferowane wykształcenie w dziedzinie psychologii.',
        'Workshops for Supporting Parents and Caregivers',
        'We are looking for volunteers to co-organize and lead a series of workshops focused on the mental health of parents, stress management techniques, and effective parenting methods. The workshops aim to support parents and caregivers in daily parenting challenges and improve their mental well-being. Requirements: Experience working with families and children, and preferably a background in psychology.',
        'Мастер-классы по поддержке родителей и опекунов',
        'Мы ищем волонтеров для соорганизации и проведения серии мастер-классов, посвященных психическому здоровью родителей, техникам управления стрессом и эффективным методам воспитания. Цель мастер-классов - поддержка родителей и опекунов в повседневных воспитательных задачах и улучшение их психического самочувствия. Требования: опыт работы с семьями и детьми, желательно образование в области психологии.',
        'Майстер-класи підтримки для батьків та опікунів',
        'Ми шукаємо волонтерів для співорганізації та проведення серії майстер-класів, присвячених психічному здоров`я батьків, технікам управління стресом та ефективним методам виховання. Майстер-класи спрямовані на підтримку батьків та опікунів у щоденних виховних викликах та поліпшення їхнього психічного самопочуття. Вимоги: досвід роботи з сім`ями та дітьми, бажано освіта в галузі психології.',
        false,
        true,
        2,
        'https://images.unsplash.com/photo-1622610607501-32ac9c927216?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8ZmFtaWx5JTIwc3VwcG9ydHxlbnwwfHwwfHx8Mg%3D%3D',
        true,
        2,
        '2024-07-07'
       );
-- far future, fully booked
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('Konferencja Technologii Przyszłości',
        'Poszukujemy wolontariuszy do współorganizacji i prowadzenia warsztatów technologicznych dla młodzieży. Celem warsztatów jest zainteresowanie młodych ludzi nowoczesnymi technologiami, programowaniem oraz projektowaniem graficznym. Uczestnicy będą mieli okazję nauczyć się podstaw kodowania, tworzenia aplikacji oraz grafiki komputerowej, co pomoże im w rozwijaniu pasji i umiejętności w dziedzinie IT.',
        'Future Technology Conference',
        'We are looking for volunteers to co-organize and lead technology workshops for young people. The goal of the workshops is to interest young people in modern technologies, programming, and graphic design. Participants will have the opportunity to learn the basics of coding, application development, and computer graphics, which will help them develop their passion and skills in the field of IT.',
        'Конференция технологий будущего',
        'Мы ищем волонтеров для соорганизации и проведения технологических мастер-классов для молодежи. Цель мастер-классов - заинтересовать молодых людей современными технологиями, программированием и графическим дизайном. Участники смогут изучить основы кодирования, разработки приложений и компьютерной графики, что поможет им развить свои увлечения и навыки в области ИТ.',
        'Конференція технологій майбутнього',
        'Ми шукаємо волонтерів для співорганізації та проведення технологічних майстер-класів для молоді. Метою майстер-класів є зацікавлення молодих людей сучасними технологіями, програмуванням та графічним дизайном. Учасники матимуть можливість вивчити основи кодування, розробки додатків та комп`ютерної графіки, що допоможе їм розвинути свої захоплення та навички у сфері ІТ.',
        true,
        true,
        1,
        'https://images.unsplash.com/photo-1560439514-4e9645039924?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        true,
        1,
        '2024-09-09'
       );

INSERT INTO category_to_event (category_id, event_id) VALUES
                                                          (1, 1),(2, 1),(1, 2),(3, 2),(1, 3),(3, 3),(5, 4),(3, 4),(5, 5),(3, 5),(5, 6),(3, 7),(1, 7);