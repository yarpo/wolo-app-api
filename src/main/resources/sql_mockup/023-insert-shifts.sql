--liquibase formatted sql

--changeset woloapp:023
-- final mockup data

-- past targ drzewny 8 1
INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('07:30:00', '9:30:00',2,2,false,16,'Obok Uniwersytetu "PJATK"','Next to the "PJATK" University','Рядом с Университетом "PJATK"','Поруч з Університетом "PJATK"',4,1);
-- brzegi 55 2
INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('08:00:00', '11:00:00',2,1,false,12,'Niedaleko ośrodka szkolenia kierowców "ARTOM"','Near the "ARTOM" driving training center','Недалеко от центра обучения водителей "ARTOM"','Неподалік від навчального центру водіїв "ARTOM"',5,2);
-- future 3
INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('08:30:00', '9:30:00',1,1,false,12,'W budynku Opery Bałtyckiej','In the building of the Baltic Opera','В здании Балтийской оперы','У будівлі Балтійської опери',6,3);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('10:00:00', '15:30:00',1,1,false,18,'W budynku Kliniki Rehabilitacji UCK','In the building of the Rehabilitation Clinic UCK','В здании Клиники реабилитации УЦК','У будівлі Клініки реабілітації УЦК',7,3);
-- 4
INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('14:25:00','15:30:00',3,2,false,16,'Na przeciwko Muzeum Powstania Warszawskiego',' On Opposite side to the Warsaw Uprising Museum','Напротив Музея Варшавского восстания','Напроти Музею Варшавського повстання',8,4);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('21:30:00', '23:30:00',1,0,false,21,'Obok Liceum Ogólnokształcącego','Next to the Comprehensive High School','Рядом с Общеобразовательной школой','Поруч з Загальноосвітньою школою',9,4);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('16:30:00', '19:30:00',1,1,false,18,'Niedaleko Galerii Autonomii','Near the Gallery of Autonomy','Недалеко от Галереи Автономии','Поблизу Галереї Автономії',10,4);
-- 5
INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('9:30:00', '10:30:00',2,0,false,12,'Przy sklepie "Auchan"','Next to the "Auchan" store','У магазині "Auchan"','Біля магазину "Auchan"',11,5);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('11:30:00','12:30:00',3,0,false,12,'Przy automacie DHL BOX','Next to the DHL BOX vending machine','У автоматі DHL BOX','Біля автомата DHL BOX',12,5);
-- 6
INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('07:30:00', '9:30:00',3,1,false,16,'W budynku państwowej Szkoły Muzycznej','In the building of the State Music School','В здании государственной Музыкальной школы','У будівлі державної Музичної школи',13,6);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('10:30:00', '14:30:00',1,1,false,16,'Niedaleko Teatru "Collegium Nobilium"','Near the "Collegium Nobilium" Theater','Недалеко от театра "Collegium Nobilium"','Поблизу театру "Collegium Nobilium"',14,6);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('07:30:00', '9:30:00',3,0,false,16,'W Pałacu Morsztynów','In the Morsztyn Palace','В палаце Морштинов','У палаці Морштинів',15,6);
-- 7

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('08:30:00','10:30:00',3,0,false,12,'Wejście od strony ulicy Brzozowej','Entrance from Brzozowa Street','Вход с улицы Бжозовой','Вхід з вулиці Бжозової',16,7);

INSERT INTO shift (start_time,end_time,capacity,registered,is_leader_required,required_min_age,directions_pl,directions_en,directions_ru,directions_ua,address_id,event_id)
VALUES ('11:45:00','12:45:00',1,1,false,12,'Obok sklepu spożywczego','Next to the grocery store','Рядом с продуктовым магазином','Поруч з продуктовим магазином',17,7);

-- insert shift_to_user
INSERT INTO shift_to_user ( user_id, shift_id, is_on_reserve_list, is_leader) VALUES
                                                                                  ( 3, 1, false, false),
                                                                                  ( 5, 1, false, false),
                                                                                  ( 7, 2, false, false),
                                                                                  ( 7, 3, false, false),
                                                                                  ( 6, 4, false, false),
                                                                                  ( 7, 5, false, false),
                                                                                  ( 2, 5, false, false),
                                                                                  ( 3, 7, false, false),
                                                                                  ( 7, 10, false, false),
                                                                                  ( 5, 11, false, false),
                                                                                  ( 3, 11, true, false),
                                                                                  ( 3, 14, true ,false),
                                                                                  ( 7, 14, true, false);

