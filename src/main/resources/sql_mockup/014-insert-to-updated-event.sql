--liquibase formatted sql

--changeset woloapp:014

UPDATE event
SET date = '2024-05-26'
WHERE id BETWEEN 1 AND 12;

UPDATE event
SET date = '2025-05-01'
WHERE id BETWEEN 13 AND 15;

UPDATE event
SET date = '2023-01-14'
WHERE id BETWEEN 16 AND 18;

UPDATE event
SET date = '2026-11-27'
WHERE id BETWEEN 19 AND 23;

UPDATE event
SET date = '2024-07-27'
WHERE id BETWEEN 24 AND 30;

UPDATE event
SET date = '2024-09-15'
WHERE id BETWEEN 31 AND 36;

UPDATE event
SET date = '2024-10-05'
WHERE id BETWEEN 37 AND 40;

UPDATE event
SET date = '2024-05-15'
WHERE id BETWEEN 41 AND 44;

-- insert alt for images

UPDATE event
SET image_alt = 'A woman talking to a man sitting on the ground'
WHERE id = 1;

UPDATE event
SET image_alt = 'A woman raising her hands with tents in the background'
WHERE id = 2;

UPDATE event
SET image_alt = 'An old man with a backpack'
WHERE id = 3;

UPDATE event
SET image_alt = 'colorful tents'
WHERE id = 4;

UPDATE event
SET image_alt = 'A row hangers with clothes'
WHERE id = 5;

UPDATE event
SET image_alt = 'two people putting trash into a bag'
WHERE id = 6;

UPDATE event
SET image_alt = 'A person planting green plants'
WHERE id = 7;

UPDATE event
SET image_alt = 'buckets full of trash'
WHERE id = 8;

UPDATE event
SET image_alt = 'A picture of two windmills with mountains in the background'
WHERE id = 9;

UPDATE event
SET image_alt = 'A group of people holding each other in the middle of the field'
WHERE id = 10;

UPDATE event
SET image_alt = 'A picture of two puppies in a cage'
WHERE id = 11;

UPDATE event
SET image_alt = 'A picture of monkey in a cage'
WHERE id = 12;

UPDATE event
SET image_alt = 'A picture of a puppy laying down on a blanket'
WHERE id = 13;

UPDATE event
SET image_alt = 'A classroom full of students'
WHERE id = 14;

UPDATE event
SET image_alt = 'A dog standing in water'
WHERE id = 15;

UPDATE event
SET image_alt = 'A family decorating the christmass tree'
WHERE id = 16;

UPDATE event
SET image_alt = 'A group of people with laptops talking with each other'
WHERE id = 17;

UPDATE event
SET image_alt = 'A group of people sitting next to a fireplace'
WHERE id = 18;

UPDATE event
SET image_alt = 'Two children sitting with one guitar'
WHERE id = 19;

UPDATE event
SET image_alt = 'A picture of hand holding smaller hand'
WHERE id = 20;

UPDATE event
SET image_alt = 'Two people sitting next to eachother looking at the trees'
WHERE id = 21;

UPDATE event
SET image_alt = 'An elderly woman washing vegetables'
WHERE id = 22;

UPDATE event
SET image_alt = 'Two elderly people playing chess'
WHERE id = 23;

UPDATE event
SET image_alt = 'An elderly woman reading a book'
WHERE id = 24;

UPDATE event
SET image_alt = 'Two people looking at the sea'
WHERE id = 25;

UPDATE event
SET image_alt = 'A child giving thumbs up in the ball pit'
WHERE id = 26;

UPDATE event
SET image_alt = 'A group of people exercising'
WHERE id = 27;

UPDATE event
SET image_alt = 'A woman holding a green ribbon'
WHERE id = 28;

UPDATE event
SET image_alt = 'A hand holding books'
WHERE id = 29;

UPDATE event
SET image_alt = 'A child touching a book'
WHERE id = 30;

UPDATE event
SET image_alt = 'A pile of plastic bottles'
WHERE id = 31;

UPDATE event
SET image_alt = 'A man sitting on the ground with a paper cup next to him'
WHERE id = 32;

UPDATE event
SET image_alt = 'Two women sitting on a bench smiling'
WHERE id = 33;

UPDATE event
SET image_alt = 'Children playing with alphabet blocks'
WHERE id = 34;

UPDATE event
SET image_alt = 'A picture of hands with painted heart on them'
WHERE id = 35;

UPDATE event
SET image_alt = 'Person holding a phone'
WHERE id = 36;

UPDATE event
SET image_alt = 'Two hands holding a black heart'
WHERE id = 37;

UPDATE event
SET image_alt = 'Group of people taking notes'
WHERE id = 38;

UPDATE event
SET image_alt = 'Three people holding umbrellas'
WHERE id = 39;

UPDATE event
SET image_alt = 'Three smiling people sitting on the ground with colorful papers'
WHERE id = 40;

UPDATE event
SET image_alt = 'A man looking at the phone'
WHERE id = 41;

UPDATE event
SET image_alt = 'Two women speaking to each other'
WHERE id = 42;

UPDATE event
SET image_alt = 'A picture of the phone on a cardboard box'
WHERE id = 43;

UPDATE event
SET image_alt = 'A picture of a family in a field of poppies'
WHERE id = 44;

-- insert translations to name for other languages

UPDATE event SET
                 name_en = 'Good Day in City Streets',
                 name_ru = 'Хороший день на улицах города',
                 name_ua = 'Гарний день на вулицях міста'
WHERE id = 1;

UPDATE event SET
                 name_en = 'Music for Homeless People: Charity Benefit',
                 name_ru = 'Музыка для бездомных: благотворительный вечер',
                 name_ua = 'Музика для бездомних: благодійний вечір'
WHERE id = 2;

UPDATE event SET
                 name_en = 'Professional Development: Path to a New Beginning',
                 name_ru = 'Профессиональное развитие: путь к новому началу',
                 name_ua = 'Професійний розвиток: шлях до нового початку'
WHERE id = 3;

UPDATE event SET name_en = 'Warm Night: Night Shifts with Care Foundation',
                 name_ru = 'Теплая ночь: ночные дежурства с фондом заботы',
                 name_ua = 'Тепла ніч: нічні чергування з фондом турботи'
WHERE id = 4;

UPDATE event SET name_en = 'Charity Fair "Care": Creating Hope',
                 name_ru = 'Благотворительная ярмарка "Забота": создание надежды',
                 name_ua = 'Благодійний ярмарок "Турбота": створення надії'
WHERE id = 5;

