-- Table: address
CREATE TABLE address (
                         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         street VARCHAR(50) NOT NULL,
                         home_num VARCHAR(10) NOT NULL,
                         district_id BIGINT NOT NULL,
                         description VARCHAR(250) NOT NULL
);

-- Table: address_to_event
CREATE TABLE address_to_event (
                                  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                  event_id BIGINT NOT NULL,
                                  address_id BIGINT NOT NULL
);

-- Table: category
CREATE TABLE category (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          "name" VARCHAR(50) NOT NULL
);

-- Table: district
CREATE TABLE district (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          "name" VARCHAR(50) NOT NULL,
                          city VARCHAR(50) NOT NULL
);

-- Table: event
CREATE TABLE event (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       "name" VARCHAR(250) NOT NULL,
                       description TEXT NOT NULL,
                       category_id BIGINT,
                       is_pesel_ver_req BOOLEAN NOT NULL,
                       is_agreement_needed BOOLEAN NOT NULL,
                       organisation_id BIGINT NOT NULL
);

-- Table: organisation
CREATE TABLE organisation (
                              id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                              "name" VARCHAR(250) NOT NULL,
                              description TEXT NOT NULL,
                              email VARCHAR(50) NOT NULL,
                              phone_num VARCHAR(9) NOT NULL,
                              address_id BIGINT NOT NULL,
                              is_approved BOOLEAN NOT NULL,
                              moderator_id BIGINT NOT NULL
);

-- Table: role
CREATE TABLE role (
                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      "name" VARCHAR(20) NOT NULL
);

-- Table: shift
CREATE TABLE shift (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       address_to_event_id BIGINT NOT NULL,
                       start_time TIME NOT NULL,
                       end_time TIME NOT NULL,
                       "date" DATE NOT NULL,
                       capacity INT NOT NULL,
                       is_leader_required BOOLEAN NOT NULL,
                       required_min_age INT NOT NULL
);

-- Table: shift_to_user
CREATE TABLE shift_to_user (
                               id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               shift_id BIGINT NOT NULL,
                               is_on_reserve_list BOOLEAN NOT NULL,
                               is_leader BOOLEAN NOT NULL
);

-- Table: user
CREATE TABLE "user" (
                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        firstname VARCHAR(50) NOT NULL,
                        lastname VARCHAR(50) NOT NULL,
                        email VARCHAR(50) NOT NULL,
                        phone_number VARCHAR(9) NOT NULL,
                        role_id BIGINT NOT NULL,
                        is_pesel_verified BOOLEAN NOT NULL,
                        is_agreement_signed BOOLEAN NOT NULL,
                        is_adult BOOLEAN NOT NULL
);
