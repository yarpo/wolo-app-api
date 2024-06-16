--liquibase formatted sql

--changeset woloapp:023
-- final mockup data

INSERT INTO report (report_pl, report_en, report_ua, report_ru, published, event_id) VALUES
                                                                                         ('Z wielką radością pragniemy podziękować wszystkim za udział w naszym kiermaszu dobroczynności "Tworzymy Nadzieję". Dzięki Waszemu zaangażowaniu udało się stworzyć niezapomniane wydarzenie, które przyniosło nadzieję i radość wielu osobom.',
                                                                                          'We are delighted to thank everyone for participating in our charity fair "Creating Hope". Thanks to your involvement, we managed to create an unforgettable event that brought hope and joy to many.',
                                                                                          'З великою радістю хочемо подякувати всім за участь у нашому благодійному ярмарку ''Створюємо Надію''. Завдяки вашому залученню нам вдалося створити незабутню подію, яка принесла надію і радість багатьом людям.',
                                                                                          'С большой радостью хотим поблагодарить всех за участие в нашей благотворительной ярмарке "Создаем надежду". Благодаря вашему участию нам удалось создать незабываемое событие, которое принесло надежду и радость многим людям.',
                                                                                          TRUE, 1),
                                                                                         ('Koncert uważamy za udane wydarzenie, wszystko poszło jak zakładaliśmy, dziękujemy wszystkim wolontariuszom',
                                                                                          'We consider the concert a successful event, everything went as we planned, thank you to all the volunteers',
                                                                                          'Концерт вважаємо вдалим заходом, все пішло як ми планували, дякуємо всім волонтерам',
                                                                                          'Концерт считаем удачным мероприятием, всё прошло как мы планировали, благодарим всех волонтеров',
                                                                                          FALSE, 2)
                                                                                         ;