UPDATE event SET
                 name_en = 'Clean Beaches: Cleaning the Coast with Us!',
                 name_ru = 'Чистые пляжи: уборка побережья с нами!',
                 name_ua = 'Чисті пляжі: прибирання узбережжя з нами!'
WHERE id = 6;

UPDATE event SET
                 name_en = 'Tree Planting: Do Something Good for Nature',
                 name_ru = 'Посадка деревьев: сделайте что-то хорошее для природы',
                 name_ua = 'Посадка дерев: зробіть щось хороше для природи'
WHERE id = 7;

UPDATE event SET name_en = 'Recycling and Waste Management Training',
                 name_ru = 'Обучение по переработке и управлению отходами',
                 name_ua = 'Навчання з переробки та управління відходами'
WHERE id = 8;

UPDATE event SET name_en = 'Ecological Excursion to the National Park',
                 name_ru = 'Экологическая экскурсия в национальный парк',
                 name_ua = 'Екологічна екскурсія до національного парку'
WHERE id = 9;

UPDATE event SET
                 name_en = 'Youth Activation for Sustainable Development',
                 name_ru = 'Активизация молодежи для устойчивого развития',
                 name_ua = 'Активізація молоді заради сталого розвитку'
WHERE id = 10;

UPDATE event SET
                 name_en = 'Cage Farm Animal Rescue Operation',
                 name_ru = 'Операция по спасению животных с ферм клеточного содержания',
                 name_ua = 'Операція з порятунку тварин з ферм клітинного утримання'
WHERE id = 11;

UPDATE event SET
                 name_en = 'Campaign Against Cage Farming',
                 name_ru = 'Кампания против клеточного содержания',
                 name_ua = 'Кампанія проти клітинного утримання'
WHERE id = 12;

UPDATE event SET
                 name_en = 'Visit to the Animal Shelter',
                 name_ru = 'Посещение приюта для животных',
                 name_ua = 'Відвідування притулку для тварин'
WHERE id = 13;

UPDATE event SET
                 name_en = 'Education for Animal Rights',
                 name_ru = 'Образование в области прав животных',
                 name_ua = 'Освіта в галузі прав тварин'
WHERE id = 14;

UPDATE event SET
                 name_en = 'Disaster Animal Rescue Operation',
                 name_ru = 'Операция по спасению животных в случае катастроф',
                 name_ua = 'Операція з порятунку тварин у разі катастроф'
WHERE id = 15;

UPDATE event SET
                 name_en = 'Holidays for Children in Need',
                 name_ru = 'Праздники для нуждающихся детей',
                 name_ua = 'Свята для дітей, що потребують'
WHERE id = 16;

UPDATE event SET
                 name_en = 'Collaborative Work on Youth Future',
                 name_ru = 'Совместная работа над будущим молодежи',
                 name_ua = 'Спільна робота над майбутнім молоді'
WHERE id = 17;

UPDATE event SET
                 name_en = 'Summer Adventure with Children in Hardship',
                 name_ru = 'Летнее приключение с детьми в трудных условиях',
                 name_ua = 'Літня пригода з дітьми в складних умовах'
WHERE id = 18;

UPDATE event SET
                 name_en = 'Educational Support for Young Talents',
                 name_ru = 'Образовательная поддержка молодых талантов',
                 name_ua = 'Освітня підтримка молодих талантів'
WHERE id = 19;

UPDATE event SET
                 name_en = 'Supporting Families in Difficult Positions',
                 name_ru = 'Поддержка семей в трудном положении',
                 name_ua = 'Підтримка сімей у складних умовах'
WHERE id = 20;

UPDATE event SET
                 name_en = 'Active Seniors - Recreational Activities',
                 name_ru = 'Активные пенсионеры - развлекательные мероприятия',
                 name_ua = 'Активні пенсіонери - розважальні заходи'
WHERE id = 21;

UPDATE event SET
                 name_en = 'Supporting Seniors in Daily Duties',
                 name_ru = 'Поддержка пенсионеров в повседневных обязанностях',
                 name_ua = 'Підтримка пенсіонерів у щоденних обов язках'
WHERE id = 22;

UPDATE event SET
                 name_en = 'Cultural and Creative Workshops for Seniors',
                 name_ru = 'Культурные и творческие мастерские для пенсионеров',
                 name_ua = 'Культурні та творчі майстерні для пенсіонерів'
WHERE id = 23;

UPDATE event SET
                 name_en = 'Reading Club for Seniors',
                 name_ru = 'Читательский клуб для пенсионеров',
                 name_ua = 'Читацький клуб для пенсіонерів'
             WHERE id = 24;

UPDATE event SET
                 name_en = 'Golden Years - Trips and Cruises',
                 name_ru = 'Золотые годы - поездки и круизы',
                 name_ua = 'Золоті роки - подорожі та круїзи'
WHERE id = 25;

UPDATE event SET
                 name_en = 'Dream Day for Disabled Persons',
                 name_ru = 'День мечты для людей с ограниченными возможностями',
                 name_ua = 'День мрій для людей з обмеженими можливостями'
WHERE id = 26;

UPDATE event SET
                 name_en = 'Support in Therapy and Rehabilitation',
                 name_ru = 'Поддержка в терапии и реабилитации',
                 name_ua = 'Підтримка в терапії та реабілітації'
WHERE id = 27;

UPDATE event SET
                 name_en = 'Supportive Meeting: Together for a Good Cause',
                 name_ru = 'Поддерживающая встреча: вместе ради хорошего дела',
                 name_ua = 'Підтримуюча зустріч: разом заради доброї справи'
WHERE id = 28;

UPDATE event SET
                 name_en = 'Cultural Club for Disabled Persons',
                 name_ru = 'Культурный клуб для людей с ограниченными возможностями',
                 name_ua = 'Культурний клуб для людей з обмеженими можливостями'
WHERE id = 29;

UPDATE event SET
                 name_en = 'Support for Families of Disabled Persons',
                 name_ru = 'Поддержка семей людей с ограниченными возможностями',
                 name_ua = 'Підтримка сімей людей з обмеженими можливостями'
WHERE id = 30;

UPDATE event SET
                 name_en = 'Park Cleaning - Together for Cleanliness',
                 name_ru = 'Уборка парка - вместе за чистоту',
                 name_ua = 'Прибирання парку - разом за чистоту'
WHERE id = 31;

UPDATE event SET
                 name_en = 'Help for the Homeless',
                 name_ru = 'Помощь бездомным',
                 name_ua = 'Допомога бездомним'
WHERE id = 32;

UPDATE event SET
                 name_en = 'Senior Club - Creating Community',
                 name_ru = 'Клуб пенсионеров - создание сообщества',
                 name_ua = 'Клуб пенсіонерів - створення спільноти'
