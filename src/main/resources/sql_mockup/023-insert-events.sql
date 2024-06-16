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
        1,
        'https://images.unsplash.com/photo-1523473125050-1c9405e8b208?q=80&w=2671&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        true,
        1,
        '2024-05-13'
       );
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        false,
        false,
        1,
        '',
        true,
        1,
        '2023-01-14'
       );

-- future events
-- fully booked, near future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        true,
        false,
        1,
        '',
        true,
        1,
        '2024-07-05'
       );
-- far future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        false,
        false,
        2,
        '',
        true,
        2,
        '2024-09-13'
       );

--near future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        true,
        false,
        3,
        '',
        true,
        2,
        '2024-07-06'
       );
--near future
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        false,
        true,
        2,
        '',
        true,
        2,
        '2024-07-07'
       );
-- far future, fully booked
INSERT INTO event (name_pl,description_pl,name_en,description_en,name_ru,description_ru,name_ua,description_ua,is_pesel_ver_req,is_agreement_needed,organisation_id,image_url,is_approved,city_id,date)
VALUES ('',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        true,
        true,
        1,
        '',
        true,
        1,
        '2024-09-09'
       );


INSERT INTO category_to_event (category_id, event_id) VALUES
                                                          (5, 1),(5, 2),(5, 3),(5, 4),(5, 5),(5, 6),(5, 7);