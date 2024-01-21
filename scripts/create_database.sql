-- create database and connect to it
drop database blockchain_voting;
create database blockchain_voting;
\c blockchain_voting

-- create tables
create table clients(
    id SERIAL CONSTRAINT clients_id_key PRIMARY KEY,
    password varchar(70) NOT NULL,
    id_right_to_vote int NOT NULL);

create table rights_to_vote(
    id SERIAL CONSTRAINT rights_to_vote_id_key PRIMARY KEY,
    email varchar(70) NOT NULL UNIQUE);

create table votings(
    id SERIAL CONSTRAINT votings_id_key PRIMARY KEY,
    voting_key VARCHAR(64) NOT NULL);

create table email_to_vote(
    id SERIAL CONSTRAINT email_to_vote_key PRIMARY KEY,
    id_voting int NOT NULL,
    id_user int NOT NULL);

create table candidates(
    id SERIAL CONSTRAINT candidates_id_key PRIMARY KEY,
    name varchar(70) NOT NULL,
    description varchar (400)
);

-- add constraints
alter table clients add constraint ref_id_right_to_vote FOREIGN KEY (id_right_to_vote) REFERENCES rights_to_vote (id);
alter table email_to_vote add constraint ref_id_user FOREIGN KEY (id_user) REFERENCES clients (id);
alter table email_to_vote add constraint ref_id_voting FOREIGN KEY (id_voting) REFERENCES votings (id);