WHERE id = 33;

UPDATE event SET
                 name_en = 'Help for Children in Need - Shared Care',
                 name_ru = 'Помощь детям в нужде - общая забота',
                 name_ua = 'Допомога дітям, що потребують - спільна турбота'
WHERE id = 34;

UPDATE event SET
                 name_en = 'Social Activation - Supporting Together',
                 name_ru = 'Социальная активация - поддержка вместе',
                 name_ua = 'Соціальна активація - підтримка разом'
WHERE id = 35;

UPDATE event SET
                 name_en = 'Support Line for People in Crisis',
                 name_ru = 'Линия поддержки для людей в кризисе',
                 name_ua = 'Лінія підтримки для людей в кризі'
WHERE id = 36;

UPDATE event SET
                 name_en = 'Group Support - Overcoming Crisis Together',
                 name_ru = 'Групповая поддержка - преодоление кризиса вместе',
                 name_ua = 'Групова підтримка - подолання кризи разом'
WHERE id = 37;

UPDATE event SET
                 name_en = 'Self-Help and Personal Development Workshops',
                 name_ru = 'Мастерские самопомощи и личностного развития',
                 name_ua = 'Майстерні самодопомоги та особистісного розвитку'
WHERE id = 38;

UPDATE event SET
                 name_en = 'Support for Families in Crisis',
                 name_ru = 'Поддержка семей в кризисе',
                 name_ua = 'Підтримка сімей у кризі'
WHERE id = 39;

UPDATE event SET
                 name_en = 'Open Days - Get to Know Our Work',
                 name_ru = 'Дни открытых дверей - познакомьтесь с нашей работой',
                 name_ua = 'Дні відкритих дверей - познайомтеся з нашою роботою'
WHERE id = 40;

UPDATE event SET
                 name_en = 'Support in Anti-Addiction Activities',
                 name_ru = 'Поддержка в антинаркотической деятельности',
                 name_ua = 'Підтримка в антинаркотичній діяльності'
WHERE id = 41;

UPDATE event SET
                 name_en = 'Therapeutic Workshops - Overcoming Addictions Together',
                 name_ru = 'Терапевтические мастерские - преодоление зависимостей вместе',
                 name_ua = 'Терапевтичні майстерні - подолання залежностей разом'
WHERE id = 42;

UPDATE event SET
                 name_en = 'Anti-Addiction Campaign - Education and Awareness',
                 name_ru = 'Кампания против наркозависимости - образование и осведомленность',
                 name_ua = 'Кампанія проти наркозалежності - освіта та обізнаність'
WHERE id = 43;

UPDATE event SET
                 name_en = 'Supporting Families - Connecting and Helping',
                 name_ru = 'Поддержка семей - соединение и помощь',
                 name_ua = 'Підтримка сімей - з єднання та допомога'
WHERE id = 44;

-- insert translations to description for other languages

UPDATE event SET description_en = 'Would you like to join us to make the streets of our city a better place? Together we can help homeless people by offering warm meals, clothing, and psychological support. Your goodwill is welcome!',
                 description_ru = 'Хотели бы вы присоединиться к нам, чтобы сделать улицы нашего города лучше? Вместе мы можем помочь бездомным, предлагая теплые блюда, одежду и психологическую поддержку. Ваша добрая воля приветствуется!',
                 description_ua = 'Чи хотіли б ви приєднатися до нас, щоб зробити вулиці нашого міста кращим місцем? Разом ми можемо допомогти бездомним, пропонуючи теплі страви, одяг та психологічну підтримку. Ваша добра воля вітається!'
WHERE id = 1;

UPDATE event SET description_en = 'Join us as a volunteer during our charity concert where music meets helping those in need. Your help in organizing this unique event will improve the lives of homeless people.',
                 description_ru = 'Присоединяйтесь к нам в качестве волонтера во время нашего благотворительного концерта, где музыка сочетается с помощью нуждающимся. Ваша помощь в организации этого уникального мероприятия улучшит жизнь бездомных.',
                 description_ua = 'Приєднуйтесь до нас як волонтер під час нашого благодійного концерту, де музика поєднується з допомогою тим, хто цього потребує. Ваша допомога в організації цієї унікальної події покращить життя бездомних.'
WHERE id = 2;

UPDATE event SET description_en = 'On cold nights, we need your help as a volunteer. Join us during night shifts to offer warm meals and shelter to the homeless.',
                 description_ru = 'В холодные ночи нам нужна ваша помощь в качестве волонтера. Присоединяйтесь к нам во время ночных смен, чтобы предложить теплую еду и укрытие бездомным.',
                 description_ua = 'У холодні ночі нам потрібна ваша допомога як волонтера. Долучайтеся до нас під час нічних змін, щоб пропонувати теплі страви та притулок бездомним.'
WHERE id IN (3, 4);

UPDATE event SET description_en = 'If you would like to help organize a charity fair, join us on this joint mission! Your presence creates hope and joy for others.',
                 description_ru = 'Если вы хотели бы помочь организовать благотворительную ярмарку, присоединяйтесь к нам в этой совместной миссии! Ваше присутствие создает надежду и радость для других.',
                 description_ua = 'Якщо ви хочете допомогти організувати благодійний ярмарок, приєднуйтесь до нас у цій спільній місії! Ваша присутність створює надію та радість для інших.'
WHERE id = 5;

UPDATE event SET description_en = 'Join us in a beach cleaning action and help protect our beautiful beaches! Regardless of experience, everyone is welcome. Together we can do a lot for our nature. Join us today!',
                 description_ru = 'Присоединяйтесь к нам в акции по уборке пляжей и помогите защитить наши красивые пляжи! Независимо от опыта, все приветствуются. Вместе мы можем многое сделать для нашей природы. Присоединяйтесь к нам уже сегодня!',
                 description_ua = 'Долучайтеся до нас у акції з прибирання пляжів та допоможіть захистити наші красиві пляжі! Незалежно від досвіду, всі вітаються. Разом ми можемо багато зробити для нашої природи. Приєднуйтесь до нас сьогодні!'
WHERE id = 6;

UPDATE event SET description_en = 'Join us to plant trees together and care for our planet. No previous experience in tree planting is required, all willing participants are welcome! Together we create a better future for the environment.',
                 description_ru = 'Присоединяйтесь к нам, чтобы вместе сажать деревья и заботиться о нашей планете. Предыдущий опыт посадки деревьев не требуется, все желающие участники приветствуются! Вместе мы создаем лучшее будущее для окружающей среды.',
                 description_ua = 'Приєднуйтесь до нас, щоб разом саджати дерева та дбати про нашу планету. Попередній досвід посадки дерев не потрібен, всі бажаючі учасники вітаються! Разом ми створюємо краще майбутнє для довкілля.'
