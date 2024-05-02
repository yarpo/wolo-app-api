--liquibase formatted sql

--changeset woloapp:014

-- translated directions
UPDATE shift SET
                 directions_en = 'Manhattan Gdańsk Shopping Center',
                 directions_ru = 'Торговый центр Manhattan в Гданьске',
                 directions_ua = 'Торговий центр Manhattan у Гданську'
WHERE id IN (1, 45, 67);

UPDATE shift SET
                 directions_en = 'Gdańsk Social Cooperative, at doorbell 11',
                 directions_ru = 'Гданьская социальная кооперативная, на домофоне 11',
                 directions_ua = 'Гданська соціальна кооперативна, на домофоні 11'
WHERE id = 2;

UPDATE shift SET
                 directions_en = 'During the school year from 12:00 to 17:00',
                 directions_ru = 'Во время учебного года с 12:00 до 17:00',
                 directions_ua = 'Під час навчального року з 12:00 до 17:00'
WHERE id IN (3, 5, 48, 46, 68, 70);

UPDATE shift SET
                 directions_en = 'Near the veterinary clinic entrance',
                 directions_ru = 'Рядом с входом в ветеринарную клинику',
                 directions_ua = 'Поруч з входом до ветеринарної клініки'
WHERE id IN (7, 50, 72);

UPDATE shift SET
                 directions_en = 'Near the parish building of the Mother of God of Częstochowa',
                 directions_ru = 'Рядом с зданием прихода Матери Божьей Ченстоховской',
                 directions_ua = 'Поблизу будівлі парафії Матері Божої Ченстоховської'
WHERE id IN (52, 74, 9);

UPDATE shift SET
                 directions_en = 'Near the green gate entrance',
                 directions_ru = 'Рядом с входом через зеленые ворота',
                 directions_ua = 'Поруч з входом через зелені ворота'
WHERE id IN (14, 57, 79);

UPDATE shift SET
                 directions_en = 'On the second floor, Call at 6',
                 directions_ru = 'На втором этаже, Звонить по 6',
                 directions_ua = 'На другому поверсі, Дзвонити за 6'
WHERE id IN (16, 20, 59, 63, 81, 85);

UPDATE shift SET
                 directions_en = 'Opposite School-Educational Center No. 2',
                 directions_ru = 'Напротив Школьно-Воспитательного Центра № 2',
                 directions_ua = 'Напроти Шкільно-Виховного Центру № 2'
WHERE id IN (17, 60, 82);

UPDATE shift SET
                 directions_en = 'Regional Sanitary and Epidemiological Station in Gdańsk',
                 directions_ru = 'Региональная санитарно-эпидемиологическая станция в Гданьске',
                 directions_ua = 'Регіональна санітарно-епідеміологічна станція в Гданську'
WHERE id IN (18, 61, 83);

UPDATE shift SET
                 directions_en = 'Willa Morskie Oko',
                 directions_ru = 'Дом Морское Око',
                 directions_ua = 'Будинок Морське Око'
WHERE id IN (19, 62, 84);

UPDATE shift SET
                 directions_en = 'Pizzeria Papryczka',
                 directions_ru = 'Пиццерия Папричка',
                 directions_ua = 'Піцерія Папричка'
WHERE id IN (21, 64, 86);

UPDATE shift SET
                 directions_en = 'Car Workshop',
                 directions_ru = 'Автомастерская',
                 directions_ua = 'Автомайстерня'
WHERE id IN (22, 65, 87);

UPDATE shift SET
                 directions_en = 'Near the GAUM Gdańsk Academy of Managerial Skills',
                 directions_ru = 'Вблизи ГАУМ Гданьская Академия Управленческих Навыков',
                 directions_ua = 'Поблизу ГАУМ Гданська Академія Управлінських Навичок'
WHERE id IN (23, 66, 88);

UPDATE shift SET
                 directions_en = 'Center for Information and Ecological Education',
                 directions_ru = 'Центр Информации и Экологического Образования',
                 directions_ua = 'Центр Інформації та Екологічної Освіти'
WHERE id = 24;

UPDATE shift SET
                 directions_en = 'Municipal Transport Authority. Customer service point',
                 directions_ru = 'Управление Муниципального Транспорта. Пункт обслуживания клиентов',
                 directions_ua = 'Управління Муніципального Транспорту. Пункт обслуговування клієнтів'
WHERE id = 25;

UPDATE shift SET
                 directions_en = 'Bambini 3. Montessori Kindergarten',
                 directions_ru = 'Bambini 3. Детский сад Монтессори',
                 directions_ua = 'Bambini 3. Дитячий садок Монтессорі'
WHERE id = 26;

