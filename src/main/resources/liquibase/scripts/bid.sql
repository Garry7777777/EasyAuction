-- liquibase formatted sql

-- changeset garry:1
CREATE TABLE public.bid
(
    date   TIMESTAMP NOT NULL PRIMARY KEY,
    bidder VARCHAR(255),
    lot_id BIGINT  REFERENCES lot
);