WHERE id = 7;

UPDATE event SET description_en = 'We are looking for volunteers who would like to help us conduct training on recycling and waste management. Your help will enable others to understand the importance of proper waste management for sustainable development.',
                 description_ru = 'Мы ищем волонтеров, которые хотели бы помочь нам провести обучение по переработке и управлению отходами. Ваша помощь позволит другим понять важность правильного управления отходами для устойчивого развития.',
                 description_ua = 'Ми шукаємо волонтерів, які хотіли б допомогти нам провести навчання з переробки та управління відходами. Ваша допомога дозволить іншим зрозуміти важливість правильного управління відходами для сталого розвитку.'
WHERE id = 8;

UPDATE event SET description_en = 'We are looking for guides and assistants to organize an ecological excursion to the National Park. This is a great opportunity to share your knowledge about nature and support its protection through education and awareness.',
                 description_ru = 'Мы ищем гидов и помощников для организации экологической экскурсии в Национальный парк. Это отличная возможность поделиться вашими знаниями о природе и поддержать ее защиту через образование и осведомленность.',
                 description_ua = 'Ми шукаємо гідів та помічників для організації екологічної екскурсії до Національного парку. Це чудова можливість поділитися вашими знаннями про природу та підтримати її захист через освіту та обізнаність.'
WHERE id = 9;

UPDATE event SET description_en = 'We invite you to participate in our educational project aimed at activating youth in actions for sustainable development. Together we will teach young people about environmental protection and inspire them to take action for our planet. Join us to create a better future!',
                 description_ru = 'Приглашаем вас принять участие в нашем образовательном проекте, направленном на активизацию молодежи в действиях по устойчивому развитию. Вместе мы будем учить молодежь охране окружающей среды и вдохновлять их на действия ради нашей планеты. Присоединяйтесь к нам, чтобы создать лучшее будущее!',
                 description_ua = 'Запрошуємо вас взяти участь у нашому освітньому проекті, спрямованому на активізацію молоді у діях за сталий розвиток. Разом ми навчатимемо молодих людей захисту навколишнього середовища та надихатимемо їх на дії заради нашої планети. Приєднуйтесь до нас, щоб створити краще майбутнє!'
WHERE id = 10;

UPDATE event SET description_en = 'We are looking for dedicated volunteers who would like to help us in rescuing animals from cage farms. Your help can change the lives of hundreds of animals suffering in these conditions.',
                 description_ru = 'Мы ищем преданных волонтеров, которые хотели бы помочь нам в спасении животных с ферм клеточного содержания. Ваша помощь может изменить жизнь сотен животных, страдающих в этих условиях.',
                 description_ua = 'Ми шукаємо відданих волонтерів, які хотіли б допомогти нам у рятуванні тварин з клітинних ферм. Ваша допомога може змінити життя сотень тварин, які страждають у цих умовах.'
WHERE id = 11;

UPDATE event SET description_en = 'We are looking for volunteers who would like to engage in lobbying, collecting signatures, and promoting awareness about this social issue. Together we can contribute to positive changes for farm animals.',
                 description_ru = 'Мы ищем волонтеров, которые хотели бы заниматься лоббированием, сбором подписей и повышением осведомленности о этой социальной проблеме. Вместе мы можем способствовать положительным изменениям для фермерских животных.',
                 description_ua = 'Ми шукаємо волонтерів, які хотіли б займатися лобіюванням, збором підписів та підвищенням обізнаності щодо цієї соціальної проблеми. Разом ми можемо сприяти позитивним змінам для сільськогосподарських тварин.'
WHERE id = 12;

UPDATE event SET description_en = 'We warmly invite you to participate in our volunteer work at the animal shelter. If you love animals and want to help them through care and adoption assistance, this opportunity is for you!',
                 description_ru = 'Мы сердечно приглашаем вас принять участие в нашей волонтерской деятельности в приюте для животных. Если вы любите животных и хотите помочь им через уход и помощь в адопции, эта возможность для вас!',
                 description_ua = 'Ми сердечно запрошуємо вас взяти участь у нашій волонтерській діяльності в притулку для тварин. Якщо ви любите тварин і хочете допомогти їм через догляд та допомогу в адопції, ця можливість для вас!'
WHERE id = 13;

UPDATE event SET description_en = 'Looking for volunteers to assist with lectures, workshops, and educational campaigns. Your work will help spread the idea of respect and care for animals in society.',
                 description_ru = 'Ищем волонтеров для помощи на лекциях, мастер-классах и образовательных кампаниях. Ваша работа поможет распространить идею уважения и заботы о животных в обществе.',
                 description_ua = 'Шукаємо волонтерів для допомоги на лекціях, майстер-класах та освітніх кампаніях. Ваша робота допоможе поширити ідею поваги та турботи про тварин у суспільстві.'
WHERE id = 14;

UPDATE event SET description_en = 'As part of our disaster animal aid program, we are looking for volunteers ready to act in emergency situations. We participate in rescue operations, helping animals in difficult situations like fires or floods. If you have the skills and willingness to help in extreme conditions, join our rescue team. Your work can save many animals’ lives.',
                 description_ru = 'В рамках нашей программы помощи животным при катастрофах мы ищем волонтеров, готовых действовать в чрезвычайных ситуациях. Мы участвуем в спасательных операциях, помогая животным в сложных ситуациях, таких как пожары или наводнения. Если у вас есть навыки и желание помогать в экстремальных условиях, присоединяйтесь к нашей спасательной команде. Ваша работа может спасти множество жизней животных.',
                 description_ua = 'У рамках нашої програми допомоги тваринам під час катастроф ми шукаємо волонтерів, готових діяти в екстрених ситуаціях. Ми беремо участь у рятувальних операціях, допомагаючи тваринам у складних ситуаціях, таких як пожежі або повені. Якщо у вас є навички та бажання допомагати в екстремальних умовах, приєднуйтесь до нашої рятувальної команди. Ваша робота може врятувати багато життів тварин.'
WHERE id = 15;


UPDATE event SET description_en = 'We warmly invite warm-hearted volunteers to join our family of Good Angels during the upcoming Christmas holidays. We are preparing special holidays for children who find themselves in difficult life situations. Together, we can make these days full of warmth, love, and smiles.',
                 description_ru = 'Мы тепло приглашаем сердечных волонтеров присоединиться к нашей семье Ангелов Добра в преддверии Рождественских праздников. Мы готовим особенные праздники для детей, оказавшихся в сложных жизненных ситуациях. Вместе мы можем сделать эти дни полными тепла, любви и улыбок.',
                 description_ua = 'Ми сердечно запрошуємо теплих волонтерів приєднатися до нашої родини Добрих Ангелів під час наближених Різдвяних свят. Ми готуємо особливі свята для дітей, які опинилися в складних життєвих ситуаціях. Разом ми можемо зробити ці дні повними тепла, любові та усмішок.'
