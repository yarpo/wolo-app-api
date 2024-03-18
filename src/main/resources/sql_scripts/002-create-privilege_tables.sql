--liquibase formatted sql

--changeset woloapp:002

-- Table: privilege
CREATE TABLE privilege (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(255) NOT NULL
);

-- Table: privilege_to_role
CREATE TABLE privilege_to_role (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        role_id BIGINT NOT NULL,
        privilege_id BIGINT NOT NULL
);

ALTER TABLE privilege_to_role
        ADD CONSTRAINT fk_privilege_to_role_role_id
                FOREIGN KEY (role_id)
                        REFERENCES "role" (id),
        ADD CONSTRAINT fk_privilege_to_role_privilege_id
                FOREIGN KEY (privilege_id)
                        REFERENCES privilege (id);

-- Table: user_to_role
CREATE TABLE user_to_role (
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
       role_id BIGINT NOT NULL,
       user_id BIGINT NOT NULL
);

ALTER TABLE user_to_role
        ADD CONSTRAINT fk_user_to_role_role_id
                FOREIGN KEY (role_id)
                        REFERENCES "role" (id),
        ADD CONSTRAINT fk_user_to_role_user_id
                FOREIGN KEY (user_id)
                        REFERENCES "user" (id);
