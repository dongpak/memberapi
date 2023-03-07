--liquibase formatted sql

--changeset dongp:0
CREATE TABLE "member" (
    id              UUID PRIMARY KEY,
    active          BOOL NOT NULL DEFAULT true,
    "name"          TEXT NOT NULL,
    othername       TEXT,
    start_date      TIMESTAMP NOT NULL,
    end_date        TIMESTAMP,
    regular         BOOL NOT NULL DEFAULT true,
    created_date    TIMESTAMP NOT NULL,
    created_by      TEXT NOT NULL,
    updated_date    TIMESTAMP NOT NULL,
    updated_by      TEXT NOT NULL
)

