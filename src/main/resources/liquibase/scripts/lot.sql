-- liquibase formatted sql

-- changeset garry:1
CREATE TABLE public.lot
(
    id            BIGINT NOT NULL PRIMARY KEY,
    bid_price     INTEGER,
    description   VARCHAR(4096),
    start_price   INTEGER,
    status        INTEGER,
    title         VARCHAR(255)
);