WHERE id = 16;

UPDATE event SET description_en = 'Our mission is to support youth in overcoming life challenges and building their future. We are looking for passionate individuals who would like to be mentors and friends to young people in need. Your presence and support can help shape their dreams and perspectives.',
                 description_ru = 'Наша миссия заключается в поддержке молодежи в преодолении жизненных трудностей и построении их будущего. Мы ищем страстных людей, которые хотели бы быть наставниками и друзьями для молодых людей в нужде. Ваше присутствие и поддержка могут помочь сформировать их мечты и взгляды.',
                 description_ua = 'Наша місія полягає у підтримці молоді у подоланні життєвих викликів та будівництві їхнього майбутнього. Ми шукаємо пристрасних осіб, які хотіли б стати наставниками та друзями для молодих людей, що потребують. Ваша присутність і підтримка можуть допомогти формувати їхні мрії та перспективи.'
WHERE id = 17;

UPDATE event SET description_en = 'We are preparing a summer camp for children who have not had the opportunity for holiday adventures. We are looking for volunteers who would like to spend time at the camp, organizing activities, acting as guides, and creating unforgettable memories. Your presence can make these summer days an unforgettable experience.',
                 description_ru = 'Мы готовим летний лагерь для детей, которым не хватало возможности для праздничных приключений. Мы ищем волонтеров, которые хотели бы провести время в лагере, организовывая мероприятия, выступая в качестве гидов и создавая незабываемые воспоминания. Ваше присутствие может сделать эти летние дни незабываемым опытом.',
                 description_ua = 'Ми готуємо літній табір для дітей, яким не вистачало можливості для святкових пригод. Ми шукаємо волонтерів, які хотіли б провести час у таборі, організовуючи заходи, виступаючи як гіди та створюючи незабутні спогади. Ваша присутність може зробити ці літні дні незабутнім досвідом.'
WHERE id = 18;

UPDATE event SET description_en = 'Our organization is engaged in providing educational support for children and youth in difficult situations. We are looking for volunteers who can help with teaching, tutoring, and developing students skills. Your help can be key to their educational success.',
                 description_ru = 'Наша организация занимается предоставлением образовательной поддержки детям и молодежи в сложных ситуациях. Мы ищем волонтеров, которые могут помочь в обучении, репетиторстве и развитии навыков учащихся. Ваша помощь может быть ключом к их образовательному успеху.',
                 description_ua = 'Наша організація займається наданням освітньої підтримки дітям та молоді в складних ситуаціях. Ми шукаємо волонтерів, які можуть допомогти з навчанням, репетиторством та розвитком навичок учнів. Ваша допомога може бути ключем до їхнього освітнього успіху.'
WHERE id = 19;

UPDATE event SET description_en = 'We are looking for volunteers who would like to help activate families with children in difficult life situations. You can provide psychological support, organize workshops, and help solve everyday problems. Your work can be the spark that helps these families find a better way forward.',
                 description_ru = 'Мы ищем волонтеров, которые хотели бы помочь активизировать семьи с детьми в сложных жизненных ситуациях. Вы можете предоставлять психологическую поддержку, организовывать мастер-классы и помогать решать повседневные проблемы. Ваша работа может стать искрой, которая поможет этим семьям найти лучший путь вперед.',
                 description_ua = 'Ми шукаємо волонтерів, які хотіли б допомогти активізувати сім ї з дітьми у складних життєвих ситуаціях. Ви можете надавати психологічну підтримку, організовувати майстерні та допомагати вирішувати повсякденні проблеми. Ваша робота може стати іскрою, яка допоможе цим сім ям знайти кращий шлях вперед.'
WHERE id = 20;

UPDATE event SET description_en = 'We warmly invite you to join our program "Active Seniors", where our valued seniors can enjoy recreational and social activities. We are looking for kind volunteers who would like to help organize activities, board games, walks, and conversations. Your presence will make these "Golden Years" even more beautiful.',
                 description_ru = 'Мы тепло приглашаем вас присоединиться к нашей программе "Активные пенсионеры", где наши ценные пенсионеры могут наслаждаться развлекательными и социальными мероприятиями. Мы ищем добрых волонтеров, которые хотели бы помочь организовать мероприятия, настольные игры, прогулки и беседы. Ваше присутствие сделает эти "Золотые года" еще более красивыми.',
                 description_ua = 'Ми сердечно запрошуємо вас приєднатися до нашої програми "Активні Пенсіонери", де наші цінні пенсіонери можуть насолоджуватися рекреаційними та соціальними заходами. Ми шукаємо добрих волонтерів, які хотіли б допомогти організувати заходи, настільні ігри, прогулянки та розмови. Ваша присутність зробить ці "Золоті Роки" ще красивішими.'
WHERE id = 21;

UPDATE event SET description_en = 'Our organization is seeking warm-hearted volunteers who can assist our seniors with daily duties, such as grocery shopping, doctor visits, or household chores. Your help will mean a lot to our elders and allow them to enjoy their independence.',
                 description_ru = 'Наша организация ищет сердечных волонтеров, которые могут помочь нашим пенсионерам в повседневных обязанностях, таких как покупка продуктов, посещение врача или домашние дела. Ваша помощь будет иметь большое значение для наших старших и позволит им наслаждаться их независимостью.',
                 description_ua = 'Наша організація шукає сердечних волонтерів, які могли б допомогти нашим пенсіонерам у повсякденних обов язках, таких як покупка продуктів, візит до лікаря або побутові справи. Ваша допомога матиме велике значення для наших старших та дозволить їм насолоджуватися своєю незалежністю.'
WHERE id = 22;

UPDATE event SET description_en = 'Join us for our cultural and creative workshops for seniors. We are looking for volunteers who would like to share their skills and passion with our seniors. Together we can discover new talents and foster creativity among our "Golden Years" community.',
                 description_ru = 'Присоединяйтесь к нам на наших культурных и творческих мастер-классах для пенсионеров. Мы ищем волонтеров, которые хотели бы поделиться своими навыками и страстью с нашими пенсионерами. Вместе мы можем открыть новые таланты и способствовать креативности среди нашего сообщества "Золотых лет".',
                 description_ua = 'Приєднуйтесь до нас на наших культурних та творчих майстернях для пенсіонерів. Ми шукаємо волонтерів, які хотіли б поділитися своїми навичками та пристрастю з нашими пенсіонерами. Разом ми можемо відкрити нові таланти та сприяти креативності серед нашої спільноти "Золотих Років".'
