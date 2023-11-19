--liquibase formatted sql

--changeset woloapp:2
INSERT INTO address ( street, home_num, district_id, description) VALUES
                                                                      ( 'ul. Kwiatowa', '10A', 2, 'Wejście ze strony parku'),
                                                                      ( 'ul. Słoneczna', '5', 1, 'Biuro PJATK'),
                                                                      ( 'ul. Parkowa', '7', 3, 'Wejście przez zieloną bramę, na domofonie #31');

INSERT INTO "user" ( firstname, lastname, email, phone_number, role_id, is_pesel_verified, is_agreement_signed, is_adult) VALUES
                                                                                                                              ( 'Jan', 'Kowalski', 'jan.kowalski@example.com', '123456789', 3, true, true, true),
                                                                                                                              ( 'Anna', 'Nowak', 'anna.nowak@example.com', '987654321', 3, false, true, true),
                                                                                                                              ( 'Piotr', 'Wójcik', 'piotr.wojcik@example.com', '456789123', 2, true, false,false);

INSERT INTO organisation ( "name", description, email, phone_num, address_id, is_approved, moderator_id, logo_url) VALUES
( 'Pomorska Fundacja Bracia Mniejsi', 'Pomagamy zwierzakom).', 'info@sprintclub.com', '123456789', 1, true, 1, 'https://upload.wikimedia.org/wikipedia/commons/2/25/Intel_logo_%282006-2020%29.jpg'),
( 'Centrum Wolontariatu w Gdańsku', 'Regionalne Centrum Wolontariatu w Gdańsku powstało w 1994r. Od 2001 roku jesteśmy samodzielnym stowarzyszeniem działającym w ramach ogólnopolskiej Federacji Centrów Wolontariatu', 'info@nowehoryzontygallery.com', '987654321', 2, true, 2, 'https://img.freepik.com/premium-wektory/streszczenie-kolorowe-logo-projektu_650075-1506.jpg'),
( 'Akademia Programowania "Koduj z Nami"', 'Akademia oferująca kursy programowania dla osób w każdym wieku.', 'info@kodujznami.com', '456789123', 3, true, 1, 'https://img.freepik.com/darmowe-wektory/ptak-kolorowe-logo-wektor-gradientu_343694-1365.jpg');

INSERT INTO event ( "name", description, category_id, is_pesel_ver_req, is_agreement_needed, organisation_id, image_url, is_approved) VALUES
                                                                                                                                       ( 'Bieg Maratoński', ' Pieski spędzające cały dzień na kanapie się nudzą
Dlatego poszukujemy wolontariuszy, do spacerów z dużymi psami, które mieszkają w Leźnie i są pod opieką naszej Fundacji.', 1, false, false, 1, 'https://www.konferencje.pl/gfx/konferencje/userfiles/images/Event%20czym%20jest%20event%20w%20plenerze.jpg', true),
                                                                                                                                       ( 'Festyn dla dzieci na Rudnikach', 'Poszukujemy wolontariuszy do pomocy przy organizacji festynu dla dzieci.
Festyn obędzie się 10 czerwca 2023  na Rudnikach.', 2, true, true, 3, 'https://img.freepik.com/darmowe-wektory/ptak-kolorowe-logo-wektor-gradientu_343694-1365.jpg', true),
                                                                                                                                       ( 'Warsztaty Programowania', 'Już w czerwcu o 10:00 pod Fontanną Neptuna w Gdańsku wystartuje Parada Seniorów 2023!
O godzinie 10:00 rozpocznie się część oficjalna z przemówieniami.', 3, true, true, 2, 'https://cdn-daimk.nitrocdn.com/OrmiGLClAGCkdsnLonNSzrJOPvnSqbNv/assets/images/optimized/rev-d9c9754/www.projektefektywny.pl/wp-content/uploads/2021/05/Wieczor-tematyczny.jpg', false);

INSERT INTO address_to_event ( event_id, address_id) VALUES
                                                         ( 1, 1),
                                                         ( 2, 2),
                                                         ( 3, 3);

INSERT INTO shift ( address_to_event_id, start_time, end_time, date, capacity, is_leader_required, required_min_age) VALUES
                                                                                                                         ( 1, '09:00:00', '12:00:00', '2023-06-10', 20, true, 18),
                                                                                                                         ( 2, '14:00:00', '18:00:00', '2023-06-12', 15, false, 16),
                                                                                                                         ( 3, '10:30:00', '13:30:00', '2023-06-15', 10, true, 18);

INSERT INTO shift_to_user ( user_id, shift_id, is_on_reserve_list, is_leader) VALUES
                                                                                  ( 1, 1, false, true),
                                                                                  ( 2, 2, false, false),
                                                                                  ( 3, 3, true, false);
