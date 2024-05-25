ALTER TABLE organisation
    RENAME COLUMN description TO description_pl;

ALTER TABLE organisation
    ADD COLUMN description_en TEXT;

ALTER TABLE organisation
    ADD COLUMN description_ua TEXT;

ALTER TABLE organisation
    ADD COLUMN description_ru TEXT;