WHERE id = 23;

UPDATE event SET description_en = 'Our reading club for seniors is a place where our older friends can share their love of books and literature. We are looking for volunteers who would like to lead discussions, organize author meetings, and provide support with reading. Your passion for literature can become an inspiration for our seniors.',
                 description_ru = 'Наш читательский клуб для пенсионеров — это место, где наши старшие друзья могут делиться своей любовью к книгам и литературе. Мы ищем волонтеров, которые хотели бы вести обсуждения, организовывать встречи с авторами и помогать в чтении. Ваша страсть к литературе может стать вдохновением для наших пенсионеров.',
                 description_ua = 'Наш читацький клуб для пенсіонерів — це місце, де наші старші друзі можуть ділитися своєю любов ю до книг та літератури. Ми шукаємо волонтерів, які хотіли б вести дискусії, організовувати зустрічі з авторами та надавати підтримку у читанні. Ваша пристрасть до літератури може стати натхненням для наших пенсіонерів.'
WHERE id = 24;

UPDATE event SET description_en = 'Our "Golden Years" deserve unique adventures and memories. We are looking for volunteers who would like to help organize trips and cruises for our seniors. Your participation will allow them to discover new places and enjoy life to the fullest, even in their later years.',
                 description_ru = 'Наши "Золотые года" заслуживают уникальных приключений и воспоминаний. Мы ищем волонтеров, которые хотели бы помочь организовать поездки и круизы для наших пенсионеров. Ваше участие позволит им открывать новые места и наслаждаться жизнью в полной мере, даже в поздние годы.',
                 description_ua = 'Наші "Золоті Роки" заслуговують на унікальні пригоди та спогади. Ми шукаємо волонтерів, які хотіли б допомогти організувати поїздки та круїзи для наших пенсіонерів. Ваша участь дозволить їм відкривати нові місця та насолоджуватися життям на повну, навіть у пізніші роки.'
WHERE id = 25;

UPDATE event SET description_en = 'Join us for "Dream Day" where we aim to fulfill the dreams of disabled persons. We seek dreamers and organizers to help make these special moments come true. Your involvement can turn their dreams into reality.',
                 description_ru = 'Присоединяйтесь к нам на "День Мечты", где мы стремимся исполнить мечты людей с ограниченными возможностями. Мы ищем мечтателей и организаторов, чтобы помочь осуществить эти особенные моменты. Ваше участие может превратить их мечты в реальность.',
                 description_ua = 'Приєднуйтесь до нас на "День Мрій", де ми прагнемо втілити мрії людей з обмеженими можливостями. Ми шукаємо мрійників та організаторів, щоб допомогти зробити ці особливі моменти реальністю. Ваша участь може перетворити їхні мрії на дійсність.'
WHERE id = 26;

UPDATE event SET description_en = 'We offer therapy and rehabilitation for disabled persons, helping to improve their quality of life. We are looking for volunteers who would like to assist during therapy sessions, provide emotional support, and help with rehabilitation. Your presence can be key to their success and independence.',
                 description_ru = 'Мы предлагаем терапию и реабилитацию для людей с ограниченными возможностями, помогая улучшить их качество жизни. Мы ищем волонтеров, которые хотели бы помочь во время терапевтических сеансов, предоставить эмоциональную поддержку и помочь с реабилитацией. Ваше присутствие может быть ключом к их успеху и независимости.',
                 description_ua = 'Ми пропонуємо терапію та реабілітацію для людей з обмеженими можливостями, допомагаючи покращити їхню якість життя. Ми шукаємо волонтерів, які хотіли б допомогти під час терапевтичних сесій, надати емоційну підтримку та допомогти з реабілітацією. Ваша присутність може бути ключем до їхнього успіху та незалежності.'
WHERE id = 27;

UPDATE event SET description_en = 'Join our event where we support people with disabilities, helping them in their therapy and rehabilitation processes. We seek volunteers ready to provide emotional support and assist during therapeutic sessions. Your participation can have a significant impact on improving their quality of life. See you at the event!',
                 description_ru = 'Присоединяйтесь к нашему мероприятию, где мы поддерживаем людей с ограниченными возможностями, помогая им в процессах терапии и реабилитации. Мы ищем волонтеров, готовых предоставить эмоциональную поддержку и помочь во время терапевтических сеансов. Ваше участие может оказать значительное влияние на улучшение их качества жизни. Увидимся на мероприятии!',
                 description_ua = 'Приєднуйтесь до нашого заходу, де ми підтримуємо людей з обмеженими можливостями, допомагаючи їм у процесах терапії та реабілітації. Ми шукаємо волонтерів, готових надати емоційну підтримку та допомогти під час терапевтичних сесій. Ваша участь може мати значний вплив на покращення їхньої якості життя. Бачимося на заході!'
WHERE id = 28;

UPDATE event SET description_en = 'Our Cultural Club for Disabled Persons is a place where people with disabilities can enjoy art and culture. We are looking for volunteers who would like to organize cultural events, artistic workshops, and support our members in developing their passions. Your creativity can brighten the lives of our participants.',
                 description_ru = 'Наш культурный клуб для людей с ограниченными возможностями - это место, где люди с инвалидностью могут наслаждаться искусством и культурой. Мы ищем волонтеров, которые хотели бы организовывать культурные мероприятия, художественные мастерские и поддерживать наших участников в развитии их страстей. Ваше творчество может осветить жизни наших участников.',
                 description_ua = 'Наш Культурний клуб для осіб з інвалідністю - це місце, де люди з обмеженими можливостями можуть насолоджуватися мистецтвом та культурою. Ми шукаємо волонтерів, які хотіли б організовувати культурні заходи, мистецькі майстерні та підтримувати наших членів у розвитку їхніх захоплень. Ваша креативність може освітлити життя наших учасників.'
WHERE id = 29;

UPDATE event SET description_en = 'Our foundation also acts to support families of disabled persons. We are seeking volunteers who would like to offer emotional and practical support to families in need. Your presence can help them cope with the challenges of daily life.',
                 description_ru = 'Наш фонд также действует для поддержки семей людей с ограниченными возможностями. Мы ищем волонтеров, которые хотели бы предложить эмоциональную и практическую поддержку нуждающимся семьям. Ваше присутствие может помочь им справиться с повседневными жизненными вызовами.',
                 description_ua = 'Наш фонд також діє з метою підтримки сімей осіб з інвалідністю. Ми шукаємо волонтерів, які хотіли б запропонувати емоційну та практичну підтримку сім ям, що цього потребують. Ваша присутність може допомогти їм впоратися з викликами повсякденного життя.'
