CREATE TABLE IF NOT EXISTS reserve_list (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    shift_id BIGINT NOT NULL
);

ALTER TABLE reserve_list
    ADD CONSTRAINT fk_reserve_list_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    ADD CONSTRAINT fk_reserve_list_shift_id
        FOREIGN KEY (shift_id)
            REFERENCES shift (id);