UPDATE shift SET
                 directions_en = 'Scientific Department (WiMBP Branch)',
                 directions_ru = 'Научный отдел (Филиал WiMBP)',
                 directions_ua = 'Науковий відділ (Філія WiMBP)'
WHERE id = 27;

UPDATE shift SET
                 directions_en = 'Kindergarten No. 62 "Fairy Tale Land"',
                 directions_ru = 'Детский сад № 62 "Страна Сказок"',
                 directions_ua = 'Дитячий садок № 62 "Країна Казок"'
WHERE id = 28;

UPDATE shift SET
                 directions_en = 'GDDKiA Branch in Gdańsk',
                 directions_ru = 'Филиал GDDKiA в Гданьске',
                 directions_ua = 'Філія GDDKiA в Гданську'
WHERE id = 29;

UPDATE shift SET
                 directions_en = 'Oliwa Park Estate',
                 directions_ru = 'Оливский Парк',
                 directions_ua = 'Олівський Парк'
WHERE id = 30;

UPDATE shift SET
                directions_en = 'Academy "I will come up with something"',
                directions_ru = 'Академия "Я что-то придумаю"',
                directions_ua = 'Академія "Я щось придумаю"'
WHERE id = 31;

UPDATE shift SET
                directions_en = 'Next to the bike rental',
                directions_ru = 'Рядом с прокатом велосипедов',
                directions_ua = 'Поруч з прокатом велосипедів'
WHERE id = 32;

UPDATE shift SET
                directions_en = 'Bike Rental "Bike Rental Gdańsk"',
                directions_ru = 'Прокат велосипедов "Bike Rental Gdańsk"',
                directions_ua = 'Прокат велосипедів "Bike Rental Gdańsk"'
WHERE id = 33;

UPDATE shift SET
                directions_en = 'In the renovated tenement house TBS Motława on the 3rd floor',
                directions_ru = 'В отреставрированном доме TBS Мотлава на 3 этаже',
                directions_ua = 'У відновленому будинку TBS Мотлава на 3 поверсі'
WHERE id = 34;

UPDATE shift SET
                directions_en = 'Polish Post Office',
                directions_ru = 'Почта Польши',
                directions_ua = 'Пошта Польщі'
WHERE id = 35;

UPDATE shift SET
                directions_en = 'Shopping Center "Galeria Przymorze"',
                directions_ru = 'Торговый центр "Галерея Пжыморже"',
                directions_ua = 'Торговий центр "Галерея Пжиморже"'
WHERE id = 36;

UPDATE shift SET
                 directions_en = 'Babie Lato Library (WiMBP Branch)',
                 directions_ru = 'Библиотека Бабье Лето (Филиал WiMBP)',
                 directions_ua = 'Бібліотека Бабине Літо (Філія WiMBP)'
WHERE id = 37;

UPDATE shift SET
                 directions_en = 'Oliwa Cathedral',
                 directions_ru = 'Оливский собор',
                 directions_ua = 'Олівський собор'
WHERE id = 38;

UPDATE shift SET
                 directions_en = 'Medical Laboratories Bruss Alab Group Sp. z o.o.',
                 directions_ru = 'Медицинские лаборатории Bruss Alab Group Sp. z o.o.',
                 directions_ua = 'Медичні лабораторії Bruss Alab Group Sp. z o.o.'
WHERE id = 39;

UPDATE shift SET
                 directions_en = 'Medicus Nursing and Care Services Agency',
                 directions_ru = 'Медикус Агентство медицинских и уходовых услуг',
                 directions_ua = 'Медікус Агентство медичних та доглядових послуг'
WHERE id = 40;

UPDATE shift SET
                 directions_en = 'Parish of Divine Providence in Gdańsk',
                 directions_ru = 'Парафия Божьего Провидения в Гданьске',
                 directions_ua = 'Парафія Божого Провидіння в Гданську'
WHERE id = 41;

UPDATE shift SET
                 directions_en = 'Near the Rzeczypospolitej 02 bus stop',
                 directions_ru = 'Вблизи автобусной остановки Речепосполита 02',
                 directions_ua = 'Поблизу автобусної зупинки Речепосполіта 02'
WHERE id = 42;

UPDATE shift SET
                 directions_en = 'QuestRooms, next to the ORLEN charging station',
                 directions_ru = 'QuestRooms, рядом с зарядной станцией ORLEN',
                 directions_ua = 'QuestRooms, поруч з зарядною станцією ORLEN'
WHERE id = 43;

UPDATE shift SET
                 directions_en = 'Opposite the optical salon',
                 directions_ru = 'Напротив оптического салона',
                 directions_ua = 'Напроти оптичного салону'
WHERE id = 44;