WHERE id = 30;

UPDATE event SET description_en = 'We are seeking active volunteers to participate in a park cleaning action. Together, we can make our local community cleaner and more welcoming. Join us and help us give back to a more beautiful environment!',
                 description_ru = 'Мы ищем активных волонтеров для участия в акции по уборке парка. Вместе мы можем сделать наше местное сообщество чище и более приветливым. Присоединяйтесь к нам и помогите нам вернуть красоту нашей окружающей среде!',
                 description_ua = 'Ми шукаємо активних волонтерів для участі в акції з прибирання парку. Разом ми можемо зробити нашу місцеву громаду чистішою та більш гостинною. Приєднуйтесь до нас і допоможіть нам повернути красу нашому довкіллю!'
WHERE id = 31;

UPDATE event SET description_en = 'We are organizing a support action for homeless residents in our area. We are looking for volunteers who would like to provide meals, clothing, and engage in conversations with those in need. Your help can make a significant difference to those who need it most.',
                 description_ru = 'Мы организуем акцию поддержки для бездомных жителей нашего района. Мы ищем волонтеров, которые хотели бы предоставить еду, одежду и вступить в разговоры с нуждающимися. Ваша помощь может существенно изменить жизнь тех, кто в ней больше всего нуждается.',
                 description_ua = 'Ми організовуємо акцію підтримки для бездомних мешканців нашого району. Ми шукаємо волонтерів, які хотіли б забезпечити їжу, одяг та вступити в розмови з тими, хто цього потребує. Ваша допомога може значно змінити життя тих, хто цього найбільше потребує.'
WHERE id = 32;

UPDATE event SET description_en = 'We are creating a Senior Club where our older neighbors can spend time together and share their stories. We are looking for volunteers who would like to help organize meetings, social events, and take care of our older friends. Your presence can create a warm and friendly community.',
                 description_ru = 'Мы создаем Клуб пенсионеров, где наши старшие соседи могут проводить время вместе и делиться своими историями. Мы ищем волонтеров, которые хотели бы помочь организовать встречи, социальные мероприятия и заботиться о наших старших друзьях. Ваше присутствие может создать теплое и дружелюбное сообщество.',
                 description_ua = 'Ми створюємо Клуб пенсіонерів, де наші старші сусіди можуть проводити час разом та ділитися своїми історіями. Ми шукаємо волонтерів, які хотіли б допомогти організувати зустрічі, соціальні заходи та піклуватися про наших старших друзів. Ваша присутність може створити теплу та дружню спільноту.'
WHERE id = 33;

UPDATE event SET description_en = 'Our charity action focuses on helping children in difficult life situations. We are looking for volunteers who would like to collect school supplies, toys, and clothing for children. Your care can make the lives of young residents in our area better.',
                 description_ru = 'Наша благотворительная акция направлена на помощь детям в сложных жизненных ситуациях. Мы ищем волонтеров, которые хотели бы собирать школьные принадлежности, игрушки и одежду для детей. Ваша забота может сделать жизнь молодых жителей нашего района лучше.',
                 description_ua = 'Наша благодійна акція зосереджена на допомозі дітям у складних життєвих ситуаціях. Ми шукаємо волонтерів, які хотіли б збирати шкільне приладдя, іграшки та одяг для дітей. Ваша турбота може зробити життя молодих мешканців нашої місцевості кращим.'
WHERE id = 34;

UPDATE event SET description_en = 'Our club promotes active engagement in the community. We are looking for volunteers who would like to organize community events, educational workshops, and charitable actions. Together, we can make a difference in the local community and create positive change. Join us to give back and support each other!',
                 description_ru = 'Наш клуб способствует активному участию в жизни сообщества. Мы ищем волонтеров, которые хотели бы организовывать общественные мероприятия, образовательные семинары и благотворительные акции. Вместе мы можем изменить местное сообщество и создать положительные изменения. Присоединяйтесь к нам, чтобы помочь и поддержать друг друга!',
                 description_ua = 'Наш клуб сприяє активній участі у громадському житті. Ми шукаємо волонтерів, які хотіли б організовувати громадські заходи, освітні майстерні та благодійні акції. Разом ми можемо змінити місцеву громаду та створити позитивні зміни. Приєднуйтесь до нас, щоб допомагати та підтримувати один одного!'
WHERE id = 35;

UPDATE event SET description_en = 'We are looking for volunteers for our support line for people in emotional crisis. If you have empathy and are a good listener, join us to help those who need psychological support. Your support can be the key to improving others lives.',
                 description_ru = 'Мы ищем волонтеров для нашей линии поддержки людей в эмоциональном кризисе. Если вы обладаете эмпатией и умеете хорошо слушать, присоединяйтесь к нам, чтобы помочь тем, кто нуждается в психологической поддержке. Ваша поддержка может стать ключом к улучшению жизни других.',
                 description_ua = 'Ми шукаємо волонтерів для нашої лінії підтримки людей в емоційній кризі. Якщо ви емпатичні та вмієте добре слухати, приєднуйтесь до нас, щоб допомогти тим, хто потребує психологічної підтримки. Ваша підтримка може бути ключем до покращення життя інших.'
WHERE id = 36;

UPDATE event SET description_en = 'Our foundation organizes group support sessions for people struggling with emotional issues. We are looking for volunteers who would like to lead these sessions, provide emotional support, and help build healthy relationships. Your skills can help overcome difficulties.',
                 description_ru = 'Наш фонд организует групповые сессии поддержки для людей, сталкивающихся с эмоциональными проблемами. Мы ищем волонтеров, которые хотели бы вести эти сессии, предоставлять эмоциональную поддержку и помогать строить здоровые отношения. Ваши навыки могут помочь преодолеть трудности.',
                 description_ua = 'Наш фонд організує групові сесії підтримки для людей, які зіткнулися з емоційними проблемами. Ми шукаємо волонтерів, які хотіли б вести ці сесії, надавати емоційну підтримку та допомагати будувати здорові стосунки. Ваші навички можуть допомогти подолати труднощі.'
WHERE id = 37;

