--liquibase formatted sql

--changeset woloapp:006
CREATE TABLE IF NOT EXISTS report (
                                      id BIGSERIAL PRIMARY KEY,
                                      report TEXT NOT NULL,
                                      published BOOLEAN,
                                      event_id BIGINT,
                                      FOREIGN KEY (event_id) REFERENCES event(id)
);