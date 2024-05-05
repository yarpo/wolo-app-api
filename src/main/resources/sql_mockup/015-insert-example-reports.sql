--liquibase formatted sql

--changeset woloapp:015

INSERT INTO report (report_pl, report_en, report_ua, report_ru, published, event_id) VALUES
                                                                                         ('Jak dotąd, dzięki zaangażowaniu ponad 30 osób, udało nam się pomóc ponad 1000 potrzebującym.
                                                                                          W przyszłości planujemy zorganizować więcej zmian dla większej liczby wolontariuszy. Dziękujemy za pomoc w tworzeniu lepszego świata!',
                                                                                          'So far with over 30 people, we managed to help over 1000 people in need.
                                                                                         In the future, we plan to organize more shifts to engage a larger number of volunteers. Thanks for helping us make the world a better place!',
                                                                                          'До теперішнього моменту, завдяки залученню понад 30 осіб, нам вдалося допомогти понад 1000 людям, які потребують.
                                                                                         У майбутньому ми плануємо організувати більше змін для більшої кількості добровольців. Дякуємо за допомогу у створенні кращого світу!',
                                                                                          'До настоящего момента, благодаря участию более 30 человек, нам удалось помочь более 1000 нуждающимся.
                                                                                         В будущем мы планируем организовать больше смен для большего числа волонтеров. Спасибо за помощь в создании лучшего мира!',
                                                                                          FALSE, 20),
                                                                                         ('W minionym tygodniu zaangażowaliśmy ponad 25 wolontariuszy, co pozwoliło nam wspólnymi siłami zorganizować 5 wydarzeń charytatywnych.
                                                                                          Dziękujemy za wsparcie naszej inicjatywy!',
                                                                                          'Last week, we engaged over 25 volunteers, which allowed us to organize 5 charity events together.
                                                                                         Thank you for supporting our initiative!',
                                                                                          'На минулому тижні ми залучили понад 25 волонтерів, що дозволило нам разом організувати 5 благодійних заходів.
                                                                                         Дякуємо за підтримку нашої ініціативи!',
                                                                                          'На прошлой неделе мы привлекли более 25 волонтеров, что позволило нам совместно организовать 5 благотворительных мероприятий.
                                                                                         Спасибо за поддержку нашей инициативы!',
                                                                                          FALSE, 16),
                                                                                         ('W minionym miesiącu udało nam się zebrać potrzebną kwotę na wsparcie osób niepełnosprawnych.
                                                                                          Dziękujemy za wsparcie i dołączenie do naszej akcji!',
                                                                                          'Last month, we managed to raise the necessary funds to support people with disabilities.
                                                                                         Thank you for your support and joining our campaign!',
                                                                                          'У минулому місяці нам вдалося зібрати необхідну суму для підтримки людей з інвалідністю.
                                                                                         Дякуємо за підтримку та приєднання до нашої акції!',
                                                                                          'В прошлом месяце нам удалось собрать необходимую сумму на поддержку людей с ограниченными возможностями.
                                                                                         Спасибо за поддержку и присоединение к нашей кампании!',
                                                                                          TRUE, 17);