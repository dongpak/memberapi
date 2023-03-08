--liquibase formatted sql

--changeset dongp:0
CREATE TABLE member_church (
	member_id       UUID NOT NULL,
	church_id       UUID NOT NULL,
	created_date    TIMESTAMP NOT NULL,
	created_by      TEXT NOT NULL,
	updated_date    TIMESTAMP NOT NULL,
	updated_by      TEXT NOT NULL,
	PRIMARY KEY (member_id, church_id),
	FOREIGN KEY(member_id) REFERENCES "member"(id),
	FOREIGN KEY(church_id) REFERENCES church(id)
);