UPDATE event SET description_en = 'Join our self-help and personal development workshops. These sessions are a chance for people in crisis to develop their skills and find internal balance. We are looking for volunteers who would like to lead these workshops and share their knowledge with others. Your help can be the key to their growth and development.',
                 description_ru = 'Присоединяйтесь к нашим мастер-классам по самопомощи и личностному развитию. Эти сессии являются шансом для людей в кризисе развить свои навыки и найти внутреннее равновесие. Мы ищем волонтеров, которые хотели бы вести эти мастер-классы и делиться своими знаниями с другими. Ваша помощь может стать ключом к их росту и развитию.',
                 description_ua = 'Приєднуйтесь до наших майстерень з самодопомоги та особистісного розвитку. Ці сесії є шансом для людей у кризі розвинути свої навички та знайти внутрішню рівновагу. Ми шукаємо волонтерів, які хотіли б вести ці майстерні та ділитися своїми знаннями з іншими. Ваша допомога може бути ключем до їхнього росту та розвитку.'
WHERE id = 38;

UPDATE event SET description_en = 'Our foundation offers psychological support for families affected by emotional crises. We are looking for volunteers who would like to assist therapists during therapy sessions, provide support to parents and children, and help build healthy family relationships. Your presence can bring relief and hope to these families.',
                 description_ru = 'Наш фонд предлагает психологическую поддержку семьям, пострадавшим от эмоциональных кризисов. Мы ищем волонтеров, которые хотели бы помочь терапевтам во время терапевтических сеансов, предоставлять поддержку родителям и детям, помогать строить здоровые семейные отношения. Ваше присутствие может принести облегчение и надежду этим семьям.',
                 description_ua = 'Наш фонд надає психологічну підтримку сім ям, які постраждали від емоційних криз. Ми шукаємо волонтерів, які хотіли б допомогти терапевтам під час терапевтичних сесій, надавати підтримку батькам та дітям, допомагати будувати здорові сімейні відносини. Ваша присутність може принести полегшення та надію цим сім ям.'
WHERE id = 39;

UPDATE event SET description_en = 'We would like to invite volunteers to participate in our Open Days, where they can learn about our work and mission. We are looking for people who can help with event organization, provide information, and create an open and friendly environment. Your presence can help promote mental health in the community.',
                 description_ru = 'Мы хотели бы пригласить волонтеров принять участие в наших Днях открытых дверей, где они могут узнать о нашей работе и миссии. Мы ищем людей, которые могут помочь с организацией мероприятий, предоставлением информации и созданием открытой и дружелюбной атмосферы. Ваше присутствие может помочь продвигать психическое здоровье в сообществе.',
                 description_ua = 'Ми хотіли б запросити волонтерів взяти участь у наших Днях відкритих дверей, де вони можуть дізнатися про нашу роботу та місію. Ми шукаємо людей, які можуть допомогти з організацією заходів, наданням інформації та створенням відкритого та дружнього середовища. Ваша присутність може допомогти просувати психічне здоров я у спільноті.'
WHERE id = 40;

UPDATE event SET description_en = 'We are seeking dedicated volunteers who would like to join our anti-addiction efforts. Together, we can conduct preventive, educational, and therapeutic activities to help people struggling with various addictions to substances and behaviors. Join us to fight for a healthy and addiction-free community.',
                 description_ru = 'Мы ищем преданных волонтеров, которые хотели бы присоединиться к нашим усилиям по борьбе с зависимостями. Вместе мы можем проводить профилактические, образовательные и терапевтические мероприятия, чтобы помочь людям, борющимся с различными зависимостями от веществ и поведения. Присоединяйтесь к нам, чтобы бороться за здоровое и свободное от зависимостей сообщество.',
                 description_ua = 'Ми шукаємо відданих волонтерів, які хотіли б приєднатися до наших зусиль у боротьбі з залежностями. Разом ми можемо проводити профілактичні, освітні та терапевтичні заходи, щоб допомогти людям, які борються з різними залежностями від речовин та поведінки. Приєднуйтесь до нас, щоб боротися за здорову та вільну від залежностей спільноту.'
WHERE id = 41;

UPDATE event SET description_en = 'Our organization organizes therapeutic workshops for people struggling with various types of addictions, such as computer or alcohol addiction. We are looking for volunteers who would like to lead these workshops, provide emotional support, and assist in the recovery process. Your involvement can be key to helping participants regain control of their lives.',
                 description_ru = 'Наша организация проводит терапевтические мастер-классы для людей, страдающих от различных видов зависимостей, таких как компьютерная или алкогольная зависимость. Мы ищем волонтеров, которые хотели бы вести эти мастер-классы, предоставлять эмоциональную поддержку и помогать в процессе выздоровления. Ваше участие может быть ключом к помощи участникам в возвращении контроля над своей жизнью.',
                 description_ua = 'Наша організація проводить терапевтичні майстерні для людей, які страждають від різних видів залежностей, таких як комп ютерна або алкогольна залежність. Ми шукаємо волонтерів, які хотіли б вести ці майстерні, надавати емоційну підтримку та допомагати у процесі одужання. Ваша участь може бути ключем до допомоги учасникам повернути контроль над своїм життям.'
WHERE id = 42;

UPDATE event SET description_en = 'We are preparing a campaign against addictions, aimed at raising awareness about the various types of dependencies. We seek volunteers who would like to help organize events, educational meetings, and social actions. Your work can help prevent addictions from various sources.',
                 description_ru = 'Мы готовим кампанию против зависимостей, направленную на повышение осведомленности о различных типах зависимостей. Мы ищем волонтеров, которые хотели бы помочь организовать мероприятия, образовательные встречи и социальные акции. Ваша работа может помочь предотвратить зависимости от различных источников.',
                 description_ua = 'Ми готуємо кампанію проти залежностей, спрямовану на підвищення обізнаності про різні типи залежностей. Ми шукаємо волонтерів, які хотіли б допомогти організувати заходи, освітні зустрічі та соціальні акції. Ваша робота може допомогти запобігти залежностям від різних джерел.'
WHERE id = 43;

UPDATE event SET description_en = 'Our association offers support to families affected by addictions to various substances and behaviors. We are looking for volunteers who would like to help organize support meetings, provide guidance to parents, and create safe spaces for conversations. Your presence can make a difference for these families during challenging times.',
                 description_ru = 'Наше объединение оказывает поддержку семьям, пострадавшим от зависимостей от различных веществ и поведенческих зависимостей. Мы ищем волонтеров, которые хотели бы помочь организовать встречи поддержки, давать советы родителям и создавать безопасные места для разговоров. Ваше присутствие может сделать разницу для этих семей в трудные времена.',
                 description_ua = 'Наше об єднання надає підтримку сім ям, постраждалим від залежностей від різних речовин і поведінкових залежностей. Ми шукаємо волонтерів, які б хотіли допомогти організувати зустрічі підтримки, надавати поради батькам і створювати безпечні місця для розмов. Ваша присутність може зробити різницю для цих сімей у складні часи.'
WHERE id = 44;