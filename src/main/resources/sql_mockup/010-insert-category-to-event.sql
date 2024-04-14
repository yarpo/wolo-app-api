--liquibase formatted sql

--changeset woloapp:010

INSERT INTO category_to_event (category_id, event_id) VALUES
                                                          (5, 1),
                                                          (3, 1),
                                                          (3, 2),
                                                          (1, 2),
                                                          (3, 3),
                                                          (5, 4),
                                                          (3, 4),
                                                          (1, 5),
                                                          (6, 5),
                                                          (5, 5),
                                                          (4, 6),
                                                          (3, 8),
                                                          (6, 8),
                                                          (3, 9),
                                                          (1, 9),
                                                          (2, 9),
                                                          (3, 10),
                                                          (1, 12),
                                                          (3, 14),
                                                          (5, 16),
                                                          (3, 17),
                                                          (2, 18);

INSERT INTO shift_to_user ( user_id, shift_id, is_on_reserve_list, is_leader) VALUES
                                                                                  ( 3, 14, false, true),
                                                                                  ( 3, 15, false, false),
                                                                                  ( 3, 16, true, false),
                                                                                  ( 3, 17, false, true),
                                                                                  ( 3, 18, false, false),
                                                                                  ( 3, 19, true, true),
                                                                                  ( 6, 14, false, false),
                                                                                  ( 6, 15, false, false),
                                                                                  ( 6, 16, true, true),
                                                                                  ( 6, 17, false, false),
                                                                                  ( 6, 1, false, false),
                                                                                  ( 6, 2, false, true);