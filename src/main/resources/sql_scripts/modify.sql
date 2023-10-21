
CREATE UNIQUE INDEX idx_unique_email ON "user" (email);

-- foreign keys
-- Tabela: address
ALTER TABLE address
    ADD CONSTRAINT fk_address_district_id
        FOREIGN KEY (district_id)
            REFERENCES district (id);

-- Tabela: address_to_event
ALTER TABLE address_to_event
    ADD CONSTRAINT fk_address_to_event_event_id
        FOREIGN KEY (event_id)
            REFERENCES event (id),
    ADD CONSTRAINT fk_address_to_event_address_id
        FOREIGN KEY (address_id)
            REFERENCES address (id);

-- Tabela: event
ALTER TABLE event
    ADD CONSTRAINT fk_event_category_id
        FOREIGN KEY (category_id)
            REFERENCES category (id),
    ADD CONSTRAINT fk_event_organisation_id
        FOREIGN KEY (organisation_id)
            REFERENCES organisation (id);

-- Tabela: organisation
ALTER TABLE organisation
    ADD CONSTRAINT fk_organisation_address_id
        FOREIGN KEY (address_id)
            REFERENCES address (id)
            ON DELETE CASCADE,
    ADD CONSTRAINT fk_organisation_moderator_id
        FOREIGN KEY (moderator_id)
            REFERENCES "user" (id);


-- Tabela: shift
ALTER TABLE shift
    ADD CONSTRAINT fk_shift_address_to_event_id
        FOREIGN KEY (address_to_event_id)
            REFERENCES address_to_event (id);


-- Tabela: shift_to_user
ALTER TABLE shift_to_user
    ADD CONSTRAINT fk_shift_to_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    ADD CONSTRAINT fk_shift_to_user_shift_id
        FOREIGN KEY (shift_id)
            REFERENCES shift (id);

-- Tabela: user
ALTER TABLE "user"
    ADD CONSTRAINT fk_user_role_id
        FOREIGN KEY (role_id)
            REFERENCES role (id);



CREATE OR REPLACE FUNCTION update_event_after_category_delete()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE event
    SET category_id = (SELECT id FROM category WHERE "name" = 'Podstawowa')
    WHERE category_id = OLD.id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_event_after_category_delete
    AFTER DELETE ON category
    FOR EACH ROW
EXECUTE FUNCTION update_event_after_category_delete();