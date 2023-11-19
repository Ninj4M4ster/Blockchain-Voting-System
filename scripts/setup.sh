# connect to postgres user
sudo -i -u postgres
psql  # start querying

# create database and connect to it
create database blockchain_voting;
\c blockchain_voting

# create tables
create table klienci(
id int CONSTRAINT klienci_id_key PRIMARY KEY,
haslo varchar(70) NOT NULL,
id_prawa_do_glosu int NOT NULL);

create table prawa_do_glosu(
id int CONSTRAINT prawa_do_glosu_id_key PRIMARY KEY,
email varchar(70) NOT NULL);

create table glosowania(
id int CONSTRAINT glosowania_id_key PRIMARY KEY,
klucz_glosowania VARCHAR(64) NOT NULL);

create table email_glos(
id_glosowania int NOT NULL,
id_uzytkownika int NOT NULL);

# add constraints
alter table prawa_do_glosu add constraint unique_email UNIQUE(email);
alter table klienci add constraint ref_id_prawa_do_glosu FOREIGN KEY (id_prawa_do_glosu) REFERENCES prawa_do_glosu (id);
alter table email_glos add constraint ref_id_uzytkownika FOREIGN KEY (id_uzytkownika) REFERENCES klienci (id);
alter table email_glos add constraint ref_id_glosowania FOREIGN KEY (id_glosowania) REFERENCES glosowania (id);


